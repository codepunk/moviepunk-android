package com.codepunk.moviepunk

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.codepunk.moviepunk.util.lifecycle.ProcessLifecycleObserver
import com.codepunk.moviepunk.util.log.FormattingDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MoviePunkApp : Application() {

    // region Variables

    @Inject
    lateinit var processLifecycleObserver: ProcessLifecycleObserver

    //@Inject
    //lateinit var dataUpdateManager: DataUpdateManager

    // region Methods

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(processLifecycleObserver)
        if (BuildConfig.DEBUG) {
            Timber.plant(FormattingDebugTree())
        }
    }

    // endregion Methods

}