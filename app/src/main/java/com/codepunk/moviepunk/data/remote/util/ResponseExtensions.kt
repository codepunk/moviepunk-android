package com.codepunk.moviepunk.data.remote.util

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import com.codepunk.moviepunk.data.mapper.toApiStatus
import com.codepunk.moviepunk.data.remote.response.ApiStatusResponse
import com.codepunk.moviepunk.util.exception.ApiException
import com.codepunk.moviepunk.util.exception.HttpException
import com.codepunk.moviepunk.util.http.HttpStatus
import kotlinx.serialization.json.Json
import okhttp3.Headers
import retrofit2.Response

fun <R> Response<R>.toEither(
    onHeaders: (Headers) -> Unit = {},
    ifUnsuccessful: (HttpStatus, String) -> Exception = { httpStatus, _ ->
        HttpException(httpStatus = httpStatus)
    }
): Either<Exception, R> = either {
    onHeaders(headers())
    if (isSuccessful) {
        val body = body()
        ensureNotNull(body) { IllegalStateException("Body is null") }
    } else {
        val httpStatus: HttpStatus = HttpStatus.of(code())
        val errorBodyString = errorBody()?.string()
        ensureNotNull(errorBodyString) { HttpException(httpStatus = httpStatus) }
        raise(ifUnsuccessful(httpStatus, errorBodyString))
    }
}

fun <R> Response<R>.toApiEither(
    onHeaders: (Headers) -> Unit = {}
): Either<Exception, R> = toEither(
    onHeaders = onHeaders,
    ifUnsuccessful = { httpStatus, errorBodyString ->
        try {
            ApiException(
                httpStatus = httpStatus,
                apiStatus = Json.decodeFromString<ApiStatusResponse>(errorBodyString).toApiStatus()
            )
        } catch (e: Exception) {
            e
        }
    }
)
