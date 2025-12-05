package com.codepunk.moviepunk.ui.compose.screen.movies

import com.codepunk.moviepunk.domain.model.CuratedContentItem
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.repository.RepositoryState

data class MoviesState(
    val isConnected: Boolean = false,
    val curatedContentLoading: Boolean = false,
    val curatedContentItem: CuratedContentItem? = null,
    val curatedContentError: RepositoryState? = null,
    val genresLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val genresError: Throwable? = null,
)
