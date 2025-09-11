package com.codepunk.moviepunk.ui.compose.screen.home

import com.codepunk.moviepunk.domain.model.Genre

data class HomeState(
    val genresLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val genresError: Throwable? = null
)
