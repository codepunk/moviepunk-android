package com.codepunk.moviepunk.util.extension

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

// region Properties

val ConnectivityManager.isConnected: Boolean
    get() {
        val network = activeNetwork ?: return false
        val capabilities = getNetworkCapabilities(network) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

// endregion Properties
