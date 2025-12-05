package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.CuratedContentItemEntity
import com.codepunk.moviepunk.data.remote.dto.CuratedContentItemDto
import com.codepunk.moviepunk.domain.model.CuratedContentItem

// ====================
// Dto to entity
// ====================

fun CuratedContentItemDto.toEntity() = CuratedContentItemEntity(
    id = id,
    href = href,
    url = url
)

// ====================
// Entity to domain
// ====================

fun CuratedContentItemEntity.toModel() = CuratedContentItem(
    id = id,
    href = href,
    url = url
)