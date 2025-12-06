package com.codepunk.moviepunk.data.repository

import kotlinx.coroutines.flow.*

suspend inline fun <Entity, Dto, Model> networkBoundResource(
    crossinline query: () -> Flow<Entity>,
    crossinline fetch: suspend () -> Dto,
    crossinline shouldFetch: suspend (Entity) -> Boolean,
    crossinline saveFetchResult: suspend (Dto) -> Unit,
    crossinline transform: (Entity) -> Model
): Model {
    val initial = query().first()
    val cached = initial.run {
        if (shouldFetch(this)) {
            val fetched = fetch()
            saveFetchResult(fetched)
            query().first()
        } else {
            this
        }
    }
    return transform(cached)
}
