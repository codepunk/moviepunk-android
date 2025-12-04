package com.codepunk.moviepunk.domain.model

data class ApiStatus(
    val success: Boolean = false,
    val statusCode: Int = 0,
    val statusMessage: String = ""
)
