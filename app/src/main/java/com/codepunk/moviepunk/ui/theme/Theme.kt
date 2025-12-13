@file:Suppress("unused")

package com.codepunk.moviepunk.ui.theme

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private const val MEDIUM_CONTRAST = (1f / 3)
private const val HIGH_CONTRAST = (2f / 3)

@Immutable
data class ExtendedColorScheme(
    val tmdbGradientStart: ColorFamily,
    val tmdbGradientEnd: ColorFamily,
    val coolGradientStart: ColorFamily,
    val coolGradientEnd: ColorFamily,
    val warmGradientStart: ColorFamily,
    val warmGradientEnd: ColorFamily,
    val hotGradientStart: ColorFamily,
    val hotGradientEnd: ColorFamily,
    val cta: ColorFamily,
)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

val extendedLight = ExtendedColorScheme(
    tmdbGradientStart = ColorFamily(
        tmdbGradientStartLight,
        onTmdbGradientStartLight,
        tmdbGradientStartContainerLight,
        onTmdbGradientStartContainerLight,
    ),
    tmdbGradientEnd = ColorFamily(
        tmdbGradientEndLight,
        onTmdbGradientEndLight,
        tmdbGradientEndContainerLight,
        onTmdbGradientEndContainerLight,
    ),
    coolGradientStart = ColorFamily(
        coolGradientStartLight,
        onCoolGradientStartLight,
        coolGradientStartContainerLight,
        onCoolGradientStartContainerLight,
    ),
    coolGradientEnd = ColorFamily(
        coolGradientEndLight,
        onCoolGradientEndLight,
        coolGradientEndContainerLight,
        onCoolGradientEndContainerLight,
    ),
    warmGradientStart = ColorFamily(
        warmGradientStartLight,
        onWarmGradientStartLight,
        warmGradientStartContainerLight,
        onWarmGradientStartContainerLight,
    ),
    warmGradientEnd = ColorFamily(
        warmGradientEndLight,
        onWarmGradientEndLight,
        warmGradientEndContainerLight,
        onWarmGradientEndContainerLight,
    ),
    hotGradientStart = ColorFamily(
        hotGradientStartLight,
        onHotGradientStartLight,
        hotGradientStartContainerLight,
        onHotGradientStartContainerLight,
    ),
    hotGradientEnd = ColorFamily(
        hotGradientEndLight,
        onHotGradientEndLight,
        hotGradientEndContainerLight,
        onHotGradientEndContainerLight,
    ),
    cta = ColorFamily(
        ctaLight,
        onCtaLight,
        ctaContainerLight,
        onCtaContainerLight,
    ),
)

val extendedDark = ExtendedColorScheme(
    tmdbGradientStart = ColorFamily(
        tmdbGradientStartDark,
        onTmdbGradientStartDark,
        tmdbGradientStartContainerDark,
        onTmdbGradientStartContainerDark,
    ),
    tmdbGradientEnd = ColorFamily(
        tmdbGradientEndDark,
        onTmdbGradientEndDark,
        tmdbGradientEndContainerDark,
        onTmdbGradientEndContainerDark,
    ),
    coolGradientStart = ColorFamily(
        coolGradientStartDark,
        onCoolGradientStartDark,
        coolGradientStartContainerDark,
        onCoolGradientStartContainerDark,
    ),
    coolGradientEnd = ColorFamily(
        coolGradientEndDark,
        onCoolGradientEndDark,
        coolGradientEndContainerDark,
        onCoolGradientEndContainerDark,
    ),
    warmGradientStart = ColorFamily(
        warmGradientStartDark,
        onWarmGradientStartDark,
        warmGradientStartContainerDark,
        onWarmGradientStartContainerDark,
    ),
    warmGradientEnd = ColorFamily(
        warmGradientEndDark,
        onWarmGradientEndDark,
        warmGradientEndContainerDark,
        onWarmGradientEndContainerDark,
    ),
    hotGradientStart = ColorFamily(
        hotGradientStartDark,
        onHotGradientStartDark,
        hotGradientStartContainerDark,
        onHotGradientStartContainerDark,
    ),
    hotGradientEnd = ColorFamily(
        hotGradientEndDark,
        onHotGradientEndDark,
        hotGradientEndContainerDark,
        onHotGradientEndContainerDark,
    ),
    cta = ColorFamily(
        ctaDark,
        onCtaDark,
        ctaContainerDark,
        onCtaContainerDark,
    ),
)

val extendedLightMediumContrast = ExtendedColorScheme(
    tmdbGradientStart = ColorFamily(
        tmdbGradientStartLightMediumContrast,
        onTmdbGradientStartLightMediumContrast,
        tmdbGradientStartContainerLightMediumContrast,
        onTmdbGradientStartContainerLightMediumContrast,
    ),
    tmdbGradientEnd = ColorFamily(
        tmdbGradientEndLightMediumContrast,
        onTmdbGradientEndLightMediumContrast,
        tmdbGradientEndContainerLightMediumContrast,
        onTmdbGradientEndContainerLightMediumContrast,
    ),
    coolGradientStart = ColorFamily(
        coolGradientStartLightMediumContrast,
        onCoolGradientStartLightMediumContrast,
        coolGradientStartContainerLightMediumContrast,
        onCoolGradientStartContainerLightMediumContrast,
    ),
    coolGradientEnd = ColorFamily(
        coolGradientEndLightMediumContrast,
        onCoolGradientEndLightMediumContrast,
        coolGradientEndContainerLightMediumContrast,
        onCoolGradientEndContainerLightMediumContrast,
    ),
    warmGradientStart = ColorFamily(
        warmGradientStartLightMediumContrast,
        onWarmGradientStartLightMediumContrast,
        warmGradientStartContainerLightMediumContrast,
        onWarmGradientStartContainerLightMediumContrast,
    ),
    warmGradientEnd = ColorFamily(
        warmGradientEndLightMediumContrast,
        onWarmGradientEndLightMediumContrast,
        warmGradientEndContainerLightMediumContrast,
        onWarmGradientEndContainerLightMediumContrast,
    ),
    hotGradientStart = ColorFamily(
        hotGradientStartLightMediumContrast,
        onHotGradientStartLightMediumContrast,
        hotGradientStartContainerLightMediumContrast,
        onHotGradientStartContainerLightMediumContrast,
    ),
    hotGradientEnd = ColorFamily(
        hotGradientEndLightMediumContrast,
        onHotGradientEndLightMediumContrast,
        hotGradientEndContainerLightMediumContrast,
        onHotGradientEndContainerLightMediumContrast,
    ),
    cta = ColorFamily(
        ctaLightMediumContrast,
        onCtaLightMediumContrast,
        ctaContainerLightMediumContrast,
        onCtaContainerLightMediumContrast,
    ),
)

val extendedLightHighContrast = ExtendedColorScheme(
    tmdbGradientStart = ColorFamily(
        tmdbGradientStartLightHighContrast,
        onTmdbGradientStartLightHighContrast,
        tmdbGradientStartContainerLightHighContrast,
        onTmdbGradientStartContainerLightHighContrast,
    ),
    tmdbGradientEnd = ColorFamily(
        tmdbGradientEndLightHighContrast,
        onTmdbGradientEndLightHighContrast,
        tmdbGradientEndContainerLightHighContrast,
        onTmdbGradientEndContainerLightHighContrast,
    ),
    coolGradientStart = ColorFamily(
        coolGradientStartLightHighContrast,
        onCoolGradientStartLightHighContrast,
        coolGradientStartContainerLightHighContrast,
        onCoolGradientStartContainerLightHighContrast,
    ),
    coolGradientEnd = ColorFamily(
        coolGradientEndLightHighContrast,
        onCoolGradientEndLightHighContrast,
        coolGradientEndContainerLightHighContrast,
        onCoolGradientEndContainerLightHighContrast,
    ),
    warmGradientStart = ColorFamily(
        warmGradientStartLightHighContrast,
        onWarmGradientStartLightHighContrast,
        warmGradientStartContainerLightHighContrast,
        onWarmGradientStartContainerLightHighContrast,
    ),
    warmGradientEnd = ColorFamily(
        warmGradientEndLightHighContrast,
        onWarmGradientEndLightHighContrast,
        warmGradientEndContainerLightHighContrast,
        onWarmGradientEndContainerLightHighContrast,
    ),
    hotGradientStart = ColorFamily(
        hotGradientStartLightHighContrast,
        onHotGradientStartLightHighContrast,
        hotGradientStartContainerLightHighContrast,
        onHotGradientStartContainerLightHighContrast,
    ),
    hotGradientEnd = ColorFamily(
        hotGradientEndLightHighContrast,
        onHotGradientEndLightHighContrast,
        hotGradientEndContainerLightHighContrast,
        onHotGradientEndContainerLightHighContrast,
    ),
    cta = ColorFamily(
        ctaLightHighContrast,
        onCtaLightHighContrast,
        ctaContainerLightHighContrast,
        onCtaContainerLightHighContrast,
    ),
)

val extendedDarkMediumContrast = ExtendedColorScheme(
    tmdbGradientStart = ColorFamily(
        tmdbGradientStartDarkMediumContrast,
        onTmdbGradientStartDarkMediumContrast,
        tmdbGradientStartContainerDarkMediumContrast,
        onTmdbGradientStartContainerDarkMediumContrast,
    ),
    tmdbGradientEnd = ColorFamily(
        tmdbGradientEndDarkMediumContrast,
        onTmdbGradientEndDarkMediumContrast,
        tmdbGradientEndContainerDarkMediumContrast,
        onTmdbGradientEndContainerDarkMediumContrast,
    ),
    coolGradientStart = ColorFamily(
        coolGradientStartDarkMediumContrast,
        onCoolGradientStartDarkMediumContrast,
        coolGradientStartContainerDarkMediumContrast,
        onCoolGradientStartContainerDarkMediumContrast,
    ),
    coolGradientEnd = ColorFamily(
        coolGradientEndDarkMediumContrast,
        onCoolGradientEndDarkMediumContrast,
        coolGradientEndContainerDarkMediumContrast,
        onCoolGradientEndContainerDarkMediumContrast,
    ),
    warmGradientStart = ColorFamily(
        warmGradientStartDarkMediumContrast,
        onWarmGradientStartDarkMediumContrast,
        warmGradientStartContainerDarkMediumContrast,
        onWarmGradientStartContainerDarkMediumContrast,
    ),
    warmGradientEnd = ColorFamily(
        warmGradientEndDarkMediumContrast,
        onWarmGradientEndDarkMediumContrast,
        warmGradientEndContainerDarkMediumContrast,
        onWarmGradientEndContainerDarkMediumContrast,
    ),
    hotGradientStart = ColorFamily(
        hotGradientStartDarkMediumContrast,
        onHotGradientStartDarkMediumContrast,
        hotGradientStartContainerDarkMediumContrast,
        onHotGradientStartContainerDarkMediumContrast,
    ),
    hotGradientEnd = ColorFamily(
        hotGradientEndDarkMediumContrast,
        onHotGradientEndDarkMediumContrast,
        hotGradientEndContainerDarkMediumContrast,
        onHotGradientEndContainerDarkMediumContrast,
    ),
    cta = ColorFamily(
        ctaDarkMediumContrast,
        onCtaDarkMediumContrast,
        ctaContainerDarkMediumContrast,
        onCtaContainerDarkMediumContrast,
    ),
)

val extendedDarkHighContrast = ExtendedColorScheme(
    tmdbGradientStart = ColorFamily(
        tmdbGradientStartDarkHighContrast,
        onTmdbGradientStartDarkHighContrast,
        tmdbGradientStartContainerDarkHighContrast,
        onTmdbGradientStartContainerDarkHighContrast,
    ),
    tmdbGradientEnd = ColorFamily(
        tmdbGradientEndDarkHighContrast,
        onTmdbGradientEndDarkHighContrast,
        tmdbGradientEndContainerDarkHighContrast,
        onTmdbGradientEndContainerDarkHighContrast,
    ),
    coolGradientStart = ColorFamily(
        coolGradientStartDarkHighContrast,
        onCoolGradientStartDarkHighContrast,
        coolGradientStartContainerDarkHighContrast,
        onCoolGradientStartContainerDarkHighContrast,
    ),
    coolGradientEnd = ColorFamily(
        coolGradientEndDarkHighContrast,
        onCoolGradientEndDarkHighContrast,
        coolGradientEndContainerDarkHighContrast,
        onCoolGradientEndContainerDarkHighContrast,
    ),
    warmGradientStart = ColorFamily(
        warmGradientStartDarkHighContrast,
        onWarmGradientStartDarkHighContrast,
        warmGradientStartContainerDarkHighContrast,
        onWarmGradientStartContainerDarkHighContrast,
    ),
    warmGradientEnd = ColorFamily(
        warmGradientEndDarkHighContrast,
        onWarmGradientEndDarkHighContrast,
        warmGradientEndContainerDarkHighContrast,
        onWarmGradientEndContainerDarkHighContrast,
    ),
    hotGradientStart = ColorFamily(
        hotGradientStartDarkHighContrast,
        onHotGradientStartDarkHighContrast,
        hotGradientStartContainerDarkHighContrast,
        onHotGradientStartContainerDarkHighContrast,
    ),
    hotGradientEnd = ColorFamily(
        hotGradientEndDarkHighContrast,
        onHotGradientEndDarkHighContrast,
        hotGradientEndContainerDarkHighContrast,
        onHotGradientEndContainerDarkHighContrast,
    ),
    cta = ColorFamily(
        ctaDarkHighContrast,
        onCtaDarkHighContrast,
        ctaContainerDarkHighContrast,
        onCtaContainerDarkHighContrast,
    ),
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
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

val LocalExtendedColorScheme = staticCompositionLocalOf {
    // Provide a default empty scheme to avoid crashes during composition.
    // This will be replaced by the actual scheme in MoviePunkTheme.
    ExtendedColorScheme(
        tmdbGradientStart = unspecified_scheme,
        tmdbGradientEnd = unspecified_scheme,
        coolGradientStart = unspecified_scheme,
        coolGradientEnd = unspecified_scheme,
        warmGradientStart = unspecified_scheme,
        warmGradientEnd = unspecified_scheme,
        hotGradientStart = unspecified_scheme,
        hotGradientEnd = unspecified_scheme,
        cta = unspecified_scheme
    )
}

@Suppress("UnusedReceiverParameter")
val MaterialTheme.dimens: FixedDimensScheme
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current

@Suppress("UnusedReceiverParameter")
val MaterialTheme.extendedColorScheme: ExtendedColorScheme
    @Composable
    @ReadOnlyComposable
    get() = LocalExtendedColorScheme.current

@Suppress("SameParameterValue")
@Composable
private fun <S> chooseColorScheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    lightScheme: S,
    darkScheme: S,
    mediumContrastLightColorScheme: S,
    mediumContrastDarkColorScheme: S,
    highContrastLightColorScheme: S,
    highContrastDarkColorScheme: S,
    dynamicChooser: (Context) -> S? = { _ -> null }
): S {
    val context = LocalContext.current
    val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    return if (!dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        if (darkTheme) {
            when {
                uiModeManager.contrast >= HIGH_CONTRAST -> highContrastDarkColorScheme
                uiModeManager.contrast >= MEDIUM_CONTRAST -> mediumContrastDarkColorScheme
                else -> darkScheme
            }
        } else {
            when {
                uiModeManager.contrast >= HIGH_CONTRAST -> highContrastLightColorScheme
                uiModeManager.contrast >= MEDIUM_CONTRAST -> mediumContrastLightColorScheme
                else -> lightScheme
            }
        }
    } else if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        dynamicChooser(context)
    } else {
        null
    } ?: if (darkTheme) {
        darkScheme
    } else {
        lightScheme
    }
}

@Composable
fun MoviePunkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = chooseColorScheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
        lightScheme = lightScheme,
        darkScheme = darkScheme,
        mediumContrastLightColorScheme = mediumContrastLightColorScheme,
        mediumContrastDarkColorScheme = mediumContrastDarkColorScheme,
        highContrastLightColorScheme = highContrastLightColorScheme,
        highContrastDarkColorScheme = highContrastDarkColorScheme
    ) { context ->
        if (darkTheme) {
            dynamicDarkColorScheme(context)
        } else {
            dynamicLightColorScheme(context)
        }
    }

    val extendedColorScheme = chooseColorScheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
        lightScheme = extendedLight,
        darkScheme = extendedDark,
        mediumContrastLightColorScheme = extendedLightMediumContrast,
        mediumContrastDarkColorScheme = extendedDarkMediumContrast,
        highContrastLightColorScheme = extendedLightHighContrast,
        highContrastDarkColorScheme = extendedDarkHighContrast
    )

    CompositionLocalProvider(
        LocalDimens provides fixedDimens,
        LocalExtendedColorScheme provides extendedColorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}
