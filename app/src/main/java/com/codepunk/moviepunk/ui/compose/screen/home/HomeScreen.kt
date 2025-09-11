package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
