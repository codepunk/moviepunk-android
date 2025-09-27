package com.codepunk.moviepunk.data.repository

import android.net.ConnectivityManager
import androidx.paging.PagingData
import androidx.paging.map
import app.cash.quiver.Absent
import app.cash.quiver.asOutcome
import app.cash.quiver.extensions.OutcomeOf
import app.cash.quiver.failure
import app.cash.quiver.present
import arrow.core.raise.either
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.local.dao.MovieDao
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.mapper.combineToGenreEntities
import com.codepunk.moviepunk.data.mapper.toGenre
import com.codepunk.moviepunk.data.mapper.toMovie
import com.codepunk.moviepunk.data.paging.TrendingMoviePagerFactory
import com.codepunk.moviepunk.data.remote.util.WebScraper
import com.codepunk.moviepunk.data.remote.util.toApiEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.EntityType
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.HashedImage
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.domain.model.TimeWindow
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import com.codepunk.moviepunk.util.extension.isConnected
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
    private val trendingMoviePagerFactory: TrendingMoviePagerFactory,
    private val webScraper: WebScraper,
    private val connectivityManager: ConnectivityManager
) : MoviePunkRepository {

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

    override fun getTrendingMovies(timeWindow: TimeWindow): Flow<PagingData<Movie>> {
        // TODO Some way of caching this pager?
        return trendingMoviePagerFactory.create(timeWindow)
            .flow.map { pagingData ->
                pagingData.map { it.toMovie() }
            }
    }

    /**
     * This method works a little differently than getLocalGenres.
     * We need to do the following:
     * 1. IF we have a connection:
     *    a. Get the current background hash from TMDB website
     *    b. If it is different than cache, re-retrieve all backgrounds
     *    c. Re-cache those backgrounds
     *    d. ???? Should I try to download/cache the images themselves at this stage?
     * 2. Retrieve all from cache
     */
    override fun getHashedBackgroundImages(): Flow<OutcomeOf<List<HashedImage>>> =
        flow<OutcomeOf<List<HashedImage>>> {
            if (connectivityManager.isConnected) {

                val hash = try {
                    val hashResult = webScraper.scrapeTmdbWallpaperHash(
                        urlString = BuildConfig.TMDB_URL
                    ).body()

                    // TODO NEXT

                    hashResult
                } catch (e: Exception) {
                    ""
                }

                val result = try {
                    val scrapeResult = webScraper.scrapeTmdbWallpaper(
                        urlString = BuildConfig.TMDB_URL
                    ).toApiEither()
                    scrapeResult.asOutcome()
                } catch (e: Exception) {
                    e.failure()
                }

                // TODO NEXT Cache / map / return value
                Timber.i("result: $result")
            }

        }.flowOn(ioDispatcher)

        // endregion Methods

    }
