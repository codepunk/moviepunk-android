package com.codepunk.moviepunk.data.remote.interceptor

import android.net.ConnectivityManager
import com.codepunk.moviepunk.util.extension.isConnected
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : Interceptor {

    // region Methods

    override fun intercept(chain: Interceptor.Chain): Response =
        if (connectivityManager.isConnected) {
            chain.proceed(request = chain.request())
        } else {
            Response.Builder()
                .request(request = chain.request())
                .protocol(Protocol.HTTP_2)
                .code(code = SERVICE_UNAVAILABLE)
                .message(message = ERROR_MESSAGE)
                .body(body = ERROR_RESPONSE_BODY)
                .build()
        }

    // endregion Methods

    // region Companion object

    private companion object {
        const val SERVICE_UNAVAILABLE = 503
        const val ERROR = "error"
        const val ERROR_MESSAGE = "No internet connection"
        const val ERROR_RESPONSE_BODY_STRING = "{\"$ERROR\": \"$ERROR_MESSAGE\"}"

        val APPLICATION_JSON_MEDIA_TYPE by lazy { "application/json".toMediaType() }
        val ERROR_RESPONSE_BODY by lazy {
            ERROR_RESPONSE_BODY_STRING.toResponseBody(APPLICATION_JSON_MEDIA_TYPE)
        }
    }

    // endregion Companion object

}