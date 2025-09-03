package com.codepunk.moviepunk.data.repository

import arrow.core.Either
import arrow.core.left
import com.codepunk.moviepunk.data.mapper.toMoviePage
import com.codepunk.moviepunk.data.remote.util.toEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.MoviePage
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviePunkRepositoryImpl(
    private val webservice: MoviePunkWebservice
) : MoviePunkRepository {

    // region Methods

    override suspend fun fetchTrendingMovies(): Flow<Either<Exception, MoviePage>> = flow {
        // TODO Incorporate Room, paging, etc.
        try {
            webservice.fetchTrendingMovies()
                .toEither { it.toMoviePage() }
        } catch (e: Exception) {
            e.left()
        }.apply {
            emit(this)
        }
    }

    // endregion Methods

}
