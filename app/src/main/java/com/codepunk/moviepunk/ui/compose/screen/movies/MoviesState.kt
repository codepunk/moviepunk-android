package com.codepunk.moviepunk.ui.compose.screen.movies

import com.codepunk.moviepunk.domain.model.Configuration
import com.codepunk.moviepunk.domain.model.CuratedContentItem
import com.codepunk.moviepunk.domain.repository.RepositoryState

data class MoviesState(
    val configuration: Configuration = Configuration(),
    val isConnected: Boolean = false,
    val isLoadingAny: Boolean = false,
    val curatedContentLoading: Boolean = false,
    val featuredContentItem: CuratedContentItem? = null,
    val curatedContentError: RepositoryState? = null,
)
