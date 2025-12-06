package com.codepunk.moviepunk.ui.compose.screen.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.codepunk.moviepunk.core.CoreViewModel
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.domain.model.Movie
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
//        getGenres()
        getTrendingMovies()
    }

    private fun getCuratedContent() {
        viewModelScope.launch(ioDispatcher) {
            state = state.copy(curatedContentLoading = true)
            val currentId = state.curatedContentItem?.id ?: 0
            val result = repository.getCuratedContent(currentId)
            state = state.copy(curatedContentLoading = false)
            result.onRight { curatedContentItem ->
                state = state.copy(
                    curatedContentItem = curatedContentItem
                )
            }
        }
        /*
        state = state.copy(
            curatedContentLoading = true
        )
        viewModelScope.launch(context = ioDispatcher) {
            repository.getRandomCuratedContentItem().collect { result ->
                state = state.copy(
                    curatedContentLoading = false,
                    curatedContentItem = result.getOrElse { state.curatedContentItem },
                    curatedContentError = result.leftOrNull()
                )
            }
        }
         */
    }

    /*
    fun getGenres() {
        viewModelScope.launch(context = ioDispatcher) {
            state = state.copy(
                genresLoading = true
            )
            repository.getGenres().collect { result ->
                Timber.d(message = "Genres result: $result")
                state = state.copy(
                    genresLoading = false,
                    genres = result.getOrElse { state.genres },
                    genresError = result.leftOrNull()
                )
            }
        }
    }
     */

    private fun getTrendingMovies() {
        /*
        trendingMoviesFlow = repository.getTrendingMovies(TimeWindow.DAY).cachedIn(viewModelScope)
         */
    }

    override fun handleIntent(intent: MoviesIntent) {
        when (intent) {
            MoviesIntent.RefreshIntent -> refresh()
            MoviesIntent.TestIntent -> sendMessage(MoviesMessage.TestMessage)
        }
    }

    // endregion Methods

}
