package com.codepunk.moviepunk.data.remote.interceptor

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LanguageInterceptor @Inject constructor(
    @param:ApplicationContext private val context: Context
) : Interceptor {

    // region Properties

    val language: String = context.resources.configuration.locales[0].toLanguageTag()

    // endregion Properties

    // region Overridden methods

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("language", language)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .apply { url(newUrl) }
            .build()

        return chain.proceed(newRequest)
    }

    // endregion Overridden methods

}