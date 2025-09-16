package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.remote.response.ApiStatusResponse
import com.codepunk.moviepunk.domain.model.ApiStatus

fun ApiStatusResponse.toModel() = ApiStatus(
    success = success,
    statusCode = statusCode,
    statusMessage = statusMessage
)
