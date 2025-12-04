package com.codepunk.moviepunk.ui.compose.screen.people

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
fun PeopleScreen(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "People"
        )
    }
}

@ScreenPreviews
@Composable
fun PeopleScreenPreview() {
    MoviePunkTheme {
        Scaffold { paddingValues ->
            PeopleScreen(
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
