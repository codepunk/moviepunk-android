package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.quiver.fold
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
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
                Timber.d(message = "Genres: $outcome")
                state = outcome.fold(
                    onAbsent = {
                        state.copy(genresLoading = true)
                    },
                    onPresent = { genres ->
                        state.copy(
                            genresLoading = false,
                            genres = genres,
                            genresThrowable = null
                        )
                    },
                    onFailure = { th ->
                        state.copy(
                            genresLoading = false,
                            genres = emptyList(),
                            genresThrowable = th
                        )
                    }
                )
            }
        }
    }

    fun getTrendingMovies() {
        viewModelScope.launch(context = ioDispatcher) {
            repository.getTrendingMovies().collect {
                state = state.copy()
            }
        }
    }

    // endregion Methods

}
