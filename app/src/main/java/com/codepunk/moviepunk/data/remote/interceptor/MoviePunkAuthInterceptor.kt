package com.codepunk.moviepunk.data.remote.interceptor

import com.codepunk.moviepunk.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class MoviePunkAuthInterceptor @Inject constructor() : Interceptor {

    // region Overridden methods

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .apply {
                addHeader(AUTHORIZATION, "Bearer ${BuildConfig.THE_MOVIE_DB_ACCESS_TOKEN}")
                /* TODO UserSessionManager?
                val authentication = userSessionManager.userSession.value
                if (authentication is Authenticated) {
                    addHeader(AUTHORIZATION, authentication.authToken)
                }
                 */
            }
            .build()
        return chain.proceed(newRequest)
    }

    // endregion Overridden methods

    // region Companion object

    companion object {
        private const val AUTHORIZATION: String = "Authorization"
    }

    // endregion Companion object

}
