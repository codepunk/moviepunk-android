package com.codepunk.moviepunk.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import app.cash.quiver.Absent
import app.cash.quiver.extensions.OutcomeOf
import app.cash.quiver.failure
import app.cash.quiver.present
import arrow.core.raise.either
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.local.dao.MovieDao
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.mapper.combineToGenreEntities
import com.codepunk.moviepunk.data.mapper.toMovie
import com.codepunk.moviepunk.data.mapper.toGenre
import com.codepunk.moviepunk.data.paging.TrendingMovieRemoteMediator
import com.codepunk.moviepunk.data.paging.TrendingMovieRemoteMediatorFactory
import com.codepunk.moviepunk.data.remote.util.toApiEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.EntityType
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.domain.model.TimeWindow
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

class MoviePunkRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val genreDao: GenreDao,
    private val movieDao: MovieDao,
    private val webservice: MoviePunkWebservice,
    private val trendingMovieRemoteMediatorFactory: TrendingMovieRemoteMediatorFactory
) : MoviePunkRepository {

    // region Properties

    private val trendingMovieRemoteMediators: MutableMap<TimeWindow, TrendingMovieRemoteMediator> =
        mutableMapOf()

    // endregion Properties

    // region Methods

    private suspend fun getNewestGenre(): Instant = try {
        genreDao.getNewestGenre()
    } catch (_: Exception) {
        Instant.DISTANT_PAST
    }

    /**
     * This version uses [channelFlow] to emit cached data immediately
     * and then update it if necessary. It relies on [GenreDao.getGenres] returning a
     * [Flow]<[List]<[GenreEntity]>>.
     */
    private fun getLocalGenres(): Flow<OutcomeOf<List<GenreEntity>>> = channelFlow {
        // Emit cached genres (and keep emitting as they are updated)
        launch {
            genreDao.getGenres()
                .map { localGenres -> localGenres.present() }
                .catch { e -> e.failure() }
                .collect { send(it) }
        }

        // Check if cached genres need to be updated
        val duration = BuildConfig.DATA_REFRESH_DURATION_MINUTES.minutes
        val needsRefresh = Clock.System.now() - getNewestGenre() > duration
        if (needsRefresh) {
            Timber.i(message = "Genre data is out of date, updating")
            send(Absent)
            either {
                // TODO Do I need all of these separate try's? Can they be combined?
                // bind() will raise any non-CancellationException from toApiEither()
                val movieGenreDtos = try {
                    webservice.fetchGenres(EntityType.MOVIE).toApiEither().bind().genres
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    raise(e)
                }
                val tvGenreDtos = try {
                    webservice.fetchGenres(EntityType.TV).toApiEither().bind().genres
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    raise(e)
                }

                // Catch any exceptions while respecting cancellation
                val genreEntities = try {
                    combineToGenreEntities(
                        movieGenreDtos = movieGenreDtos,
                        tvGenreDtos = tvGenreDtos
                    )
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    raise(e)
                }

                try {
                    genreDao.insertAll(genreEntities)
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    raise(e)
                }
            }.onLeft { e ->
                // This will now only receive non-CancellationExceptions
                send(e.failure())
            }
        } else {
            Timber.i(message = "Genre data is up to date")
        }
    }.flowOn(ioDispatcher)

    override fun getGenres(): Flow<OutcomeOf<List<Genre>>> =
        getLocalGenres().map { outcome ->
            outcome.map { localGenres ->
                localGenres.map { it.toGenre() }
            }
        }

    override fun getMovieGenres(): Flow<OutcomeOf<List<Genre>>> = flow {
        emit(Absent)
    }.flowOn(ioDispatcher)

    override fun getTvGenres(): Flow<OutcomeOf<List<Genre>>> = flow {
        emit(Absent)
    }.flowOn(ioDispatcher)

    @OptIn(ExperimentalPagingApi::class)
    override fun getTrendingMovies(timeWindow: TimeWindow): Flow<PagingData<Movie>> {
        val remoteMediator = trendingMovieRemoteMediators.getOrPut(timeWindow) {
            trendingMovieRemoteMediatorFactory.create(EntityType.MOVIE, timeWindow)
        }
        return Pager(
            config = PagingConfig(
                pageSize = BuildConfig.TMDB_PAGE_SIZE,
                enablePlaceholders = false // Recommended when using RemoteMediator
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                movieDao.getTrendingMoviePagingSource()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toMovie() }
        }
    }

    // endregion Methods

}
