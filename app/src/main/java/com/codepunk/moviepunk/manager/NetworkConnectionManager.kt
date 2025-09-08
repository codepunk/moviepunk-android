package com.codepunk.moviepunk.manager

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.codepunk.moviepunk.util.extension.isConnected
import javax.inject.Inject

/*
 * TODO Investigate some way to have this manager maintain a flow of genre and other data
 *  That way, a ViewModel could just incorporate that flow into its state
 *
 * Also TODO I don't really need this manager. Just incorporate the refresh into the repository.
 */

class NetworkConnectionManager @Inject constructor(
    private val connectivityManager: ConnectivityManager
) {

    // region Properties

    private var isConnected = false
        set(value) {
            if (field != value) {
                field = value
                if (value) {
                    onConnected()
                } else {
                    onDisconnected()
                }
            }
        }

    // endregion Properties

    // region Constructors

    init {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    if (connectivityManager.isConnected) {
                        isConnected = true
                    }
                }

                override fun onLost(network: Network) {
                    if (!connectivityManager.isConnected) {
                        isConnected = false
                    }
                }
            }
        )

    }

    // endregion Constructors

    // region Methods

    private fun onConnected() {
        // No op
    }

    private fun onDisconnected() {
        // No op
    }

    // endregion Methods

}
