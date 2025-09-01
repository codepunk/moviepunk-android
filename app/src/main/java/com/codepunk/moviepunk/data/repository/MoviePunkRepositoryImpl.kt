package com.codepunk.moviepunk.data.repository

import arrow.core.Either
import com.codepunk.moviepunk.data.mapper.toApiError
import com.codepunk.moviepunk.data.mapper.toMoviePage
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.ApiError
import com.codepunk.moviepunk.domain.model.MoviePage
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviePunkRepositoryImpl(
    private val webservice: MoviePunkWebservice
) : MoviePunkRepository {

    override suspend fun fetchTrendingMovies(): Flow<Either<ApiError, MoviePage>> = flow {
        // TODO Incorporate Room, paging, etc.
        val response = webservice.fetchTrendingMovies()
        emit(
            value = response.body
                .mapLeft { it.toApiError() }
                .map { it.toMoviePage() }
        )
    }

}
