package com.codepunk.moviepunk.data.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class RemoteGenre(
    val id: Int = 0,
    val name: String = ""
)
