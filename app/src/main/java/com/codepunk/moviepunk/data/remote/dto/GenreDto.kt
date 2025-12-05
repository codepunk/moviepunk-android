package com.codepunk.moviepunk.data.remote.dto

import com.codepunk.moviepunk.domain.model.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id: Int = 0,
    val name: String = "",
    val mediaTypes: List<MediaType> = emptyList()
)
