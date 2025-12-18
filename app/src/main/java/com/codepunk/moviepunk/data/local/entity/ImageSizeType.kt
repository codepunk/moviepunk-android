package com.codepunk.moviepunk.data.local.entity

import kotlinx.serialization.SerialName

enum class ImageSizeType(val value: String) {
    @SerialName("backdrop")
    BACKDROP("backdrop"),

    @SerialName("logo")
    LOGO("logo"),

    @SerialName("poster")
    POSTER("poster"),

    @SerialName("profile")
    PROFILE("profile"),

    @SerialName("still")
    STILL("still"),

    @SerialName("unknown")
    UNKNOWN("unknown");

    // region Companion object

    companion object {
        fun of(value: String): ImageSizeType = ImageSizeType.entries.find {
            it.value.equals(other = value, ignoreCase = true)
        } ?: UNKNOWN
    }

    // endregion Companion object
}
