package com.codepunk.moviepunk.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class CoreViewModel<Intent : CoreIntent, Message : CoreMessage>() : ViewModel() {

    // region Properties

    private val messageChannel: Channel<Message> = Channel(capacity = Channel.UNLIMITED)
    val messageFlow: Flow<Message> = messageChannel.receiveAsFlow()

    // endregion Properties

    // region Methods

    fun sendIntent(intent: Intent) {
        if (intent is UserIntent) {
            // Log user intent
            // Analytics.logUserIntent(intent.intentName)
            Timber.i("Analytics: eventName=${intent.eventName}")
        }
        handleIntent(intent)
    }

    protected abstract fun handleIntent(intent: Intent)

    protected fun sendMessage(message: Message) {
        viewModelScope.launch {
            messageChannel.send(message)
        }
    }

    override fun onCleared() {
        messageChannel.close()
        super.onCleared()
    }

    // endregion Methods

}
