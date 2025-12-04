package com.codepunk.moviepunk.manager

import com.codepunk.moviepunk.session.UserSession
import com.codepunk.moviepunk.session.UserSession.Authenticated
import com.codepunk.moviepunk.session.UserSession.Unauthenticated
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSessionManager @Inject constructor() {

    private val _userSessionFlow: MutableStateFlow<UserSession> =
        MutableStateFlow(Unauthenticated)

    val userSessionFlow: StateFlow<UserSession> = _userSessionFlow.asStateFlow()

    fun login(userSession: Authenticated) {
        _userSessionFlow.value = userSession
    }

    fun logout() {
        _userSessionFlow.value = Unauthenticated
    }

    fun update(userSession: UserSession) {
        _userSessionFlow.value = userSession
    }

    fun isLoggedIn(): Boolean = _userSessionFlow.value is Authenticated

}
