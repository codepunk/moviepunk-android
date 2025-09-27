package com.codepunk.moviepunk.domain.repository

import androidx.paging.PagingData
import app.cash.quiver.extensions.OutcomeOf
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.HashedImage
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.domain.model.TimeWindow
import kotlinx.coroutines.flow.Flow

interface MoviePunkRepository {

    // region Methods

    fun getGenres(): Flow<OutcomeOf<List<Genre>>>

    fun getMovieGenres(): Flow<OutcomeOf<List<Genre>>>

    fun getTvGenres(): Flow<OutcomeOf<List<Genre>>>

    fun getTrendingMovies(timeWindow: TimeWindow = TimeWindow.DAY): Flow<PagingData<Movie>>

    fun getHashedBackgroundImages(): Flow<OutcomeOf<List<HashedImage>>>

    // endregion Methods

}
