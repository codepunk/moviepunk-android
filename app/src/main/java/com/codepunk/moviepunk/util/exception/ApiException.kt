package com.codepunk.moviepunk.util.exception

import com.codepunk.moviepunk.domain.model.ApiStatus
import com.codepunk.moviepunk.util.http.HttpStatus

class ApiException(
    httpStatus: HttpStatus,
    val apiStatus: ApiStatus
) : HttpException(httpStatus) {

    // region Overridden methods

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as ApiException

        return apiStatus == other.apiStatus
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + apiStatus.hashCode()
        return result
    }

    override fun toString(): String {
        return "ApiException(httpStatus=$httpStatus, apiStatus=$apiStatus)"
    }

    // endregion Overridden methods

}
