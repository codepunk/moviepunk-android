package com.codepunk.moviepunk.ui.compose.screen.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

private const val PIXEL_9_PRO_LANDSCAPE = "spec:width=411dp,height=920dp,dpi=442," +
        "isRound=false,chinSize=0dp,orientation=landscape,cutout=none,navigation=gesture"
private const val PIXEL_5_LANDSCAPE = "spec:width=393dp,height=851dp,dpi=440," +
        "isRound=false,chinSize=0dp,orientation=landscape,cutout=none,navigation=gesture"
private const val PIXEL_TABLET_PORTRAIT = "spec:width=800dp,height=1200dp,dpi=276," +
        "isRound=false,chinSize=0dp,orientation=portrait,cutout=none,navigation=gesture"

// Pixel 9 Pro

@Preview(
    name = "Pixel 9 Pro",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_9,
    group = "Light Mode"
)
@Preview(
    name = "Pixel 9 Pro Landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = PIXEL_9_PRO_LANDSCAPE,
    group = "Light Mode"
)
@Preview(
    name = "Pixel 9 Pro Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_9,
    group = "Dark Mode"
)
@Preview(
    name = "Pixel 9 Pro Landscape Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = PIXEL_9_PRO_LANDSCAPE,
    group = "Dark Mode"
)

// Pixel 5

@Preview(
    name = "Pixel 5",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_5,
    group = "Light Mode"
)
@Preview(
    name = "Pixel 5 Landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = PIXEL_5_LANDSCAPE,
    group = "Light Mode"
)
@Preview(
    name = "Pixel 5 Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_5,
    group = "Dark Mode"
)
@Preview(
    name = "Pixel 5 Landscape Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = PIXEL_5_LANDSCAPE,
    group = "Dark Mode"
)

// Pixel Tablet

@Preview(
    name = "Pixel Tablet Portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = PIXEL_TABLET_PORTRAIT,
    group = "Light Mode"
)
@Preview(
    name = "Pixel Tablet",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_TABLET,
    group = "Light Mode"
)
@Preview(
    name = "Pixel Tablet Portrait Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = PIXEL_TABLET_PORTRAIT,
    group = "Dark Mode"
)
@Preview(
    name = "Pixel Tablet Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_TABLET,
    group = "Dark Mode"
)
annotation class ScreenPreviews