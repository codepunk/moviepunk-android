package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.codepunk.moviepunk.R
import com.codepunk.moviepunk.ui.compose.screen.preview.ComponentPreviews
import com.codepunk.moviepunk.ui.theme.MoviePunkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier
) {
    val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo(
        supportLargeAndXLargeWidth = true
    ).windowSizeClass
    val appIcon = when {
        windowSizeClass.isWidthAtLeastBreakpoint(
            WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
        ) -> R.drawable.tmdb_logo_horiz
        else -> R.drawable.tmdb_logo_vert
    }

    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        title = {
            Image(
                painter = painterResource(appIcon),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 8.dp)

            )
        }
    )
}

@ComponentPreviews
@Composable
fun HomeTopAppBarPreviews() {
    MoviePunkTheme {
        Scaffold { padding ->
            HomeTopAppBar(
                modifier = Modifier.padding(padding)
            )
        }
    }
}