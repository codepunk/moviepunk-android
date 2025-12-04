package com.codepunk.moviepunk.session

sealed interface UserSession {

    data object Unauthenticated : UserSession

    data class Authenticated(
        val userId: Long,
        val authToken: String
    ) : UserSession

}
