package com.codepunk.moviepunk.data.local.entity

enum class ConfigurationKey(val value: String) {
    CHANGE_KEYS("change_keys"),

    IMAGES_BASE_URL("images.base_url"),

    IMAGES_SECURE_BASE_URL("images.secure_base_url"),

    IMAGES_BACKDROP_SIZES("images.backdrop_sizes"),

    IMAGES_LOGO_SIZES("images.logo_sizes"),

    IMAGES_POSTER_SIZES("images.poster_sizes"),

    IMAGES_PROFILE_SIZES("images.profile_sizes"),

    IMAGES_STILL_SIZES("images.still_sizes"),

    UNKNOWN("unknown");

    // region Companion object

    companion object {
        fun of(value: String): ConfigurationKey = ConfigurationKey.entries.find {
            it.value.equals(other = value, ignoreCase = true)
        } ?: UNKNOWN
    }

    // endregion Companion object
}
