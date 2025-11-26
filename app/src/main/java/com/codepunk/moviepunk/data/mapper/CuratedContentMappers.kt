package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.CuratedContentItemEntity
import com.codepunk.moviepunk.data.remote.dto.CuratedContentItemDto
import com.codepunk.moviepunk.domain.model.CuratedContentItem

// ====================
// Remote to entity
// ====================

fun CuratedContentItemDto.toCuratedContentItemEntity() =
    CuratedContentItemEntity(
        id = id,
        href = href,
        url = url
    )

// ====================
// Entity to domain
// ====================

fun CuratedContentItemEntity.toCuratedContentItem() =
    CuratedContentItem(
        id = id,
        href = href,
        url = url
    )
