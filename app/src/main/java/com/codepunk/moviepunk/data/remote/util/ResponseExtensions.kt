package com.codepunk.moviepunk.data.remote.util

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.codepunk.moviepunk.data.mapper.toApiStatus
import com.codepunk.moviepunk.data.remote.entity.RemoteApiStatus
import com.codepunk.moviepunk.domain.model.ApiStatus
import com.codepunk.moviepunk.util.exception.ApiException
import com.codepunk.moviepunk.util.exception.HttpException
import com.codepunk.moviepunk.util.http.HttpStatus
import kotlinx.serialization.json.Json
import retrofit2.Response

fun <T, M> Response<T>.toEither(
    map: (T) -> M
): Either<Exception, M> =
    if (isSuccessful) {
        body()?.let { map(it) }?.right()
            ?: IllegalStateException("Response code is ${code()} but body is null.").left()
    } else {
        val httpStatus: HttpStatus = HttpStatus.of(code())
        val apiStatus: ApiStatus? = errorBody()?.string()?.run {
            try {
                Json.decodeFromString<RemoteApiStatus>(this)
            } catch (_: Exception) {
                null
            }
        }?.toApiStatus()

        val exception = apiStatus?.run {
            ApiException(httpStatus = httpStatus, apiStatus = apiStatus)
        } ?: HttpException(httpStatus = httpStatus)
        exception.left()
    }
