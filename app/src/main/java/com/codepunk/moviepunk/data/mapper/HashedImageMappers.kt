package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.HashedImageEntity
import com.codepunk.moviepunk.data.remote.dto.HashedImageDto

// region Methods

// ====================
// Dto to entity
// ====================

fun HashedImageDto.toHashedImageEntities(): List<HashedImageEntity> =
    imageUrls.toList().mapIndexed { index, (density, urlString) ->
        HashedImageEntity(
            hash = hash,
            id = index,
            density = density,
            urlString = urlString
        )
    }

// ====================
// Entity to domain
// ====================

