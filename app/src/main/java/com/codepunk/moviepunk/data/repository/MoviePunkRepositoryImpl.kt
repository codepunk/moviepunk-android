package com.codepunk.moviepunk.data.repository

import app.cash.quiver.Absent
import app.cash.quiver.extensions.OutcomeOf
import app.cash.quiver.raise.outcomeOf
import arrow.core.Either
import arrow.core.left
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.mapper.*
import com.codepunk.moviepunk.data.remote.util.toApiEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.MoviePage
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

class MoviePunkRepositoryImpl(
    private val db: MoviePunkDatabase,
    private val webservice: MoviePunkWebservice
) : MoviePunkRepository {

    // region Properties

    val genreDao: GenreDao by lazy { db.genreDao() }

    // endregion Properties

    // region Methods

    override suspend fun getGenres(): Flow<OutcomeOf<List<Genre>>> = flow {
        // Step 1: Signify that we are loading
        emit(value = Absent)

        // Step 2: Emit any cached genres
        val genres = outcomeOf {
            genreDao.getGenres().map { it.toGenre() }
        }
        emit(value = genres)

        // Step 3: Determine if we need to refresh cached data
        val elapsed = Clock.System.now() - getNewestGenre()
        if (elapsed > BuildConfig.DATA_REFRESH_DURATION_MINUTES.minutes) {
            Timber.i(message = "Genre data is out of date, updating")
            // Step 3a: Signify that we are loading
            emit(value = Absent)
            val genres = outcomeOf {
                // Step 3b: Fetch remote genres
                val movieGenres = webservice.fetchMovieGenres().toApiEither { result ->
                    result.genres.map { it.toLocalGenre()}
                }.bind()
                val tvGenres = webservice.fetchTvGenres().toApiEither { result ->
                    result.genres.map { it.toLocalGenre()}
                }.bind()

                // Step 3c: Cache genres
                genreDao.insertGenres(
                    genres = (movieGenres + tvGenres).distinctBy { it.id },
                    movieGenres = movieGenres.map { it.toLocalMovieGenre() },
                    tvGenres = tvGenres.map {it.toLocalTvGenre() }
                )

                genreDao.getGenres().map { it.toGenre() }
            }
            // Step 3d: Emit cached genres
            emit(value = genres)
        } else {
            Timber.i(message = "Genre data is up to date")
        }
    }

    override suspend fun getMovieGenres(): Flow<Either<Exception, List<Genre>>> = flow {
        // TODO
    }

    override suspend fun getTvGenres(): Flow<Either<Exception, List<Genre>>> = flow {
        // TODO
    }

    override suspend fun getNewestGenre(): Instant =
        try {
            genreDao.getNewestGenre()
        } catch (_: Exception) {
            Instant.DISTANT_PAST
        }

    override suspend fun getTrendingMovies(): Flow<Either<Exception, MoviePage>> = flow {
        // TODO Incorporate Room, paging, etc.
        try {
            webservice.fetchTrendingMovies().toApiEither(
                onHeaders = { /* TODO Do something with headers here */ }
            ) { it.toMoviePage() }
        } catch (e: Exception) {
            e.left()
        }.apply {
            emit(this)
        }
    }

    // endregion Methods

}
