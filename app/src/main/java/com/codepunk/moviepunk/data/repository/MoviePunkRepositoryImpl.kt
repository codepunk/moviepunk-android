package com.codepunk.moviepunk.data.repository

import androidx.room.withTransaction
import app.cash.quiver.Absent
import app.cash.quiver.extensions.OutcomeOf
import app.cash.quiver.failure
import app.cash.quiver.present
import arrow.core.Either
import arrow.core.raise.either
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.local.dao.MovieDao
import com.codepunk.moviepunk.data.local.entity.LocalGenre
import com.codepunk.moviepunk.data.mapper.toGenre
import com.codepunk.moviepunk.data.mapper.toLocalGenres
import com.codepunk.moviepunk.data.remote.util.toApiEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.MoviePage
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

class MoviePunkRepositoryImpl(
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

    private fun getLocalGenres(
        emitCachedBeforeFetching: Boolean = true
    ): Flow<OutcomeOf<List<LocalGenre>>> = flow {
        val elapsed = Clock.System.now() - getNewestGenre()
        if (elapsed > BuildConfig.DATA_REFRESH_DURATION_MINUTES.minutes) {
            Timber.i(message = "Genre data is out of date, updating")

            // Optionally emit cached (local) genres
            if (emitCachedBeforeFetching) {
                emit(genreDao.getGenres().present())
            }

            emit(Absent)

            // Refresh genres from remote source
            either {
                try {
                    // Fetch remote genres and convert to local
                    val localGenres = toLocalGenres(
                        movieResult = webservice.fetchMovieGenres().toApiEither().bind(),
                        tvResult = webservice.fetchTvGenres().toApiEither().bind()
                    )

                    // Cache fetched genres to local db
                    db.withTransaction {
                        genreDao.clearGenres()
                        genreDao.insertGenres(localGenres)
                    }
                } catch (e: Exception) {
                    raise(e)
                }
            }.onLeft { e ->
                // Emit any errors encountered during refresh
                emit(e.failure())
            }
        }

        // Finally, emit cached (local) genres
        Timber.i(message = "Genre data is up to date")
        emit(genreDao.getGenres().present())
    }

    override suspend fun getGenres(): Flow<OutcomeOf<List<Genre>>> =
        getLocalGenres().map { outcome ->
            outcome.map { localGenres ->
                localGenres.map { it.toGenre() }
            }
        }

    override suspend fun getMovieGenres(): Flow<Either<Exception, List<Genre>>> = flow {
        // TODO
    }

    override suspend fun getTvGenres(): Flow<Either<Exception, List<Genre>>> = flow {
        // TODO
    }

    override suspend fun getTrendingMovies(): Flow<OutcomeOf<MoviePage>> =
        getGenres().map { genresOutcome ->

            // TODO NEXT
            genresOutcome.map { genres ->
                // We now have a list of genres, so we can fetch trending movies


                MoviePage()
            }
        }

    // endregion Methods

}
