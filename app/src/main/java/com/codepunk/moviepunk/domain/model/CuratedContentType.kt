package com.codepunk.moviepunk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CuratedContentType(val value: String) {
    @SerialName("featured")
    FEATURED("featured"),

    @SerialName("community")
    COMMUNITY("community"),

    @SerialName("unknown")
    UNKNOWN("unknown");

    // region Companion object

    companion object {
        fun of(value: String): CuratedContentType = entries.find {
            it.value.equals(other = value, ignoreCase = true)
        } ?: UNKNOWN
    }

    // endregion Companion object

}
