package com.codepunk.moviepunk.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CuratedContentItemDto(
    val id: Int = 0,
    val href: String = "",
    val url: String = ""
)
