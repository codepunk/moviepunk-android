package com.codepunk.moviepunk.domain.repository

import androidx.paging.PagingData
import arrow.core.Either
import com.codepunk.moviepunk.domain.model.Configuration
import com.codepunk.moviepunk.domain.model.CuratedContentItem
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.domain.model.TimeWindow
import kotlinx.coroutines.flow.Flow

interface MoviePunkRepository {

    // region Methods

    suspend fun getConfiguration(): Either<RepositoryState, Configuration>

    suspend fun getCuratedContent(): Either<RepositoryState, List<CuratedContentItem>>

    suspend fun getFeaturedContentItem(currentId: Int): Either<RepositoryState, CuratedContentItem?>

    suspend fun getGenres(): Either<RepositoryState, List<Genre>>

    fun getTrendingMovies(
        timeWindow: TimeWindow = TimeWindow.DAY,
        pageLimit: Int = 0
    ): Flow<PagingData<Movie>>

    // endregion Methods

}
