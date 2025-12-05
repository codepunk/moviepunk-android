package com.codepunk.moviepunk.domain.repository

import com.codepunk.moviepunk.domain.model.ApiStatus
import com.codepunk.moviepunk.util.http.HttpStatus

sealed interface RepositoryState {

    object UninitializedState : RepositoryState {
        override fun toString(): String = "UninitializedStatus"
    }

    object NoConnectivityState : RepositoryState {
        override fun toString(): String = "NoConnectivityFailure"
    }

    open class HttpState(
        val httpStatus: HttpStatus
    ) : RepositoryState {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as HttpState

            return httpStatus == other.httpStatus
        }

        override fun hashCode(): Int {
            return httpStatus.hashCode()
        }

        override fun toString(): String {
            return "HttpFailure(httpStatus=$httpStatus)"
        }
    }

    class ApiState(
        httpStatus: HttpStatus,
        val apiStatus: ApiStatus
    ) : HttpState(
        httpStatus = httpStatus
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            if (!super.equals(other)) return false

            other as ApiState

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

    class ExceptionState(
        val exception: Exception
    ) : RepositoryState {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ExceptionState

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