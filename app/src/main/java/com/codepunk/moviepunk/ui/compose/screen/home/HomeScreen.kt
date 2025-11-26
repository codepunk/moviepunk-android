package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.ui.compose.screen.preview.ScreenPreviews
import com.codepunk.moviepunk.ui.theme.MoviePunkTheme
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

@Composable
fun HomeScreen(
    state: HomeState,
    trendingMovies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    @Suppress("unused")
    onIntent: (HomeIntent) -> Unit = {}
) {
    LaunchedEffect(key1 = trendingMovies.loadState) {
        Timber.i("trendingMovies.loadState = ${trendingMovies.loadState}")
        if (trendingMovies.loadState.refresh is LoadState.Error) {
            // TODO
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (trendingMovies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    count = trendingMovies.itemCount,
                    key = trendingMovies.itemKey { it.id }
                ) { index ->
                    trendingMovies[index]?.also { movie ->
                        MovieCard(
                            movie = movie
                        )
                    }
                }

                item {
                    if (trendingMovies.loadState.append is LoadState.Loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(150.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }

        // TODO TEMP
        AlertDialog( // 3
            onDismissRequest = { // 4
                // shouldShowDialog.value = false
            },
            // 5
            title = { Text(text = "Alert Dialog") },
            text = { Text(text = "Jetpack Compose Alert Dialog") },
            confirmButton = { // 6
                Button(
                    onClick = {
                        //shouldShowDialog.value = false
                        onIntent(HomeIntent.TestIntent)
                    }
                ) {
                    Text(
                        text = "Confirm",
                        //color = Color.White
                    )
                }
            }
        )
        // END TEMP
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .height(150.dp)
    ) {
        Text(
            text = movie.title
        )
    }
}

@ScreenPreviews
@Composable
fun HomeScreenPreview() {
    MoviePunkTheme {
        Scaffold { padding ->
            val emptyMovies = flowOf(PagingData.empty<Movie>()).collectAsLazyPagingItems()
            HomeScreen(
                state = HomeState(),
                trendingMovies = emptyMovies,
                modifier = Modifier.padding(padding)
            )
        }
    }
}
