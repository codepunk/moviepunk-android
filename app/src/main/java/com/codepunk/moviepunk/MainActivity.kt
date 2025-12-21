package com.codepunk.moviepunk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.codepunk.moviepunk.manager.ConfigurationManager
import com.codepunk.moviepunk.manager.UserSessionManager
import com.codepunk.moviepunk.session.UserSession.Authenticated
import com.codepunk.moviepunk.ui.compose.Navigation
import com.codepunk.moviepunk.ui.compose.Route
import com.codepunk.moviepunk.ui.compose.screen.splash.SplashViewModel
import com.codepunk.moviepunk.ui.theme.MoviePunkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var configurationManager: ConfigurationManager

    @Inject
    lateinit var userSessionManager: UserSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var keepSplashScreen = true

        val splashViewModel: SplashViewModel by viewModels()

        lifecycleScope.launch {
            splashViewModel.syncCompleteFlow.collect { isComplete ->
                Timber.i("syncComplete=$isComplete")
                keepSplashScreen = !isComplete
            }
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
