package com.codepunk.moviepunk.ui.compose.screen.home

import com.codepunk.moviepunk.core.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : CoreViewModel<HomeIntent, HomeMessage>() {

    // region Methods

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.ShowMovies -> sendMessage(HomeMessage.NavigateToMovies)
            HomeIntent.ShowTvShows -> sendMessage(HomeMessage.NavigateToTvShows)
            HomeIntent.ShowPeople -> sendMessage(HomeMessage.NavigateToPeople)
            HomeIntent.ShowMore -> sendMessage(HomeMessage.NavigateToMore)
        }
    }

    // endregion Methods

}