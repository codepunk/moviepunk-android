package com.codepunk.moviepunk.domain.repository

import com.codepunk.moviepunk.domain.model.ApiStatus
import com.codepunk.moviepunk.util.http.HttpStatus

sealed interface RepoFailure {

    object NoConnectivityFailure : RepoFailure {
        override fun toString(): String {
            return "NoConnectivityFailure"
        }
    }

    open class HttpFailure(
        val httpStatus: HttpStatus
    ) : RepoFailure {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as HttpFailure

            return httpStatus == other.httpStatus
        }

        override fun hashCode(): Int {
            return httpStatus.hashCode()
        }

        override fun toString(): String {
            return "HttpFailure(httpStatus=$httpStatus)"
        }
    }

    class ApiFailure(
        httpStatus: HttpStatus,
        val apiStatus: ApiStatus
    ) : HttpFailure(
        httpStatus = httpStatus
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            if (!super.equals(other)) return false

            other as ApiFailure

            return apiStatus == other.apiStatus
        }

        override fun hashCode(): Int {
            var result = super.hashCode()
            result = 31 * result + apiStatus.hashCode()
            return result
        }

        override fun toString(): String {
            return "ApiFailure(httpStatus=$httpStatus, apiStatus=$apiStatus)"
        }
    }

    class ExceptionFailure(
        val exception: Exception
    ) : RepoFailure {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ExceptionFailure

            return exception == other.exception
        }

        override fun hashCode(): Int {
            return exception.hashCode()
        }

        override fun toString(): String {
            return "ExceptionFailure(exception=$exception)"
        }
    }

}