package com.codepunk.moviepunk

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade
import com.codepunk.moviepunk.util.lifecycle.ProcessLifecycleObserver
import com.codepunk.moviepunk.util.log.FormattingDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

/**
 * TODO NEXT Ongoing thoughts:
 *   Some next steps:
 *   * Scaffolding on MoviesScreen (may have to look into colors, standard sizes etc.)
 *   * Different curated content component sizes for different screen sizes
 *   * "Loading" for curated content background
 */

@HiltAndroidApp
class MoviePunkApp : Application() {

    // region Variables

    @Inject
    lateinit var processLifecycleObserver: ProcessLifecycleObserver

    // region Methods

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(processLifecycleObserver)
        if (BuildConfig.DEBUG) {
            Timber.plant(FormattingDebugTree())
        }

        SingletonImageLoader.setSafe { context ->
            ImageLoader.Builder(context)
                .crossfade(true)
                .build()
        }
    }

    // endregion Methods

}