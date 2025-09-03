package com.codepunk.moviepunk.data.remote.interceptor

import android.content.Context
import android.net.ConnectivityManager
import com.codepunk.moviepunk.R
import com.codepunk.moviepunk.util.extension.isConnected
import com.codepunk.moviepunk.util.exception.NoConnectivityException
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val connectivityManager: ConnectivityManager
) : Interceptor {

    // region Methods

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityManager.isConnected()) {
            throw NoConnectivityException(
                message = context.getString(R.string.not_connected_to_internet)
            )
        }
        return chain.proceed(chain.request().newBuilder().build())
    }

    // endregion Methods

}