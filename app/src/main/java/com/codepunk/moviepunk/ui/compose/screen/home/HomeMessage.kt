package com.codepunk.moviepunk.ui.compose.screen.home

import com.codepunk.moviepunk.core.CoreMessage
import com.codepunk.moviepunk.ui.compose.Route

sealed class HomeMessage(
    val route: Route
) : CoreMessage {

    data object NavigateToMovies : HomeMessage(
        route = Route.Movies
    )

    data object NavigateToTvShows : HomeMessage(
        route = Route.TvShows
    )

    data object NavigateToPeople : HomeMessage(
        route = Route.People
    )

    data object NavigateToMore : HomeMessage(
        route = Route.HomeMore
    )

}
