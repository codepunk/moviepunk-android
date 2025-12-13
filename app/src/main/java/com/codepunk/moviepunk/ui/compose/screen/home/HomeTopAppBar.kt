package com.codepunk.moviepunk.ui.compose.screen.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.window.core.layout.WindowSizeClass
import com.codepunk.moviepunk.R
import com.codepunk.moviepunk.ui.compose.screen.preview.ComponentPreviews
import com.codepunk.moviepunk.ui.theme.MoviePunkTheme
import com.codepunk.moviepunk.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier
) {

    val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo(
        supportLargeAndXLargeWidth = true
    ).windowSizeClass

    if (
        windowSizeClass.isWidthAtLeastBreakpoint(
            WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
        )
    ) {
        @DrawableRes val appIcon = R.drawable.tmdb_logo_horiz
        val verticalPadding = MaterialTheme.dimens.paddingLarge
        TopAppBar(
            modifier = modifier,
            title = {
                Box(
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(appIcon),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .height(MaterialTheme.dimens.appBarHeight)
                            .padding(vertical = verticalPadding)

                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                //containerColor = MaterialTheme.colorScheme.inverseSurface
            )
        )
    } else {
        @DrawableRes val appIcon = R.drawable.tmdb_logo_vert
        val verticalPadding = MaterialTheme.dimens.paddingSmall
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = {
                Box(
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(appIcon),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .height(MaterialTheme.dimens.appBarHeight)
                            .padding(vertical = verticalPadding)

                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                //containerColor = MaterialTheme.colorScheme.inverseSurface
            )
        )
    }
}

@ComponentPreviews
@Composable
fun HomeTopAppBarPreviews() {
    MoviePunkTheme {
        HomeTopAppBar()
    }
}