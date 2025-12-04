package com.codepunk.moviepunk.data.remote.util

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import com.codepunk.moviepunk.data.mapper.toApiStatus
import com.codepunk.moviepunk.data.remote.response.ApiStatusResponse
import com.codepunk.moviepunk.domain.repository.RepoFailure
import com.codepunk.moviepunk.domain.repository.RepoFailure.*
import com.codepunk.moviepunk.util.http.HttpStatus
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.Headers
import retrofit2.Response

fun <R> Response<R>.toEither(
    onHeaders: (Headers) -> Unit = {},
    onFailure: (HttpStatus, String) -> RepoFailure = { httpStatus, _ ->
        HttpFailure(httpStatus = httpStatus)
    }
): Either<RepoFailure, R> = either {
    onHeaders(headers())
    val httpStatus: HttpStatus = HttpStatus.of(code())
    if (isSuccessful) {
        ensureNotNull(body()) {
            ExceptionFailure(RuntimeException("Body is null"))
        }
    } else {
        val errorBodyString = errorBody()?.string()
        ensureNotNull(errorBodyString) { HttpFailure(httpStatus = httpStatus) }
        raise(onFailure(httpStatus, errorBodyString))
    }
}

fun <R> Response<R>.toApiEither(
    onHeaders: (Headers) -> Unit = {}
): Either<RepoFailure, R> = toEither(
    onHeaders = onHeaders,
    onFailure = { httpStatus, errorBodyString ->
        try {
            ApiFailure(
                httpStatus = httpStatus,
                apiStatus = Json.decodeFromString<ApiStatusResponse>(errorBodyString).toApiStatus()
            )
        } catch (_: SerializationException) {
            HttpFailure(httpStatus = httpStatus)
        }
    }
)
