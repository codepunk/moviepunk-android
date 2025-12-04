package com.codepunk.moviepunk.data.repository

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

inline fun <LocalResult, RemoteResult, DomainResult> networkBoundResource(
    coroutineContext: CoroutineContext = Dispatchers.IO,
    crossinline query: FlowCollector<DomainResult>.() -> Flow<LocalResult>,
    crossinline fetch: suspend FlowCollector<DomainResult>.() -> RemoteResult,
    crossinline saveFetchResult: suspend FlowCollector<DomainResult>.(RemoteResult) -> Unit,
    crossinline shouldFetch: suspend FlowCollector<DomainResult>.(LocalResult) -> Boolean = { true },
    crossinline shouldEmitInitialData: (LocalResult) -> Boolean = { true },
    crossinline transform: (LocalResult) -> DomainResult
): Flow<DomainResult> = flow {
    // 1. Get the first emission from the DB Flow immediately
    val query = query(this)
    val initialData = query.first()

    // 2. Decide whether to fetch from the network
    if (shouldFetch(initialData)) {
        if (shouldEmitInitialData(initialData)) {
            // 3. Emit any cached data first
            emit(transform(initialData))
        }

        // 4. Fetch the latest data from the network
        val networkResult = fetch(this)

        // 5. Save fetched data to local DB
         saveFetchResult(networkResult)
    } else {
        false
    }

    emitAll(
        query.map { transform(it) }
    )
}.distinctUntilChanged(
).flowOn(coroutineContext)

suspend inline fun <LocalResult, RemoteResult, DomainResult> networkBoundResultOld(
    crossinline query: () -> Flow<LocalResult>,
    crossinline fetch: suspend () -> RemoteResult,
    crossinline saveFetchResult: suspend (RemoteResult) -> DomainResult,
    crossinline shouldFetch: suspend (LocalResult) -> Boolean,
    crossinline transform: (LocalResult) -> DomainResult
): DomainResult {
    // 1. Get the first emission from the DB flow immediately
    val query = query()
    val initialData = query.first()

    // 2. Decide whether to fetch from the network
    return if (shouldFetch(initialData)) {
        // 4. Fetch the latest data from the network
        val networkResult = fetch()

        // 5. Save fetched data to local DB
        val saveResult = saveFetchResult(networkResult)
        saveResult

        // We're close. When I save the result, if it's successful,
        // I want to query and transform the data again. Sorta.
        // In the case of syncing I only want to return true if we actually
        // updated the data.

        /*
        // TODO if saveResult is failure, return it
        //  otherwise return the below

        // 6. Return transformed data
        val updatedData = query.first()
        transform(updatedData)
         */
    } else {
        transform(initialData)
    }
}

suspend inline fun <Error, Local, Remote, Domain> networkBoundResult(
    crossinline query: Raise<Error>.() -> Flow<Local>,
    crossinline fetch: suspend Raise<Error>.() -> Remote,
    crossinline shouldFetch: suspend Raise<Error>.(Local) -> Boolean,
    crossinline saveFetchResult: suspend Raise<Error>.(Remote) -> Unit,
    crossinline transform: Raise<Error>.(Local) -> Domain
): Either<Error, Domain> = either {
    query().first().let { local ->
        if (shouldFetch(local)) {
            saveFetchResult(fetch())
            query().first()
        } else {
            local
        }
    }.let { local ->
        transform(local)
    }
}
