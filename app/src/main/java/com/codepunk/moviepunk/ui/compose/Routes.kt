package com.codepunk.moviepunk.ui.compose

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data object Home : Routes()
}
