package com.codepunk.moviepunk

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.codepunk.moviepunk.util.lifecycle.ProcessLifecycleObserver
import com.codepunk.moviepunk.util.log.FormattingDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

/**
 * TODO Ongoing thoughts:
 *  * A repository that returns Either<Exception, Data> will appear to be done loading
 *    immediately and return the local cached data, when in reality it could still be
 *    fetching remote data. Somewhere we need to show that it is fetching new data.
 *    We could make a Left that is a sealed class of (data object) Loading or Exception and it
 *    corresponds to an "incomplete" data request. (We could make an "IncompleteException"
 *    class as well but that seems overkill and not quite appropriate?)
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
    }

    // endregion Methods

}