package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.cash.quiver.getOrElse
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.domain.model.TimeWindow
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: MoviePunkRepository,
) : ViewModel() {

    // region Variables

    private val _stateFlow: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val stateFlow = _stateFlow.asStateFlow()

    @Suppress("unused")
    private var state: HomeState
        get() = _stateFlow.value
        set(value) { _stateFlow.value = value }

    // endregion Variables

    // region Constructors

    init {
        getGenres()
        getTrendingMovies()
    }

    // endregion Constructors

    // region Methods

    fun getGenres() {
        viewModelScope.launch(context = ioDispatcher) {
            repository.getGenres().collect { outcome ->
                Timber.d(message = "Genres outcome: $outcome")

                /* If outcome.left has a value, use that
                 * else if right has a value, null
                 * else use existing value
                 */
                state = state.copy(
                    genresLoading = outcome.isAbsent(),
                    genres = outcome.getOrElse { state.genres },
                    genresError = when {
                        outcome.isFailure() -> outcome.inner.leftOrNull()
                        outcome.isPresent() -> null
                        else -> state.genresError
                    }
                )
            }
        }
    }

    fun getTrendingMovies() {
        val trendingMoviesFlow =
            repository.getTrendingMovies(TimeWindow.DAY).cachedIn(viewModelScope)
        state = state.copy(
            trendingMoviesFlow = trendingMoviesFlow
        )
    }

    // endregion Methods

}
