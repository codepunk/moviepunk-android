package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.codepunk.moviepunk.ui.compose.screen.preview.ScreenPreviews
import com.codepunk.moviepunk.ui.theme.MoviePunkTheme

@Composable
fun HomeScreen(
    @Suppress("unused") state: HomeState,
    modifier: Modifier = Modifier,
    @Suppress("unused") onEvent: (HomeEvent) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home Screen"
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
