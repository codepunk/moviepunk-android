package com.codepunk.moviepunk.data.remote.interceptor

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject

class LanguageInterceptor @Inject constructor(
    @param:ApplicationContext private val context: Context
) : Interceptor {

    // region Properties

    val locale: Locale = context.resources.configuration.locales[0]

    // endregion Properties

    // region Overridden methods

    override fun intercept(chain: Interceptor.Chain): Response =
        if (locale == Locale.US) {
            chain.proceed(chain.request())
        } else {
            val newUrl = chain.request().url.newBuilder()
                .addQueryParameter("language", locale.toLanguageTag())
                .build()
            val request = chain.request().newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(request)
        }

    // endregion Overridden methods

}