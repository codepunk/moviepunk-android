package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.LocalGenre
import com.codepunk.moviepunk.data.remote.entity.RemoteGenre
import com.codepunk.moviepunk.data.remote.entity.RemoteGenreListResult
import com.codepunk.moviepunk.domain.model.Genre
import kotlin.time.Clock

// region Methods

// ====================
// Remote to local
// ====================

fun RemoteGenre.toLocal(
    isMovieGenre: Boolean,
    isTvGenre: Boolean
) = Clock.System.now().let { now ->
    LocalGenre(
        id = id,
        name = name,
        isMovieGenre = isMovieGenre,
        isTvGenre = isTvGenre,
        createdAt = now,
        updatedAt = now
    )
}

fun combineToLocal(
    movieResult: RemoteGenreListResult,
    tvResult: RemoteGenreListResult
): List<LocalGenre> {
    val movieGenreIds = movieResult.genres.map { it.id }.toSet()
    val tvGenreIds = tvResult.genres.map { it.id }.toSet()
    val allRemoteGenres = (movieResult.genres + tvResult.genres).distinctBy { it.id }
    return allRemoteGenres.map { remoteGenre ->
        remoteGenre.toLocal(
            isMovieGenre = movieGenreIds.contains(remoteGenre.id),
            isTvGenre = tvGenreIds.contains(remoteGenre.id)
        )
    }
}

// ====================
// Local to domain
// ====================

@Suppress("unused")
fun LocalGenre.toDomain() = Genre(
    id = id,
    name = name
)

// endregion Methods
