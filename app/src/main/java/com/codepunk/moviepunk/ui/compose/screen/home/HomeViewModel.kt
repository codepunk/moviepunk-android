package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: MoviePunkRepository
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
        fetchTrendingMovies()
    }

    // endregion Constructors

    // region Methods

    fun fetchTrendingMovies() {
        viewModelScope.launch(context = ioDispatcher) {
            repository.fetchTrendingMovies().collect {
                state = state.copy()
            }
        }
    }

    // endregion Methods

}
