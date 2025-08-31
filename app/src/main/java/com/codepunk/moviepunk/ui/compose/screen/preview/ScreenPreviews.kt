package com.codepunk.moviepunk.ui.compose.screen.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

private const val PIXEL_9_PRO = "id:pixel_9_pro"
private const val PIXEL_10_PRO = "id:pixel_10_pro"

// Pixel 9 Pro

@Preview(
    name = "Pixel 9 Pro",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = PIXEL_9_PRO,
    group = "Light Mode"
)
@Preview(
    name = "Pixel 9 Pro Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = PIXEL_9_PRO,
    group = "Dark Mode"
)

// Pixel 10 Pro

@Preview(
    name = "Pixel 10 Pro",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = PIXEL_10_PRO,
    group = "Light Mode"
)
@Preview(
    name = "Pixel 10 Pro Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = PIXEL_10_PRO,
    group = "Dark Mode"
)

annotation class ScreenPreviews
