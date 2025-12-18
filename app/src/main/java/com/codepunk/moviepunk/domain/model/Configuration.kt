package com.codepunk.moviepunk.domain.model

data class Configuration(
    val changeKeys: List<String>,
    val images: ConfigurationImages
) {
    data class ConfigurationImages(
        val baseUrl: String,
        val secureBaseUrl: String,
        val backdropSizes: List<String>,
        val logoSizes: List<String>,
        val posterSizes: List<String>,
        val profileSizes: List<String>,
        val stillSizes: List<String>,
    )
}
