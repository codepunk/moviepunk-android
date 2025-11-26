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

    fun getGenres(): Flow<Either<Exception, List<Genre>>>

    fun getMovieGenres(): Flow<Either<Exception, List<Genre>>>

    fun getTvGenres(): Flow<Either<Exception, List<Genre>>>

    fun getTrendingMovies(timeWindow: TimeWindow = TimeWindow.DAY): Flow<PagingData<Movie>>

    fun getRandomCuratedContentItem(): Flow<Either<Exception, CuratedContentItem?>>

    // endregion Methods

}
