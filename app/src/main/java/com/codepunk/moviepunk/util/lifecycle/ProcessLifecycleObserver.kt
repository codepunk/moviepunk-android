package com.codepunk.moviepunk.util.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber
import javax.inject.Inject

class ProcessLifecycleObserver @Inject constructor() : DefaultLifecycleObserver {

    // region Methods

    override fun onResume(owner: LifecycleOwner) {
        Timber.d("onResume")
    }

    // endregion Methods

}