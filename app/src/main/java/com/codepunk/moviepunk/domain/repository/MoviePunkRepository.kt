package com.codepunk.moviepunk.domain.repository

import arrow.core.Either
import kotlinx.coroutines.flow.Flow

interface MoviePunkRepository {

    // region Methods

    /*
    suspend fun syncGenresNew(): Either<RepoFailure, Boolean>

    fun syncGenres(): Flow<Either<RepoFailure, Boolean>>
     */

    fun syncCuratedContent(): Flow<Either<RepoFailure, Boolean>>

    /*
    fun getTrendingMovies(timeWindow: TimeWindow = TimeWindow.DAY): Flow<PagingData<Movie>>

    fun getRandomCuratedContentItem(): Flow<Either<RepoFailure, CuratedContentItem?>>
     */

    // endregion Methods

}
