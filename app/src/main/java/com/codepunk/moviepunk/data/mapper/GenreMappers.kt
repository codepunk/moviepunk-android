package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.remote.dto.GenreDto
import com.codepunk.moviepunk.domain.model.Genre
import kotlin.time.Clock

// region Methods

// ====================
// Dto to entity
// ====================

fun GenreDto.toGenreEntity(
    isMovieGenre: Boolean,
    isTvGenre: Boolean
) = Clock.System.now().let { now ->
    GenreEntity(
        id = id,
        name = name,
        isMovieGenre = isMovieGenre,
        isTvGenre = isTvGenre,
        createdAt = now,
        updatedAt = now
    )
}

/**
 * Convenience method that converts a list of genre IDs to a list of [GenreEntity].
 * As genre name will be empty, this is only used for inserting MovieWithGenres instances
 * into the local database.
 */
fun Int.toGenreEntity(
    isMovieGenre: Boolean = false,
    isTvGenre: Boolean = false
) = GenreEntity(
        id = this,
        name = "",
        isMovieGenre = isMovieGenre,
        isTvGenre = isTvGenre
    )

fun combineToGenreEntities(
    movieGenreDtos: List<GenreDto>,
    tvGenreDtos: List<GenreDto>
): List<GenreEntity> {
    val movieGenreIds = movieGenreDtos.map { it.id }.toSet()
    val tvGenreIds = tvGenreDtos.map { it.id }.toSet()
    val allRemoteGenres = (movieGenreDtos + tvGenreDtos).distinctBy { it.id }
    return allRemoteGenres.map { remoteGenre ->
        remoteGenre.toGenreEntity(
            isMovieGenre = movieGenreIds.contains(remoteGenre.id),
            isTvGenre = tvGenreIds.contains(remoteGenre.id)
        )
    }
}

// ====================
// Entity to domain
// ====================

@Suppress("unused")
fun GenreEntity.toGenre() = Genre(
    id = id,
    name = name
)

// endregion Methods
