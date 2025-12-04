package com.codepunk.moviepunk.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiStatusResponse(
    val success: Boolean = false,

    @SerialName(value = "status_code")
    val statusCode: Int = 0,

    @SerialName(value = "status_message")
    val statusMessage: String = ""
)
