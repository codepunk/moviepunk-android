package com.codepunk.moviepunk.domain.model

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Long = 0,
    val adult: Boolean = false,
    val backdropPath: String = "",
    val backdropUrl: String = "",
    val title: String = "",
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val posterUrl: String = "",
    val mediaType: String = "",
    val genres: List<Genre> = emptyList(),
    val popularity: Double = 0.0,
    val releaseDate: LocalDate? = null,
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
)
