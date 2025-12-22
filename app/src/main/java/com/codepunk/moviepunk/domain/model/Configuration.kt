package com.codepunk.moviepunk.domain.model

data class Configuration(
    val changeKeys: List<String> = emptyList(),
    val images: ConfigurationImages = ConfigurationImages()
) {
    data class ConfigurationImages(
        val baseUrl: String = "",
        val secureBaseUrl: String = "",
        val backdropSizes: List<String> = emptyList(),
        val logoSizes: List<String> = emptyList(),
        val posterSizes: List<String> = emptyList(),
        val profileSizes: List<String> = emptyList(),
        val stillSizes: List<String> = emptyList(),
    )
}
