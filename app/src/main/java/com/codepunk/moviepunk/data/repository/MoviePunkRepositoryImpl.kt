package com.codepunk.moviepunk.data.repository

import app.cash.quiver.Absent
import app.cash.quiver.extensions.OutcomeOf
import app.cash.quiver.failure
import app.cash.quiver.present
import arrow.core.raise.either
import com.codepunk.moviepunk.BuildConfig.DATA_REFRESH_DURATION_MINUTES
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.local.dao.MovieDao
import com.codepunk.moviepunk.data.local.entity.LocalGenre
import com.codepunk.moviepunk.data.mapper.toGenre
import com.codepunk.moviepunk.data.mapper.toLocalGenres
import com.codepunk.moviepunk.data.remote.util.toApiEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.time.Clock.System.now
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

class MoviePunkRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val db: MoviePunkDatabase,
    private val genreDao: GenreDao,
    private val movieDao: MovieDao,
    private val webservice: MoviePunkWebservice
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
     * [Flow]<[List]<[LocalGenre]>>.
     */
    private fun getLocalGenres(): Flow<OutcomeOf<List<LocalGenre>>> = channelFlow {
        // Emit cached genres (and keep emitting as they are updated)
        launch {
            genreDao.getGenres()
                .map { localGenres -> localGenres.present() }
                .catch { e -> e.failure() }
                .collect { send(it) }
        }

        // Check if cached genres need to be updated
        val needsRefresh = now() - getNewestGenre() > DATA_REFRESH_DURATION_MINUTES.minutes
        if (needsRefresh) {
            Timber.i(message = "Genre data is out of date, updating")
            send(Absent)
            either {
                try {
                    val localGenres = toLocalGenres(
                        movieResult = webservice.fetchMovieGenres().toApiEither().bind(),
                        tvResult = webservice.fetchTvGenres().toApiEither().bind()
                    )
                    // TODO Clean up any genres that are no longer used??
                    genreDao.insertGenres(localGenres)
                } catch (e: Exception) {
                    raise(e)
                }
            }.onLeft { e ->
                // Emit any errors encountered during refresh
                send(e.failure())
            }
        } else {
            Timber.i(message = "Genre data is up to date")
        }
    }.flowOn(ioDispatcher)

    override suspend fun getGenres(): Flow<OutcomeOf<List<Genre>>> =
        getLocalGenres().map { outcome ->
            outcome.map { localGenres ->
                localGenres.map { it.toGenre() }
            }
        }

    override suspend fun getMovieGenres(): Flow<OutcomeOf<List<Genre>>> = flow {
        emit(Absent)
    }.flowOn(ioDispatcher)

    override suspend fun getTvGenres(): Flow<OutcomeOf<List<Genre>>> = flow {
        emit(Absent)
    }.flowOn(ioDispatcher)

    // endregion Methods

}
