package com.codepunk.moviepunk.data.repository

import android.database.sqlite.SQLiteConstraintException
import android.net.ConnectivityManager
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import arrow.core.Either
import arrow.core.raise.either
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.CuratedContentDao
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.mapper.toEntity
import com.codepunk.moviepunk.data.mapper.toModel
import com.codepunk.moviepunk.data.paging.TrendingMoviePagerFactory
import com.codepunk.moviepunk.data.remote.dto.GenreDto
import com.codepunk.moviepunk.data.remote.util.WebScraper
import com.codepunk.moviepunk.data.remote.util.toApiEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.CuratedContentItem
import com.codepunk.moviepunk.domain.model.CuratedContentType
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.MediaType
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.domain.model.TimeWindow
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import com.codepunk.moviepunk.domain.repository.RepositoryState
import com.codepunk.moviepunk.domain.repository.RepositoryState.ExceptionState
import com.codepunk.moviepunk.util.extension.isConnected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes

class MoviePunkRepositoryImpl(
    private val connectivityManager: ConnectivityManager,
    private val db: MoviePunkDatabase,
    private val curatedContentDao: CuratedContentDao,
    private val genreDao: GenreDao,
    private val webservice: MoviePunkWebservice,
    private val trendingMoviePagerFactory: TrendingMoviePagerFactory,
    private val webScraper: WebScraper
) : MoviePunkRepository {

    // region Methods

    override suspend fun syncGenres(): Either<RepositoryState, Boolean> {
        var dataUpdated = false
        return either {
            networkBoundResource(
                query = { genreDao.getAll() },
                fetch = {
                    if (!connectivityManager.isConnected) {
                        raise(RepositoryState.NoConnectivityState)
                    }
                    val movieGenres = webservice.fetchGenres(MediaType.MOVIE)
                        .toApiEither().bind().genres
                        .map { it.copy(mediaTypes = listOf(MediaType.MOVIE)) }
                    val tvGenres = webservice.fetchGenres(MediaType.TV)
                        .toApiEither().bind().genres
                        .map { it.copy(mediaTypes = listOf(MediaType.TV)) }
                    (movieGenres + tvGenres)
                        .groupBy { dto ->
                            dto.id
                        }.map { (id, dtos) ->
                            GenreDto(
                                id = id,
                                name = dtos.firstOrNull()?.name.orEmpty(),
                                mediaTypes = dtos.map { it.mediaTypes }.flatten()
                            )
                        }
                },
                shouldFetch = { entities ->
                    if (entities.isEmpty()) {
                        true
                    } else {
                        val oldest = entities.minBy { it.genre.createdAt }.genre.createdAt
                        val now = Clock.System.now()
                        now - oldest > BuildConfig.DATA_REFRESH_DURATION_MINUTES.minutes
                    }
                },
                saveFetchResult = { dtos ->
                    dataUpdated = try {
                        db.withTransaction {
                            genreDao.deleteAll()
                            genreDao.insertAllWithMediaTypes(
                                dtos.map { it.toEntity() }
                            )
                            true
                        }
                    } catch (e: SQLiteConstraintException) {
                        raise(ExceptionState(e))
                    }
                },
                transform = { entities ->
                    entities.map { it.toModel() }
                }
            )
        }.map { dataUpdated }
    }

    override fun getGenres(): Flow<List<Genre>> = genreDao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override suspend fun syncCuratedContent(): Either<RepositoryState, Boolean> = either {
        // 1. Get the remote CSS Hrefs
        val cssUris = webScraper.getCssUris(
            urlString = BuildConfig.TMDB_URL
        ).toApiEither().bind()

        // 2. Find mobile and non-mobile hrefs (there should only be one of each)
        val standardCssUri = cssUris.find { !it.pathSegments.contains(MOBILE) }
        val mobileCssUri = cssUris.find { it.pathSegments.contains(MOBILE) }

        // 3. Fetch featured content
        val networkFeaturedCssHref = mobileCssUri?.toString().orEmpty()
        val cachedFeaturedCssHref = curatedContentDao.getHref(
            type = CuratedContentType.FEATURED.value
        )
        val featured = if (networkFeaturedCssHref != cachedFeaturedCssHref) {
            val response = webScraper.scrapeUrlForFeaturedContent(
                baseUrl = BuildConfig.TMDB_URL,
                cssHref = networkFeaturedCssHref
            ).toApiEither().bind()
            response.content
        } else {
            emptyList()
        }

        // 4. Fetch community content
        val networkCommunityCssHref = standardCssUri?.toString().orEmpty()
        val cachedCommunityCssHref = curatedContentDao.getHref(
            type = CuratedContentType.COMMUNITY.value
        )
        val community = if (networkCommunityCssHref != cachedCommunityCssHref) {
            val response = webScraper.scrapeUrlForCommunityContent(
                baseUrl = BuildConfig.TMDB_URL,
                cssHref = networkCommunityCssHref
            ).toApiEither().bind()
            response.content
        } else {
            emptyList()
        }

        // Save any newly-fetched content
        try {
            var dataUpdated = false
            db.withTransaction {
                if (featured.isNotEmpty()) {
                    curatedContentDao.deleteAll(CuratedContentType.FEATURED.value)
                    curatedContentDao.insertAll(featured.map { it.toEntity() })
                    dataUpdated = true
                }
                if (community.isNotEmpty()) {
                    curatedContentDao.deleteAll(CuratedContentType.COMMUNITY.value)
                    curatedContentDao.insertAll(community.map { it.toEntity() })
                    dataUpdated = true
                }
            }
            dataUpdated
        } catch (e: SQLiteConstraintException) {
            raise(ExceptionState(e))
        }
    }

    override suspend fun getCuratedContent(): Flow<List<CuratedContentItem>> =
        curatedContentDao.getAll().map { entities ->
            entities.map { it.toModel() }
        }

    override suspend fun getFeaturedContent(
        currentId: Int
    ): Either<RepositoryState, CuratedContentItem?> =
        either {
            try {
                curatedContentDao.getRandom(currentId)?.toModel()
            } catch (e: Exception) {
                raise(ExceptionState(e))
            }
        }

    override fun getTrendingMovies(
        timeWindow: TimeWindow,
        pageLimit: Int
    ): Flow<PagingData<Movie>> {
        // TODO Some way of caching this pager?
        return trendingMoviePagerFactory.create(
            timeWindow = timeWindow,
            pageLimit = pageLimit
        ).flow.map { pagingData ->
            pagingData.map { it.toModel() }
        }
    }

    // endregion Methods

    // region Companion object

    private companion object {
        private const val MOBILE = "mobile"
    }

    // endregion Companion object

}
