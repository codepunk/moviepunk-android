package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.local.entity.GenreMediaTypeEntity
import com.codepunk.moviepunk.data.local.relation.GenreWithMediaTypes
import com.codepunk.moviepunk.data.remote.dto.GenreDto
import com.codepunk.moviepunk.domain.model.Genre

// ====================
// Entity to model
// ====================

fun GenreWithMediaTypes.toModel(): Genre = Genre(
    id = genre.id,
    name = genre.name,
    mediaTypes = mediaTypes.map { it.mediaType }
)

// ====================
// DTO to entity
// ====================

fun GenreDto.toEntity(): GenreWithMediaTypes = GenreWithMediaTypes(
    genre = GenreEntity(
        id = id,
        name = name
    ),
    mediaTypes = mediaTypes.map {
        GenreMediaTypeEntity(
            genreId = id,
            mediaType = it
        )
    }
)
