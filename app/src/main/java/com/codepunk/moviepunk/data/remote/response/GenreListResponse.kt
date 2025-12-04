package com.codepunk.moviepunk.data.remote.response

import com.codepunk.moviepunk.data.remote.dto.GenreDto
import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponse(
    val genres: List<GenreDto> = emptyList()
)