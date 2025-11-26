package com.codepunk.moviepunk.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.codepunk.moviepunk.ui.compose.screen.home.HomeScreen
import com.codepunk.moviepunk.ui.compose.screen.home.HomeViewModel
import timber.log.Timber

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

            LaunchedEffect(Unit) {
                viewModel.messageFlow.collect { message ->
                    Timber.d("Received message: $message")
                }
            }

            val state = viewModel.stateFlow.collectAsStateWithLifecycle()
            val trendingMovies = viewModel.trendingMoviesFlow.collectAsLazyPagingItems()
            HomeScreen(
                state = state.value,
                trendingMovies = trendingMovies,
                modifier = modifier
            ) { intent ->
                viewModel.sendIntent(intent)
            }
        }

    }

}
