package com.codepunk.moviepunk.data.repository

import android.database.sqlite.SQLiteConstraintException
import android.net.ConnectivityManager
import androidx.room.withTransaction
import arrow.core.Either
import arrow.core.raise.either
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.CombinedDao
import com.codepunk.moviepunk.data.local.dao.CuratedContentDao
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.mapper.toEntity
import com.codepunk.moviepunk.data.mapper.toModel
import com.codepunk.moviepunk.data.remote.dto.GenreDto
import com.codepunk.moviepunk.data.remote.util.WebScraper
import com.codepunk.moviepunk.data.remote.util.toApiEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.CuratedContentItem
import com.codepunk.moviepunk.domain.model.CuratedContentType
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.MediaType
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import com.codepunk.moviepunk.domain.repository.RepositoryState
import com.codepunk.moviepunk.domain.repository.RepositoryState.ExceptionState
import com.codepunk.moviepunk.util.extension.isConnected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import kotlin.collections.map
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes

class MoviePunkRepositoryImpl(
    private val connectivityManager: ConnectivityManager,
    private val db: MoviePunkDatabase,
    private val combinedDao: CombinedDao,
    private val curatedContentDao: CuratedContentDao,
    private val genreDao: GenreDao,
    private val webservice: MoviePunkWebservice,
    /*
    private val movieDao: MovieDao,
    private val trendingMoviePagerFactory: TrendingMoviePagerFactory,
     */
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
                            combinedDao.insertAll(
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

    override suspend fun syncCuratedContent(
        curatedContentType: CuratedContentType
    ): Either<RepositoryState, Boolean> {
        lateinit var cssHref: String
        var dataUpdated = false
        return either {
            networkBoundResource(
                query = { curatedContentDao.getAll() },
                fetch = {
                    webScraper.scrapeUrlForContent(
                        baseUrl = BuildConfig.TMDB_URL,
                        cssHref = cssHref,
                        curatedContentType = curatedContentType
                    ).toApiEither().bind().content
                },
                shouldFetch = { content ->
                    either {
                        // Get the CSS href for curated content
                        cssHref = try {
                            val isMobile = (curatedContentType == CuratedContentType.FEATURED)
                            webScraper.scrapeUrlForIndexCssHref(
                                urlString = BuildConfig.TMDB_URL,
                                isMobile = isMobile
                            )
                        } catch (e: Exception) {
                            raise(ExceptionState(e))
                        } ?: raise(
                            ExceptionState(IOException(CSS_MESSAGE))
                        )

                        // Check whether the network uses a new href
                        val cachedCssHref = content.firstOrNull()?.href
                        cachedCssHref != cssHref
                    }.fold(
                        ifLeft = { raise(it) },
                        ifRight = { true }
                    )
                },
                saveFetchResult = { dtos ->
                    dataUpdated = try {
                        db.withTransaction {
                            curatedContentDao.deleteAll(curatedContentType.value)
                            curatedContentDao.insertAll(
                                dtos.map { it.toEntity() }
                            )
                            true
                        }
                    } catch (e: SQLiteConstraintException) {
                        raise(ExceptionState(e))
                    }
                },
                transform = { dataUpdated }
            )
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

    /*
    override fun getTrendingMovies(timeWindow: TimeWindow): Flow<PagingData<Movie>> {
        // TODO Some way of caching this pager?
        return trendingMoviePagerFactory.create(timeWindow)
            .flow.map { pagingData ->
                pagingData.map { it.toMovie() }
            }
    }
     */

    // endregion Methods

    // region Companion object

    private companion object {
        private const val CSS_MESSAGE = "Failed to retrieve CSS href"
    }

    // endregion Companion object

}
