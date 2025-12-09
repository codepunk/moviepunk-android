package com.codepunk.moviepunk.manager

import arrow.core.Either
import arrow.core.left
import com.codepunk.moviepunk.di.qualifier.ApplicationScope
import com.codepunk.moviepunk.di.qualifier.DefaultDispatcher
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.domain.model.CuratedContentType
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import com.codepunk.moviepunk.domain.repository.RepositoryState
import com.codepunk.moviepunk.domain.repository.RepositoryState.UninitializedState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
    // TODO Make flows that track when each sync is complete. Is there a way to have a retry
    //  on those syncs if they fail?

    private val _syncGenresFlow: MutableStateFlow<Either<RepositoryState, Boolean>> =
        MutableStateFlow(UninitializedState.left())
    val syncGenresFlow = _syncGenresFlow.asStateFlow()

    private val _syncFeaturedContentFlow: MutableStateFlow<Either<RepositoryState, Boolean>> =
        MutableStateFlow(UninitializedState.left())
    val syncFeaturedContentFlow = _syncFeaturedContentFlow.asStateFlow()

    private val _syncCommunityContentFlow: MutableStateFlow<Either<RepositoryState, Boolean>> =
        MutableStateFlow(UninitializedState.left())
    val syncCommunityContentFlow = _syncCommunityContentFlow.asStateFlow()

    init {
        applicationScope.launch(defaultDispatcher) {
            networkConnectionManager.connectionStateFlow.collect { isConnected ->
                if (isConnected) {
                    if (syncGenresFlow.value.isLeft()) {
                        syncGenres()
                    }
                    if (syncFeaturedContentFlow.value.isLeft()) {
                        syncFeaturedContent()
                    }
                    if (syncCommunityContentFlow.value.isLeft()) {
                        syncCommunityContent()
                    }
                }
            }
        }
    }

    private fun syncGenres() {
        applicationScope.launch(ioDispatcher) {
            _syncGenresFlow.value = repository.syncGenres()
        }
    }

    private fun syncFeaturedContent() {
        applicationScope.launch(ioDispatcher) {
            _syncFeaturedContentFlow.value =
                repository.syncCuratedContent(CuratedContentType.FEATURED)
        }
    }

    private fun syncCommunityContent() {
        applicationScope.launch(ioDispatcher) {
            _syncCommunityContentFlow.value =
                repository.syncCuratedContent(CuratedContentType.COMMUNITY)
        }
    }
}