package com.codepunk.moviepunk.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val fixedDimens = FixedDimensScheme(
    borderHairline = borderHairline,
    border = border,
    borderThick = borderThick,
    borderBold = borderBold,
    paddingTight = paddingTight,
    paddingSmall = paddingSmall,
    paddingStandard = paddingStandard,
    paddingLarge = paddingLarge,
    paddingXLarge = paddingXLarge,
    marginHorizontalCompact = marginHorizontalCompact,
    spacerCompact = spacerCompact,
    marginHorizontalMedium = marginHorizontalMedium,
    spacerMedium = spacerMedium,
    marginHorizontalExpanded = marginHorizontalExpanded,
    spacerExpanded = spacerExpanded,
    marginHorizontalLarge = marginHorizontalLarge,
    spacerLarge = spacerLarge,
    marginHorizontalExtraLarge = marginHorizontalExtraLarge,
    spacerExtraLarge = spacerExtraLarge,
    buttonIcon = buttonIcon,
    icon = icon,
    buttonHeight = buttonHeight,
    touchTargets = touchTargets,
    fab = fab,
    appBarHeight = appBarHeight,
    region2xSmall = region2xSmall,
    regionXSmall = regionXSmall,
    regionSmall = regionSmall,
    region = region,
    regionLarge = regionLarge,
    regionXLarge = regionXLarge,
    region2xLarge = region2xLarge,
    region3xLarge = region3xLarge
)

val LocalDimens = staticCompositionLocalOf { fixedDimens }

@Suppress("UnusedReceiverParameter")
val MaterialTheme.dimens: FixedDimensScheme
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current

@Composable
fun MoviePunkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(LocalDimens provides fixedDimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
