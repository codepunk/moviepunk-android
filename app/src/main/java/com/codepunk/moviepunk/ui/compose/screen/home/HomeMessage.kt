package com.codepunk.moviepunk.ui.compose.screen.home

import com.codepunk.moviepunk.core.CoreMessage

sealed interface HomeMessage : CoreMessage {
    data object TestMessage : HomeMessage
}