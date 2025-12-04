package com.codepunk.moviepunk.ui.compose

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object Auth: Route()

    @Serializable
    data object Home : Route()

    @Serializable
    data object Movies : Route()

    @Serializable
    data object TvShows : Route()

    @Serializable
    data object People : Route()

    @Serializable
    data object HomeMore : Route()

}
