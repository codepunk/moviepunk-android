package com.codepunk.moviepunk.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteMoviePage(
    val page: Int = 0,

    val results: List<RemoteMovie> = emptyList(),

    @SerialName(value = "total_pages")
    val totalPages: Int = 0,

    @SerialName(value = "total_results")
    val totalResults: Long = 0
)
