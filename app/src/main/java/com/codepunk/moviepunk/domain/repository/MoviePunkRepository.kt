package com.codepunk.moviepunk.domain.repository

import arrow.core.Either
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.MoviePage
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

interface MoviePunkRepository {

    // region Methods

    suspend fun cacheGenres(): Either<Exception, Unit>

    suspend fun getGenres(): Flow<Either<Exception, List<Genre>>>

    suspend fun getMovieGenres(): Flow<Either<Exception, List<Genre>>>

    suspend fun getTvGenres(): Flow<Either<Exception, List<Genre>>>

    suspend fun getNewestGenre(): Instant

    suspend fun getTrendingMovies(): Flow<Either<Exception, MoviePage>>

    // endregion Methods

}
