package com.codepunk.moviepunk.data.repository

import arrow.core.Either
import arrow.core.combine
import arrow.core.left
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.mapper.toLocalGenre
import com.codepunk.moviepunk.data.mapper.toLocalMovieGenre
import com.codepunk.moviepunk.data.mapper.toLocalTvGenre
import com.codepunk.moviepunk.data.mapper.toMoviePage
import com.codepunk.moviepunk.data.remote.util.toEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.MoviePage
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import com.codepunk.moviepunk.manager.DataUpdateManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Instant

class MoviePunkRepositoryImpl(
    private val db: MoviePunkDatabase,
    private val webservice: MoviePunkWebservice
) : MoviePunkRepository {

    // region Properties

    val genreDao: GenreDao by lazy { db.genreDao() }

    // endregion Properties

    // region Methods

    /**
     * Fetches all movie and TV genres and caches them to the local database. This method is
     * called from [DataUpdateManager] and should be called a maximum of once per
     * application session.
     */
    override suspend fun cacheGenres(): Either<Exception, Unit> =
        try {
            val movieResult = webservice.fetchMovieGenres().toEither()
            val tvResult = webservice.fetchTvGenres().toEither()
            movieResult.combine(
                other = tvResult,
                combineLeft = { e, _ -> e },
                combineRight = { movieResult, tvResult ->
                    val movieGenres = movieResult.genres.map { it.toLocalGenre() }
                    val tvGenres = tvResult.genres.map { it.toLocalGenre() }
                    val genres = (movieGenres + tvGenres).distinctBy { it.id }
                    genreDao.insertGenres(
                        genres = genres,
                        movieGenres = movieGenres.map { it.toLocalMovieGenre() },
                        tvGenres = tvGenres.map { it.toLocalTvGenre() }
                    )
                    movieResult
                }
            ).map {}
        } catch (e: Exception) {
            e.left()
        }

    override suspend fun getGenres(): Flow<Either<Exception, List<Genre>>> = flow {
        // TODO
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
            webservice.fetchTrendingMovies().toEither { headers ->
                // TODO Do something with headers here
            }.map {
                it.toMoviePage()
            }
        } catch (e: Exception) {
            e.left()
        }.apply {
            emit(this)
        }
    }

    // endregion Methods

}
