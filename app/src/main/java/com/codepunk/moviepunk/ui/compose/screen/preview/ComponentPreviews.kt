package com.codepunk.moviepunk.ui.compose.screen.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Phone Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PHONE,
    group = "Light Mode"
)
@Preview(
    name = "Phone Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PHONE,
    group = "Dark Mode"
)

@Preview(
    name = "Tablet Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.TABLET,
    group = "Light Mode"
)
@Preview(
    name = "Tablet Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.TABLET,
    group = "Dark Mode"
)
annotation class ComponentPreviews