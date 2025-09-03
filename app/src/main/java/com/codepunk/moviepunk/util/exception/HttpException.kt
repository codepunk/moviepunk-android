package com.codepunk.moviepunk.util.exception

import com.codepunk.moviepunk.util.http.HttpStatus

open class HttpException(
    val httpStatus: HttpStatus
) : RuntimeException(httpStatus.description) {

    // region Overridden methods

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpException

        return httpStatus == other.httpStatus
    }

    override fun hashCode(): Int {
        return httpStatus.hashCode()
    }

    override fun toString(): String {
        return "HttpException(httpStatus=$httpStatus)"
    }

    // endregion Overridden methods

}
