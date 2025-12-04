package com.codepunk.moviepunk.manager

import com.codepunk.moviepunk.di.qualifier.ApplicationScope
import com.codepunk.moviepunk.di.qualifier.DefaultDispatcher
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

// TODO When are we "done"? Maybe a bunch of flows for each sync and one "master" flow
//   that combines them all? Is that overkill?

class SyncManager @Inject constructor(
    @param:ApplicationScope private val applicationScope: CoroutineScope,
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    networkConnectionManager: NetworkConnectionManager,
    private val repository: MoviePunkRepository
) {
    private val _completeFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val completeFlow: StateFlow<Boolean> = _completeFlow.asStateFlow()

    // TODO Make flows that track when each sync is complete. Is there a way to have a retry
    //  on those syncs if they fail?

    init {
        applicationScope.launch(defaultDispatcher) {
            networkConnectionManager.connectionStateFlow.collect { isConnected ->
                if (isConnected) {
                    syncGenres()
                    syncCuratedContent()
                }
            }
        }
    }

    private fun syncGenres() {
        applicationScope.launch(ioDispatcher) {
            /*
            repository.syncGenres().collect {
                Timber.i("syncGenres result=$it")
            }
             */
        }
    }

    private fun syncCuratedContent() {
        applicationScope.launch(ioDispatcher) {
            repository.syncCuratedContent().collect {
                Timber.i("syncCuratedContent result=$it")
            }
        }
    }
}