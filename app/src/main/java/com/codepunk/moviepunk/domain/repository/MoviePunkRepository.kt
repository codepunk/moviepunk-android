package com.codepunk.moviepunk.domain.repository

import arrow.core.Either
import com.codepunk.moviepunk.domain.model.ApiError
import com.codepunk.moviepunk.domain.model.MoviePage
import kotlinx.coroutines.flow.Flow

interface MoviePunkRepository {

    suspend fun fetchTrendingMovies(): Flow<Either<ApiError, MoviePage>>

}
