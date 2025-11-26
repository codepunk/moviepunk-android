package com.codepunk.moviepunk.ui.compose.screen.home

import com.codepunk.moviepunk.domain.model.CuratedContentItem
import com.codepunk.moviepunk.domain.model.Genre

data class HomeState(
    val curatedContentLoading: Boolean = false,
    val curatedContentItem: CuratedContentItem? = null,
    val curatedContentError: Throwable? = null,
    val genresLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val genresError: Throwable? = null,
)
