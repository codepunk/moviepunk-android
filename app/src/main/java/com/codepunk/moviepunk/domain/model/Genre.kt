package com.codepunk.moviepunk.domain.model

data class Genre(
    val id: Int = 0,
    val name: String = "",
    val mediaTypes: List<MediaType> = emptyList()
)
