package com.codepunk.moviepunk.core

import android.net.ConnectivityManager
import android.net.NetworkRequest
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class CoreViewModel<Intent : CoreIntent, Message : CoreMessage>(
    networkRequest: NetworkRequest,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    private val messageChannel: Channel<Message> = Channel(capacity = Channel.UNLIMITED)
    val messageFlow: Flow<Message> = messageChannel.receiveAsFlow()

    private val networkCallback : ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                onNetworkAvailable()
            }

            override fun onLost(network: android.net.Network) {
                onNetworkLost()
            }
        }

    init {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    abstract fun sendIntent(intent: Intent)

    open fun onNetworkAvailable() {
        // No op
    }

    open fun onNetworkLost() {
        // No op
    }

    suspend fun sendMessage(message: Message) {
        messageChannel.send(message)
    }

    override fun onCleared() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        messageChannel.close()
        super.onCleared()
    }

}
