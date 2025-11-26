package com.codepunk.moviepunk.ui.compose.screen.home

import android.net.ConnectivityManager
import android.net.NetworkRequest
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import arrow.core.getOrElse
import com.codepunk.moviepunk.core.CoreViewModel
import com.codepunk.moviepunk.di.qualifier.InternetRequest
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.domain.model.TimeWindow
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: MoviePunkRepository,
    @InternetRequest networkRequest: NetworkRequest,
    connectivityManager: ConnectivityManager,
) : CoreViewModel<HomeIntent, HomeMessage>(
    networkRequest = networkRequest,
    connectivityManager = connectivityManager
) {

    // region Variables

    private val _stateFlow: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val stateFlow = _stateFlow.asStateFlow()

    var trendingMoviesFlow: Flow<PagingData<Movie>> = flowOf(PagingData.empty())
        private set

    @Suppress("unused")
    private var state: HomeState
        get() = _stateFlow.value
        set(value) { _stateFlow.value = value }

    // endregion Variables

    // region Constructors

    init {
        getCuratedContent()
        getGenres()
        getTrendingMovies()
    }

    // endregion Constructors

    // region Methods

    fun getCuratedContent() {
        viewModelScope.launch(context = ioDispatcher) {
            val curatedBackgroundsFlow = repository.getRandomCuratedContentItem()
            curatedBackgroundsFlow.collect { outcome ->
                // TODO
            }
        }
    }

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

    fun getTrendingMovies() {
        trendingMoviesFlow = repository.getTrendingMovies(TimeWindow.DAY).cachedIn(viewModelScope)
    }

    override fun sendIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.TestIntent -> {
                viewModelScope.launch {
                    sendMessage(HomeMessage.TestMessage)
                }
            }
        }
    }

    // endregion Methods

}
