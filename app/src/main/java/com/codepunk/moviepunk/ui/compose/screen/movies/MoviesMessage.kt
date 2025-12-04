package com.codepunk.moviepunk.ui.compose.screen.movies

import com.codepunk.moviepunk.core.CoreMessage

sealed interface MoviesMessage : CoreMessage {
    data object TestMessage : MoviesMessage
}