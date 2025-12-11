package com.codepunk.moviepunk.ui.compose.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepunk.moviepunk.domain.repository.RepositoryState.UninitializedState
import com.codepunk.moviepunk.manager.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    syncManager: SyncManager
) : ViewModel() {

    // region Variables

    private val minimumTimeFlow = MutableStateFlow(false)
    private val maximumTimeFlow = MutableStateFlow(false)

    val syncCompleteFlow = combine(
        syncManager.syncGenresFlow,
        syncManager.syncCuratedContentFlow,
        minimumTimeFlow,
        maximumTimeFlow
    ) {genreResult,
       curatedContentResult,
       minimumTimeReached,
       maximumTimeReached ->
        when {
            !minimumTimeReached -> false
            maximumTimeReached -> true
            genreResult.leftOrNull() == UninitializedState -> false
            curatedContentResult.leftOrNull() == UninitializedState -> false
            else -> true
        }
    }

    // endregion Variables

    // region Constructor

    init {
        startTimers()
    }

    // endregion Constructor

    // region Methods

    fun startTimers() {
        // Minimum time for splash screen
        viewModelScope.launch {
            delay(MINIMUM_SPLASH_TIME)
            minimumTimeFlow.value = true
            delay(MAXIMUM_SPLASH_TIME - MINIMUM_SPLASH_TIME)
            maximumTimeFlow.value = true
        }
    }

    // endregion Methods

    // region Companion object

    private companion object {

        // region Constants

        private const val MINIMUM_SPLASH_TIME = 2500L

        private const val MAXIMUM_SPLASH_TIME = 3500L

        // endregion Constants

    }

    // endregion Companion object

}