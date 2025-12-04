package com.codepunk.moviepunk.ui.compose.screen.movies

import com.codepunk.moviepunk.core.CoreIntent

sealed interface MoviesIntent : CoreIntent {

    data object TestIntent : MoviesIntent

}
