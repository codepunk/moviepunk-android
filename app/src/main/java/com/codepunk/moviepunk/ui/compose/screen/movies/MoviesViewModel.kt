package com.codepunk.moviepunk.ui.compose.screen.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codepunk.moviepunk.core.CoreViewModel
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.domain.model.TimeWindow
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import com.codepunk.moviepunk.manager.NetworkConnectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * TODO It seems that MainScreen / MoviesScreen is instantiated when the splash screen is
 *  still active. So we need a way to fetch these things when the sync is done.
 *  I think the way to do that is to add a "sync complete" flow to the SyncManager and observe
 *  that here and call refresh() when it's done.
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val networkConnectionManager: NetworkConnectionManager,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: MoviePunkRepository
) : CoreViewModel<MoviesIntent, MoviesMessage>() {

    // region Variables

    private val _stateFlow: MutableStateFlow<MoviesState> = MutableStateFlow(MoviesState())
    val stateFlow = _stateFlow.asStateFlow()

    var trendingMoviesFlow: Flow<PagingData<Movie>> = flowOf(PagingData.empty())
        private set

    private var state: MoviesState
        get() = _stateFlow.value
        set(value) { _stateFlow.value = value }

    // endregion Variables

    // region Constructors

    init {
        monitorNetworkConnection()
        refresh()
    }

    // endregion Constructors

    // region Methods

    private fun monitorNetworkConnection() {
        viewModelScope.launch {
            networkConnectionManager.connectionStateFlow.collect { isConnected ->
                state = state.copy(
                    isConnected = isConnected
                )
            }
        }
    }

    private fun refresh() {
        getCuratedContent()
        getTrendingMovies()
    }

    private fun getCuratedContent() {
        viewModelScope.launch(ioDispatcher) {
            state = state.copy(curatedContentLoading = true)
            val currentId = state.featuredContentItem?.id ?: 0
            val result = repository.getFeaturedContent(currentId)
            state = state.copy(curatedContentLoading = false)
            result.onRight { curatedContentItem ->
                state = state.copy(
                    featuredContentItem = curatedContentItem
                )
            }
        }
    }

    private fun getTrendingMovies() {
        trendingMoviesFlow = repository.getTrendingMovies(
            TimeWindow.DAY,
            pageLimit = 1
        ).cachedIn(viewModelScope)
    }

    override fun handleIntent(intent: MoviesIntent) {
        when (intent) {
            MoviesIntent.RefreshIntent -> refresh()
            MoviesIntent.TestIntent -> sendMessage(MoviesMessage.TestMessage)
        }
    }

    // endregion Methods

}
