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
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.MediaType
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import com.codepunk.moviepunk.domain.repository.RepositoryState
import com.codepunk.moviepunk.domain.repository.RepositoryState.ExceptionState
import com.codepunk.moviepunk.util.extension.isConnected
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes

class MoviePunkRepositoryImpl(
    private val connectivityManager: ConnectivityManager,
    private val ioDispatcher: CoroutineDispatcher,
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
        return networkBoundResult(
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
                entities.isEmpty() || entities.minBy { it.genre.createdAt }.let { oldest ->
                    Clock.System.now() - oldest.genre.createdAt >
                            BuildConfig.DATA_REFRESH_DURATION_MINUTES.minutes
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
            transform = { dataUpdated }
        )
    }

    override fun getGenres(): Flow<List<Genre>> = genreDao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override suspend fun syncCuratedContent(): Either<RepositoryState, Boolean> {
        var networkCssHref = ""
        var dataUpdated = false
        return networkBoundResult(
            query = { curatedContentDao.getAll() },
            fetch = {
                webScraper.scrapeUrlForCuratedContent(
                    baseUrl = BuildConfig.TMDB_URL,
                    cssHref = networkCssHref
                ).toApiEither().bind().content
            },
            shouldFetch = { content ->
                either {
                    // Get the CSS href for curated content
                    networkCssHref = try {
                        webScraper.scrapeUrlForIndexCssHref(BuildConfig.TMDB_URL)
                    } catch (e: Exception) {
                        raise(ExceptionState(e))
                    } ?: raise(
                        ExceptionState(IOException(CSS_MESSAGE))
                    )

                    // Check whether the network uses a new href
                    val cachedCssHref = content.firstOrNull()?.href
                    cachedCssHref != networkCssHref
                }.fold(
                    ifLeft = { raise(it) },
                    ifRight = { true }
                )
            },
            saveFetchResult = { dtos ->
                dataUpdated = try {
                    db.withTransaction {
                        curatedContentDao.deleteAll()
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
        ).apply { this }
    }
        /*
        var networkCssHref = ""
        var dataUpdated = false
        return networkBoundResource(
            coroutineContext = ioDispatcher,
            query = { curatedContentDao.getAll() },
            fetch = {
                webScraper.scrapeUrlForCuratedContent(
                    baseUrl = BuildConfig.TMDB_URL,
                    cssHref = networkCssHref
                )
            },
            saveFetchResult = {result ->
                dataUpdated = either {
                    result.toApiEither().bind().content.map { it.toCuratedContentItemEntity() }
                }.onLeft { failure ->
                    emit(failure.left())
                }.onRight { entities ->
                    // Here, we updated the data
                    db.withTransaction {
                        curatedContentDao.deleteAll()
                        curatedContentDao.insertAll(entities)
                    }.isNotEmpty()
                }.fold(
                    ifLeft = { false },
                    ifRight = { it.isNotEmpty() }
                )
            },
            shouldFetch = { content ->
                if (!connectivityManager.isConnected) {
                    emit(NoConnectivityFailure.left())
                    false
                } else {
                    either {
                        // Get the CSS href for curated content
                        networkCssHref = try {
                            webScraper.scrapeUrlForIndexCssHref(BuildConfig.TMDB_URL)
                        } catch (e: Exception) {
                            raise(ExceptionFailure(e))
                        } ?: raise(
                            ExceptionFailure(IOException(CSS_MESSAGE))
                        )

                        // Check whether the network uses a new href
                        val localCssHref = content.firstOrNull()?.href.orEmpty()
                        localCssHref != networkCssHref
                    }.onLeft {
                        emit(it.left())
                    }.getOrElse { false }
                }
            },
            shouldEmitInitialData = { false },
            transform = { dataUpdated.right() }
        )
         */
    //}

    /*
    override fun getTrendingMovies(timeWindow: TimeWindow): Flow<PagingData<Movie>> {
        // TODO Some way of caching this pager?
        return trendingMoviePagerFactory.create(timeWindow)
            .flow.map { pagingData ->
                pagingData.map { it.toMovie() }
            }
    }

    override fun getRandomCuratedContentItem(): Flow<Either<RepoFailure, CuratedContentItem?>> = flow {
        // TODO NEXT ("Not yet implemented")
    }
     */

    // endregion Methods

    // region Companion object

    private companion object {
        private const val CSS_MESSAGE = "Failed to retrieve CSS href"
    }

    // endregion Companion object

}
