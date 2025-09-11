package com.codepunk.moviepunk.data.remote.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteMovie(
    val id: Int = 0,

    val adult: Boolean = false,

    @SerialName(value = "backdrop_path")
    val backdropPath: String = "",

    val title: String = "",

    @SerialName(value = "original_language")
    val originalLanguage: String = "",

    @SerialName(value = "original_title")
    val originalTitle: String = "",

    val overview: String = "",

    @SerialName(value = "poster_path")
    val posterPath: String = "",

    @SerialName(value = "media_type")
    val mediaType: String = "",

    val genreIds: List<Int> = emptyList(),

    val popularity: Double = 0.0,

    @SerialName(value = "release_date")
    val releaseDate: LocalDate? = null,

    val video: Boolean = false,

    @SerialName(value = "vote_average")
    val voteAverage: Double = 0.0,

    @SerialName(value = "vote_count")
    val voteCount: Int = 0
)
