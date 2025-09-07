package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.LocalGenre
import com.codepunk.moviepunk.data.local.entity.LocalMovieGenre
import com.codepunk.moviepunk.data.local.entity.LocalTvGenre
import com.codepunk.moviepunk.data.remote.entity.RemoteGenre
import com.codepunk.moviepunk.domain.model.Genre
import kotlin.time.Clock

// region Methods

// ====================
// Remote to local
// ====================

fun RemoteGenre.toLocalGenre() = Clock.System.now().let { now ->
    LocalGenre(
        id = id,
        name = name,
        createdAt = now,
        updatedAt = now
    )
}

@Suppress("unused")
fun RemoteGenre.toLocalMovieGenre() = LocalMovieGenre(id = id)

@Suppress("unused")
fun RemoteGenre.toLocalTvGenre() = LocalTvGenre(id = id)

// ====================
// Local cast
// ====================

fun LocalGenre.toLocalMovieGenre() = LocalMovieGenre(id = id)

fun LocalGenre.toLocalTvGenre() = LocalTvGenre(id = id)

// ====================
// Local to domain
// ====================

@Suppress("unused")
fun LocalGenre.toGenre() = Genre(
    id = id,
    name = name
)

// endregion Methods
