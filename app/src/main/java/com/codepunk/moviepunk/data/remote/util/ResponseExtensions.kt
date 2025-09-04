package com.codepunk.moviepunk.data.remote.util

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.codepunk.moviepunk.data.mapper.toApiStatus
import com.codepunk.moviepunk.data.remote.entity.RemoteApiStatus
import com.codepunk.moviepunk.util.exception.ApiException
import com.codepunk.moviepunk.util.exception.HttpException
import com.codepunk.moviepunk.util.http.HttpStatus
import kotlinx.serialization.json.Json
import retrofit2.Response

fun <T> Response<T>.toEither(): Either<Exception, T> =
    if (isSuccessful) {
        body()?.right()
            ?: IllegalStateException("Response code is ${code()} but body is null.").left()
    } else {
        val httpStatus: HttpStatus = HttpStatus.of(code())
        val exception = errorBody()?.string()?.run {
            try {
                val apiStatus = Json.decodeFromString<RemoteApiStatus>(this).toApiStatus()
                ApiException(httpStatus = httpStatus, apiStatus = apiStatus)
            } catch (e: Exception) {
                e
            }
        } ?: HttpException(httpStatus = httpStatus)
        exception.left()
    }
