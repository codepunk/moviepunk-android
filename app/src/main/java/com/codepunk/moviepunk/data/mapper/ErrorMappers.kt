package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.remote.entity.RemoteApiError
import com.codepunk.moviepunk.domain.model.ApiError

fun RemoteApiError.toApiError() = ApiError(
    success = success,
    statusCode = statusCode,
    statusMessage = statusMessage
)
