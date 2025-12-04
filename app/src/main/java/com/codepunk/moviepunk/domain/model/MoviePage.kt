package com.codepunk.moviepunk.domain.model

data class MoviePage(
    val page: Int = 0,
    val results: List<Movie> = emptyList(),
    val totalPages: Int = 0,
    val totalResults: Int = 0
)
