package com.codepunk.moviepunk.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codepunk.moviepunk.ui.compose.screen.home.HomeScreen
import com.codepunk.moviepunk.ui.compose.screen.home.HomeViewModel

@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home
    ) {

        composable<Routes.Home> {
            val viewModel: HomeViewModel = hiltViewModel()
            val state = viewModel.stateFlow.collectAsStateWithLifecycle()
            HomeScreen(
                state = state.value,
                modifier = modifier
            ) { event ->
                // Handle events here
            }
        }

    }

}
