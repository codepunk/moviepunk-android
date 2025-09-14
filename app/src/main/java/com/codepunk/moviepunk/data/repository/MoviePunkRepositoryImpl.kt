package com.codepunk.moviepunk.data.repository

import app.cash.quiver.Absent
import app.cash.quiver.extensions.OutcomeOf
import app.cash.quiver.failure
import app.cash.quiver.present
import arrow.core.raise.either
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.local.dao.MovieDao
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.mapper.combineToEntity
import com.codepunk.moviepunk.data.mapper.toDomain
import com.codepunk.moviepunk.data.remote.util.toApiEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.EntityType
import com.codepunk.moviepunk.domain.model.Genre
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
    private val db: MoviePunkDatabase,
    private val genreDao: GenreDao,
    private val movieDao: MovieDao,
    private val webservice: MoviePunkWebservice,
) : MoviePunkRepository {

    // region Properties

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
                // bind() will raise any non-CancellationException from toApiEither()
                val movieGenreResponse =
                    webservice.fetchGenres(EntityType.MOVIE).toApiEither().bind()
                val tvGenreResponse =
                    webservice.fetchGenres(EntityType.TV).toApiEither().bind()

                // Catch any exceptions while respecting cancellation
                val genreEntities = try {
                    combineToEntity(
                        movieGenreResponse = movieGenreResponse,
                        tvGenreResponse = tvGenreResponse
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
                localGenres.map { it.toDomain() }
            }
        }

    override fun getMovieGenres(): Flow<OutcomeOf<List<Genre>>> = flow {
        emit(Absent)
    }.flowOn(ioDispatcher)

    override fun getTvGenres(): Flow<OutcomeOf<List<Genre>>> = flow {
        emit(Absent)
    }.flowOn(ioDispatcher)

    // endregion Methods

}
