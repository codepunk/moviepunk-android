package com.codepunk.moviepunk.ui.compose.screen.home

import com.codepunk.moviepunk.core.CoreIntent

sealed interface HomeIntent : CoreIntent {

    data object TestIntent : HomeIntent

}
