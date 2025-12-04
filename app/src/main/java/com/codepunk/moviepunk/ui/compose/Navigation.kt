package com.codepunk.moviepunk.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codepunk.moviepunk.ui.compose.screen.auth.AuthScreen
import com.codepunk.moviepunk.ui.compose.screen.home.HomeScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    startDestination: Route = Route.Auth
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable<Route.Auth> {
            AuthScreen(
                modifier = Modifier
            )
        }

        composable<Route.Home> {
            HomeScreen(
                modifier = modifier
            )
        }

    }

}
