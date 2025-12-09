package com.codepunk.moviepunk.data.remote.dto

import com.codepunk.moviepunk.domain.model.CuratedContentType
import kotlinx.serialization.Serializable

@Serializable
data class CuratedContentItemDto(
    val label: String = "",
    val type: CuratedContentType = CuratedContentType.UNKNOWN,
    val href: String = "",
    val url: String = ""
)
