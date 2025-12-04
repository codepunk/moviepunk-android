package com.codepunk.moviepunk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MediaType(val value: String) {
    @SerialName("movie")
    MOVIE("movie"),

    @SerialName("tv")
    TV("tv"),

    @SerialName("person")
    PERSON("person"),

    @SerialName("unknown")
    UNKNOWN("unknown");

    // region Companion object

    companion object {
        fun of(value: String): MediaType = entries.find {
            it.value.equals(other = value, ignoreCase = true)
        } ?: UNKNOWN
    }

    // endregion Companion object

}
