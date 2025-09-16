package com.codepunk.moviepunk.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id: Int = 0,
    val name: String = ""
)
