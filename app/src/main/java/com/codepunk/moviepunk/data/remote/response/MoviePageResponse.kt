package com.codepunk.moviepunk.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviePageResponse(
    val page: Int = 0,

    val results: List<MovieResponse> = emptyList(),

    @SerialName(value = "total_pages")
    val totalPages: Int = 0,

    @SerialName(value = "total_results")
    val totalResults: Long = 0
)
