package com.codepunk.moviepunk.manager

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import com.codepunk.moviepunk.di.qualifier.InternetRequest
import com.codepunk.moviepunk.util.extension.isConnected
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NetworkConnectionManager @Inject constructor(
    @InternetRequest networkRequest: NetworkRequest,
    private val connectivityManager: ConnectivityManager
) {

    // region Properties

    private val networkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            if (connectivityManager.isConnected) {
                _connectionStateFlow.value = true
            }
        }

        override fun onLost(network: Network) {
            if (!connectivityManager.isConnected) {
                _connectionStateFlow.value = false
            }
        }
    }

    private val _connectionStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val connectionStateFlow: StateFlow<Boolean> = _connectionStateFlow.asStateFlow()

    // endregion Properties

    // region Constructors

    init {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    // endregion Constructors

}
