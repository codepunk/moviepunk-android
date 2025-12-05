package com.codepunk.moviepunk.domain.repository

import arrow.core.Either
import com.codepunk.moviepunk.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface MoviePunkRepository {

    // region Methods

    /*
    suspend fun syncGenresNew(): Either<RepoFailure, Boolean>
     */

    suspend fun syncGenres(): Either<RepositoryState, Boolean>

    fun getGenres(): Flow<List<Genre>>

    suspend fun syncCuratedContent(): Either<RepositoryState, Boolean>

    /*
    fun getTrendingMovies(timeWindow: TimeWindow = TimeWindow.DAY): Flow<PagingData<Movie>>

    fun getRandomCuratedContentItem(): Flow<Either<RepoFailure, CuratedContentItem?>>
     */

    // endregion Methods

}
