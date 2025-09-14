package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.paging.PagingData
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class HomeState(
    val genresLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val genresError: Throwable? = null,

    val trendingMoviesFlow: Flow<PagingData<Movie>> = flowOf(PagingData.empty()),
    /*
    val trendingMoviesLoading: Boolean = false,
    val trendingMovies: PagingData<Movie> = PagingData.empty(),
    val trendingMoviesError: Throwable? = null
     */
)
