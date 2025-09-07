package com.codepunk.moviepunk.data.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class RemoteGenreListResult(
    val genres: List<RemoteGenre> = emptyList()
)
