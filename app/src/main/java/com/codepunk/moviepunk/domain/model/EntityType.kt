package com.codepunk.moviepunk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EntityType(val value: String) {
    @SerialName("movie")
    MOVIE("movie"),

    @SerialName("tv")
    TV("tv"),

    @SerialName("person")
    PERSON("person")
}
