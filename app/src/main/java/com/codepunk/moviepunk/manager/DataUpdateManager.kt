package com.codepunk.moviepunk.manager

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import com.codepunk.moviepunk.util.extension.isConnected
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes

/*
 * TODO Investigate some way to have this manager maintain a flow of genre and other data
 *  That way, a ViewModel could just incorporate that flow into its state
 *
 * Also TODO I don't really need this manager. Just incorporate the refresh into the repository.
 */

class DataUpdateManager @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val moviePunkRepository: MoviePunkRepository,
    @param: IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    // region Properties

    private var dataUpdated = false

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
        checkAndUpdateData()
    }

    private fun onDisconnected() {
        // No op
    }

    private fun checkAndUpdateData() {
        if (!dataUpdated) {
            ProcessLifecycleOwner.get().lifecycleScope.launch(ioDispatcher) {
                val newest = moviePunkRepository.getNewestGenre()
                val elapsed = Clock.System.now() - newest
                if (elapsed > BuildConfig.DATA_REFRESH_DURATION_MINUTES.minutes) {
                    Timber.i("Data is out of date, updating")
                    moviePunkRepository.cacheGenres().onLeft { e ->
                        // TODO Handle exception
                    }
                } else {
                    Timber.i("Data is up to date")
                }
            }
            dataUpdated = true
        }
    }

    // endregion Methods

}
