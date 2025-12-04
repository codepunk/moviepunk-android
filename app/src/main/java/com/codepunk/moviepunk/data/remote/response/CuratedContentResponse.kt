package com.codepunk.moviepunk.data.remote.response

import com.codepunk.moviepunk.data.remote.dto.CuratedContentItemDto
import kotlinx.serialization.Serializable

@Serializable
data class CuratedContentResponse(
    val content: List<CuratedContentItemDto> = emptyList()
)