package com.codepunk.moviepunk.ui.compose.screen.home

import com.codepunk.moviepunk.core.CoreIntent
import com.codepunk.moviepunk.core.UserIntent

sealed interface HomeIntent : CoreIntent {

    data object ShowMovies : HomeIntent, UserIntent {
        override val eventName: String = "show_movies"
    }

    data object ShowTvShows : HomeIntent, UserIntent {
        override val eventName: String = "show_tv_shows"
    }

    data object ShowPeople : HomeIntent, UserIntent {
        override val eventName: String = "show_people"
    }

    data object ShowMore : HomeIntent, UserIntent {
        override val eventName: String = "show_more"
    }

}
