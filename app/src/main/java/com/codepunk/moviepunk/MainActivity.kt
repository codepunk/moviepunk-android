package com.codepunk.moviepunk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.codepunk.moviepunk.manager.SyncManager
import com.codepunk.moviepunk.manager.UserSessionManager
import com.codepunk.moviepunk.session.UserSession.Authenticated
import com.codepunk.moviepunk.ui.compose.Navigation
import com.codepunk.moviepunk.ui.compose.Route
import com.codepunk.moviepunk.ui.theme.MoviePunkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var syncManager: SyncManager

    @Inject
    lateinit var userSessionManager: UserSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var keepSplashScreen = true

        // TODO Is there a way to simplify these two conditions so that we listen for complete
        //   but also have a minimum time on screen? Maybe 2 flows?

        /* From Gemini:
            import kotlinx.coroutines.*
            import kotlinx.coroutines.flow.*

            class ConditionMonitor {
                private val _condition1 = MutableStateFlow(false)
                val condition1: StateFlow<Boolean> = _condition1

                private val _condition2 = MutableStateFlow(false)
                val condition2: StateFlow<Boolean> = _condition2

                fun setCondition1(value: Boolean) { _condition1.value = value }
                fun setCondition2(value: Boolean) { _condition2.value = value }

                fun startMonitoring(scope: CoroutineScope) {
                    scope.launch {
                        combine(condition1, condition2) { c1, c2 -> c1 && c2 }
                            .collect { allConditionsMet ->
                                if (allConditionsMet) {
                                    println("Both conditions are now true! Triggering action.")
                                    // Your action code here
                                } else {
                                    println("Conditions not fully met yet.")
                                }
                            }
                    }
                }
            }

            fun main() = runBlocking {
                val monitor = ConditionMonitor()
                monitor.startMonitoring(this)

                monitor.setCondition1(true)
                delay(100) // Simulate some time passing
                monitor.setCondition2(true) // This will trigger the action
            }
         */

        lifecycleScope.launch {
            syncManager.completeFlow.collectLatest {
                // keepSplashScreen = !it
            }
        }

        lifecycleScope.launch {
            delay(1500L)
            // if (syncManager.completeFlow.value) {
            keepSplashScreen = false
            // }
        }

        splashScreen.setKeepOnScreenCondition { keepSplashScreen }

        enableEdgeToEdge()
        setContent {
            val userSession = userSessionManager.userSessionFlow.collectAsState()

            MoviePunkTheme {
                Navigation(
                    startDestination = if (userSession.value is Authenticated) {
                        Route.Home
                    } else {
                        Route.Home // TODO TEMP
                    }
                )
            }
        }
    }
}
