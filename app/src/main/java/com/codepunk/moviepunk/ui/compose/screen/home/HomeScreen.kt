package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.ui.compose.screen.preview.ScreenPreviews
import com.codepunk.moviepunk.ui.theme.MoviePunkTheme
import timber.log.Timber

@Composable
fun HomeScreen(
    state: HomeState,
    modifier: Modifier = Modifier,
    @Suppress("unused")
    onEvent: (HomeEvent) -> Unit
) {
    Timber.d(message = "state=$state")

    val lazyTrendingMoviesPagingItems = state.trendingMoviesFlow.collectAsLazyPagingItems()

    LaunchedEffect(lazyTrendingMoviesPagingItems) {
        Timber.i("lazyTrendingMoviesPagingItems=$lazyTrendingMoviesPagingItems")
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = lazyTrendingMoviesPagingItems.itemCount,
            key = lazyTrendingMoviesPagingItems.itemKey { it.id }
        ) { index ->
            lazyTrendingMoviesPagingItems[index]?.also { movie ->
                MovieCard(
                    movie = movie
                )
            }
        }
    }

    /*
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Home Screen"
        )
        if (state.genresLoading) {
            Text(text = "Genres loading...")
        }
        state.genresError?.apply {
            Text(text = "Genre error: ${this::class.java.simpleName} | ${this.message}")
        }
        Text(text = "${state.genres.size} genres found.")
    }

     */
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
            HomeScreen(
                state = HomeState(),
                modifier = Modifier.padding(padding),
                onEvent = {}
            )
        }
    }
}
