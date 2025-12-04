package com.codepunk.moviepunk.ui.compose.screen.home

import com.codepunk.moviepunk.R

enum class HomeNavItem(
    val iconRes: Int,
    val labelRes: Int,
    val contentDescriptionRes: Int,
    val intent: HomeIntent
) {

    MOVIES(
        iconRes = R.drawable.ic_movies_black_24,
        labelRes = R.string.movies,
        contentDescriptionRes = R.string.movies,
        intent = HomeIntent.ShowMovies
    ),

    TV_SHOWS(
        iconRes = R.drawable.ic_tv_shows_black_24,
        labelRes = R.string.tv_shows,
        contentDescriptionRes = R.string.tv_shows,
        intent = HomeIntent.ShowTvShows
    ),

    PEOPLE(
        iconRes = R.drawable.ic_people_black_24,
        labelRes = R.string.people,
        contentDescriptionRes = R.string.people,
        intent = HomeIntent.ShowPeople
    ),

    MORE(
        iconRes = R.drawable.ic_more_black_24,
        labelRes = R.string.more,
        contentDescriptionRes = R.string.more,
        intent = HomeIntent.ShowMore
    ),

}
