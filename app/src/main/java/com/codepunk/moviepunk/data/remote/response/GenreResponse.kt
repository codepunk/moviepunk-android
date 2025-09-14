package com.codepunk.moviepunk.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val id: Int = 0,
    val name: String = ""
)
