package com.codepunk.moviepunk.manager

import arrow.core.Either
import arrow.core.left
import com.codepunk.moviepunk.di.qualifier.ApplicationScope
import com.codepunk.moviepunk.di.qualifier.DefaultDispatcher
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.domain.model.Configuration
import com.codepunk.moviepunk.domain.model.CuratedContentItem
import com.codepunk.moviepunk.domain.model.Genre
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

class ConfigurationManager @Inject constructor(
    @param:ApplicationScope private val applicationScope: CoroutineScope,
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    networkConnectionManager: NetworkConnectionManager,
    private val repository: MoviePunkRepository
) {
    // TODO Make flows that track when each sync is complete. Is there a way to have a retry
    //  on those syncs if they fail?

    private val _genresFlow: MutableStateFlow<Either<RepositoryState, List<Genre>>> =
        MutableStateFlow(UninitializedState.left())
    val genresFlow = _genresFlow.asStateFlow()

    private val _configurationFlow: MutableStateFlow<Either<RepositoryState, Configuration>> =
        MutableStateFlow(UninitializedState.left())
    val configurationFlow = _configurationFlow.asStateFlow()

    private val _curatedContentFlow: MutableStateFlow<Either<RepositoryState, List<CuratedContentItem>>> =
        MutableStateFlow(UninitializedState.left())
    val curatedContentFlow = _curatedContentFlow.asStateFlow()

    init {
        applicationScope.launch(defaultDispatcher) {
            networkConnectionManager.connectionStateFlow.collect { isConnected ->
                if (isConnected) {
                    if (genresFlow.value.isLeft()) {
                        getGenres()
                    }
                    if (configurationFlow.value.isLeft()) {
                        getConfiguration()
                    }
                    if (curatedContentFlow.value.isLeft()) {
                        getCuratedContent()
                    }
                }
            }
        }
    }

    private fun getGenres() {
        applicationScope.launch(ioDispatcher) {
            _genresFlow.value = repository.getGenres()
        }
    }

    private fun getConfiguration() {
        applicationScope.launch(ioDispatcher) {
            _configurationFlow.value = repository.getConfiguration()
        }
    }

    private fun getCuratedContent() {
        applicationScope.launch(ioDispatcher) {
            _curatedContentFlow.value = repository.getCuratedContent()
        }
    }

}