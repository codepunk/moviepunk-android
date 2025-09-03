package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.remote.entity.RemoteApiStatus
import com.codepunk.moviepunk.domain.model.ApiStatus

fun RemoteApiStatus.toApiStatus() = ApiStatus(
    success = success,
    statusCode = statusCode,
    statusMessage = statusMessage
)
