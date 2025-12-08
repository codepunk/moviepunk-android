package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.codepunk.moviepunk.ui.compose.Route
import com.codepunk.moviepunk.ui.compose.screen.homemore.HomeMoreScreen
import com.codepunk.moviepunk.ui.compose.screen.movies.MoviesScreen
import com.codepunk.moviepunk.ui.compose.screen.movies.MoviesViewModel
import com.codepunk.moviepunk.ui.compose.screen.people.PeopleScreen
import com.codepunk.moviepunk.ui.compose.screen.tvshows.TvShowsScreen
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    @Suppress("unused")
    onIntent: (HomeIntent) -> Unit = {}
) {
    val navController = rememberNavController()
    var currentNavItem: HomeNavItem by remember { mutableStateOf(HomeNavItem.MOVIES) }

    val viewModel: HomeViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.messageFlow.collect { message ->
            navController.navigate(message.route) {
                popUpTo(navController.graph.id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    Scaffold(
        topBar = {
            HomeTopAppBar()
        }
    ) { paddingValues ->
        NavigationSuiteScaffold(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            navigationSuiteItems = {
                HomeNavItem.entries.map { navItem ->
                    item(
                        icon = {
                            Icon(
                                painter = painterResource(id = navItem.iconRes),
                                contentDescription = stringResource(id = navItem.contentDescriptionRes)
                            )
                        },
                        label = { Text(text = stringResource(id = navItem.labelRes)) },
                        selected = currentNavItem == navItem,
                        onClick = {
                            currentNavItem = navItem
                            viewModel.sendIntent(navItem.intent)
                        }
                    )
                }
            }
        ) {
            NavHost(
                modifier = modifier,
                navController = navController,
                startDestination = Route.Movies
            ) {
                composable<Route.Movies> {
                    val viewModel: MoviesViewModel = hiltViewModel()

                    LaunchedEffect(Unit) {
                        viewModel.messageFlow.collect { message ->
                            Timber.d("Received message: $message")
                        }
                    }

                    val state = viewModel.stateFlow.collectAsStateWithLifecycle()
                    val trendingMovies = viewModel.trendingMoviesFlow.collectAsLazyPagingItems()

                    MoviesScreen(
                        state = state.value,
                        trendingMovies = trendingMovies,
                        modifier = modifier
                    ) { intent ->
                        viewModel.sendIntent(intent)
                    }
                }

                composable<Route.TvShows> {
                    TvShowsScreen()
                }

                composable<Route.People> {
                    PeopleScreen()
                }

                composable<Route.HomeMore> {
                    HomeMoreScreen()
                }
            }
        }
    }
}
