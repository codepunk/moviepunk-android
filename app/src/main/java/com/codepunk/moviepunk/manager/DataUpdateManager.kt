package com.codepunk.moviepunk.manager

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.savedstate.savedState
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.local.dao.HashedImageDao
import com.codepunk.moviepunk.data.remote.util.WebScraper
import com.codepunk.moviepunk.di.qualifier.ApplicationScope
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.util.extension.isConnected
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
class DataUpdateManager @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val hashedImageDao: HashedImageDao,
    private val webScraper: WebScraper,
    @param:ApplicationScope private val appScope: CoroutineScope,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
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

    private var shouldCheckForUpdate: AtomicBoolean = AtomicBoolean(true)

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
        if (shouldCheckForUpdate.compareAndSet(expectedValue = true, newValue = false)) {
            checkForUpdate()
        }
    }

    private fun onDisconnected() {
        // No op
    }

    private fun checkForUpdate() {
        appScope.launch(ioDispatcher) {
            var shouldUpdateBackgroundImages = false

            // Get locally-cached hash
            // TODO
            val savedHash = try {
                hashedImageDao.getCurrentHash()
            } catch (e: Exception) {
                // TODO Handle error?
                return@launch
            }

            // IF we have a cached hash, compare to latest
            shouldUpdateBackgroundImages = if (savedHash == null) {
                true
            } else {
                // Get latest hash from TMDB
                val result = webScraper.scrapeTmdbWallpaperHash(BuildConfig.TMDB_URL)
                val latestHash = if (result.isSuccessful) {
                    result.body() ?: return@launch
                } else {
                    // TODO Handle error?
                    return@launch
                }

                savedHash != latestHash
            }

            if (shouldUpdateBackgroundImages) {
                // TODO NEXT
                // Clear up cached background images
                // Clear image info in local DB
                // Retrieve && cache latest background images
                //  (Choose appropriate density)
                // Download images
            }
        }
    }

    // endregion Methods

}