package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.ui.compose.screen.preview.ScreenPreviews
import com.codepunk.moviepunk.ui.theme.MoviePunkTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    state: HomeState,
    trendingMovies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    @Suppress("unused")
    onEvent: (HomeEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
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
                modifier = Modifier.padding(padding),
                onEvent = {}
            )
        }
    }
}
