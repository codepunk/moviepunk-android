package com.codepunk.moviepunk.domain.repository

import app.cash.quiver.extensions.OutcomeOf
import arrow.core.Either
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.MoviePage
import kotlinx.coroutines.flow.Flow

interface MoviePunkRepository {

    // region Methods

    suspend fun getGenres(): Flow<OutcomeOf<List<Genre>>>

    suspend fun getMovieGenres(): Flow<Either<Exception, List<Genre>>>

    suspend fun getTvGenres(): Flow<Either<Exception, List<Genre>>>

    suspend fun getTrendingMovies(): Flow<OutcomeOf<MoviePage>>

    // endregion Methods

}
