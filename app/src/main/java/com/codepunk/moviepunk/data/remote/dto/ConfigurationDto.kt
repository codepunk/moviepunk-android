package com.codepunk.moviepunk.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationDto(
    @SerialName(value = "change_keys")
    val changeKeys: List<String>,

    val images: ConfigurationImagesDto
) {
    @Serializable
    data class ConfigurationImagesDto(
        @SerialName(value = "base_url")
        val baseUrl: String,

        @SerialName(value = "secure_base_url")
        val secureBaseUrl: String,

        @SerialName(value = "backdrop_sizes")
        val backdropSizes: List<String>,

        @SerialName(value = "logo_sizes")
        val logoSizes: List<String>,

        @SerialName(value = "poster_sizes")
        val posterSizes: List<String>,

        @SerialName(value = "profile_sizes")
        val profileSizes: List<String>,

        @SerialName(value = "still_sizes")
        val stillSizes: List<String>,
    )
}
