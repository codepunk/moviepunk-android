package com.codepunk.moviepunk.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponse(
    val genres: List<GenreResponse> = emptyList()
)
