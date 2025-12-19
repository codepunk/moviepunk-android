package com.codepunk.moviepunk.domain.repository

import androidx.paging.PagingData
import arrow.core.Either
import com.codepunk.moviepunk.domain.model.CuratedContentItem
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.domain.model.TimeWindow
import kotlinx.coroutines.flow.Flow

interface MoviePunkRepository {

    // region Methods

    suspend fun syncConfiguration(): Either<RepositoryState, Boolean>

    suspend fun syncGenres(): Either<RepositoryState, Boolean>

    fun getGenres(): Flow<List<Genre>>

    suspend fun syncCuratedContent(): Either<RepositoryState, Boolean>

    suspend fun getCuratedContent(): Flow<List<CuratedContentItem>>

    suspend fun getFeaturedContent(currentId: Int): Either<RepositoryState, CuratedContentItem?>

    fun getTrendingMovies(
        timeWindow: TimeWindow = TimeWindow.DAY,
        pageLimit: Int = 0
    ): Flow<PagingData<Movie>>

    // endregion Methods

}
