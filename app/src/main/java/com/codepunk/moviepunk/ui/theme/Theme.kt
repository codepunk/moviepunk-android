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
    val tmdbGradientStart: Color,
    val onTmdbGradientStart: Color,
    val tmdbGradientStartContainer: Color,
    val onTmdbGradientStartContainer: Color,
    val tmdbGradientEnd: Color,
    val onTmdbGradientEnd: Color,
    val tmdbGradientEndContainer: Color,
    val onTmdbGradientEndContainer: Color,
    val coolGradientStart: Color,
    val onCoolGradientStart: Color,
    val coolGradientStartContainer: Color,
    val onCoolGradientStartContainer: Color,
    val coolGradientEnd: Color,
    val onCoolGradientEnd: Color,
    val coolGradientEndContainer: Color,
    val onCoolGradientEndContainer: Color,
    val warmGradientStart: Color,
    val onWarmGradientStart: Color,
    val warmGradientStartContainer: Color,
    val onWarmGradientStartContainer: Color,
    val warmGradientEnd: Color,
    val onWarmGradientEnd: Color,
    val warmGradientEndContainer: Color,
    val onWarmGradientEndContainer: Color,
    val hotGradientStart: Color,
    val onHotGradientStart: Color,
    val hotGradientStartContainer: Color,
    val onHotGradientStartContainer: Color,
    val hotGradientEnd: Color,
    val onHotGradientEnd: Color,
    val hotGradientEndContainer: Color,
    val onHotGradientEndContainer: Color,
    val cta: Color,
    val onCta: Color,
    val ctaContainer: Color,
    val onCtaContainer: Color,
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
    tmdbGradientStart = tmdbGradientStartLight,
    onTmdbGradientStart = onTmdbGradientStartLight,
    tmdbGradientStartContainer = tmdbGradientStartContainerLight,
    onTmdbGradientStartContainer = onTmdbGradientStartContainerLight,
    tmdbGradientEnd = tmdbGradientEndLight,
    onTmdbGradientEnd = onTmdbGradientEndLight,
    tmdbGradientEndContainer = tmdbGradientEndContainerLight,
    onTmdbGradientEndContainer = onTmdbGradientEndContainerLight,
    coolGradientStart = coolGradientStartLight,
    onCoolGradientStart = onCoolGradientStartLight,
    coolGradientStartContainer = coolGradientStartContainerLight,
    onCoolGradientStartContainer = onCoolGradientStartContainerLight,
    coolGradientEnd = coolGradientEndLight,
    onCoolGradientEnd = onCoolGradientEndLight,
    coolGradientEndContainer = coolGradientEndContainerLight,
    onCoolGradientEndContainer = onCoolGradientEndContainerLight,
    warmGradientStart = warmGradientStartLight,
    onWarmGradientStart = onWarmGradientStartLight,
    warmGradientStartContainer = warmGradientStartContainerLight,
    onWarmGradientStartContainer = onWarmGradientStartContainerLight,
    warmGradientEnd = warmGradientEndLight,
    onWarmGradientEnd = onWarmGradientEndLight,
    warmGradientEndContainer = warmGradientEndContainerLight,
    onWarmGradientEndContainer = onWarmGradientEndContainerLight,
    hotGradientStart = hotGradientStartLight,
    onHotGradientStart = onHotGradientStartLight,
    hotGradientStartContainer = hotGradientStartContainerLight,
    onHotGradientStartContainer = onHotGradientStartContainerLight,
    hotGradientEnd = hotGradientEndLight,
    onHotGradientEnd = onHotGradientEndLight,
    hotGradientEndContainer = hotGradientEndContainerLight,
    onHotGradientEndContainer = onHotGradientEndContainerLight,
    cta = ctaLight,
    onCta = onCtaLight,
    ctaContainer = ctaContainerLight,
    onCtaContainer = onCtaContainerLight
)

val extendedDark = ExtendedColorScheme(
    tmdbGradientStart = tmdbGradientStartDark,
    onTmdbGradientStart = onTmdbGradientStartDark,
    tmdbGradientStartContainer = tmdbGradientStartContainerDark,
    onTmdbGradientStartContainer = onTmdbGradientStartContainerDark,
    tmdbGradientEnd = tmdbGradientEndDark,
    onTmdbGradientEnd = onTmdbGradientEndDark,
    tmdbGradientEndContainer = tmdbGradientEndContainerDark,
    onTmdbGradientEndContainer = onTmdbGradientEndContainerDark,
    coolGradientStart = coolGradientStartDark,
    onCoolGradientStart = onCoolGradientStartDark,
    coolGradientStartContainer = coolGradientStartContainerDark,
    onCoolGradientStartContainer = onCoolGradientStartContainerDark,
    coolGradientEnd = coolGradientEndDark,
    onCoolGradientEnd = onCoolGradientEndDark,
    coolGradientEndContainer = coolGradientEndContainerDark,
    onCoolGradientEndContainer = onCoolGradientEndContainerDark,
    warmGradientStart = warmGradientStartDark,
    onWarmGradientStart = onWarmGradientStartDark,
    warmGradientStartContainer = warmGradientStartContainerDark,
    onWarmGradientStartContainer = onWarmGradientStartContainerDark,
    warmGradientEnd = warmGradientEndDark,
    onWarmGradientEnd = onWarmGradientEndDark,
    warmGradientEndContainer = warmGradientEndContainerDark,
    onWarmGradientEndContainer = onWarmGradientEndContainerDark,
    hotGradientStart = hotGradientStartDark,
    onHotGradientStart = onHotGradientStartDark,
    hotGradientStartContainer = hotGradientStartContainerDark,
    onHotGradientStartContainer = onHotGradientStartContainerDark,
    hotGradientEnd = hotGradientEndDark,
    onHotGradientEnd = onHotGradientEndDark,
    hotGradientEndContainer = hotGradientEndContainerDark,
    onHotGradientEndContainer = onHotGradientEndContainerDark,
    cta = ctaDark,
    onCta = onCtaDark,
    ctaContainer = ctaContainerDark,
    onCtaContainer = onCtaContainerDark
)

val extendedLightMediumContrast = ExtendedColorScheme(
    tmdbGradientStart = tmdbGradientStartLightMediumContrast,
    onTmdbGradientStart = onTmdbGradientStartLightMediumContrast,
    tmdbGradientStartContainer = tmdbGradientStartContainerLightMediumContrast,
    onTmdbGradientStartContainer = onTmdbGradientStartContainerLightMediumContrast,
    tmdbGradientEnd = tmdbGradientEndLightMediumContrast,
    onTmdbGradientEnd = onTmdbGradientEndLightMediumContrast,
    tmdbGradientEndContainer = tmdbGradientEndContainerLightMediumContrast,
    onTmdbGradientEndContainer = onTmdbGradientEndContainerLightMediumContrast,
    coolGradientStart = coolGradientStartLightMediumContrast,
    onCoolGradientStart = onCoolGradientStartLightMediumContrast,
    coolGradientStartContainer = coolGradientStartContainerLightMediumContrast,
    onCoolGradientStartContainer = onCoolGradientStartContainerLightMediumContrast,
    coolGradientEnd = coolGradientEndLightMediumContrast,
    onCoolGradientEnd = onCoolGradientEndLightMediumContrast,
    coolGradientEndContainer = coolGradientEndContainerLightMediumContrast,
    onCoolGradientEndContainer = onCoolGradientEndContainerLightMediumContrast,
    warmGradientStart = warmGradientStartLightMediumContrast,
    onWarmGradientStart = onWarmGradientStartLightMediumContrast,
    warmGradientStartContainer = warmGradientStartContainerLightMediumContrast,
    onWarmGradientStartContainer = onWarmGradientStartContainerLightMediumContrast,
    warmGradientEnd = warmGradientEndLightMediumContrast,
    onWarmGradientEnd = onWarmGradientEndLightMediumContrast,
    warmGradientEndContainer = warmGradientEndContainerLightMediumContrast,
    onWarmGradientEndContainer = onWarmGradientEndContainerLightMediumContrast,
    hotGradientStart = hotGradientStartLightMediumContrast,
    onHotGradientStart = onHotGradientStartLightMediumContrast,
    hotGradientStartContainer = hotGradientStartContainerLightMediumContrast,
    onHotGradientStartContainer = onHotGradientStartContainerLightMediumContrast,
    hotGradientEnd = hotGradientEndLightMediumContrast,
    onHotGradientEnd = onHotGradientEndLightMediumContrast,
    hotGradientEndContainer = hotGradientEndContainerLightMediumContrast,
    onHotGradientEndContainer = onHotGradientEndContainerLightMediumContrast,
    cta = ctaLightMediumContrast,
    onCta = onCtaLightMediumContrast,
    ctaContainer = ctaContainerLightMediumContrast,
    onCtaContainer = onCtaContainerLightMediumContrast
)

val extendedLightHighContrast = ExtendedColorScheme(
    tmdbGradientStart = tmdbGradientStartLightHighContrast,
    onTmdbGradientStart = onTmdbGradientStartLightHighContrast,
    tmdbGradientStartContainer = tmdbGradientStartContainerLightHighContrast,
    onTmdbGradientStartContainer = onTmdbGradientStartContainerLightHighContrast,
    tmdbGradientEnd = tmdbGradientEndLightHighContrast,
    onTmdbGradientEnd = onTmdbGradientEndLightHighContrast,
    tmdbGradientEndContainer = tmdbGradientEndContainerLightHighContrast,
    onTmdbGradientEndContainer = onTmdbGradientEndContainerLightHighContrast,
    coolGradientStart = coolGradientStartLightHighContrast,
    onCoolGradientStart = onCoolGradientStartLightHighContrast,
    coolGradientStartContainer = coolGradientStartContainerLightHighContrast,
    onCoolGradientStartContainer = onCoolGradientStartContainerLightHighContrast,
    coolGradientEnd = coolGradientEndLightHighContrast,
    onCoolGradientEnd = onCoolGradientEndLightHighContrast,
    coolGradientEndContainer = coolGradientEndContainerLightHighContrast,
    onCoolGradientEndContainer = onCoolGradientEndContainerLightHighContrast,
    warmGradientStart = warmGradientStartLightHighContrast,
    onWarmGradientStart = onWarmGradientStartLightHighContrast,
    warmGradientStartContainer = warmGradientStartContainerLightHighContrast,
    onWarmGradientStartContainer = onWarmGradientStartContainerLightHighContrast,
    warmGradientEnd = warmGradientEndLightHighContrast,
    onWarmGradientEnd = onWarmGradientEndLightHighContrast,
    warmGradientEndContainer = warmGradientEndContainerLightHighContrast,
    onWarmGradientEndContainer = onWarmGradientEndContainerLightHighContrast,
    hotGradientStart = hotGradientStartLightHighContrast,
    onHotGradientStart = onHotGradientStartLightHighContrast,
    hotGradientStartContainer = hotGradientStartContainerLightHighContrast,
    onHotGradientStartContainer = onHotGradientStartContainerLightHighContrast,
    hotGradientEnd = hotGradientEndLightHighContrast,
    onHotGradientEnd = onHotGradientEndLightHighContrast,
    hotGradientEndContainer = hotGradientEndContainerLightHighContrast,
    onHotGradientEndContainer = onHotGradientEndContainerLightHighContrast,
    cta = ctaLightHighContrast,
    onCta = onCtaLightHighContrast,
    ctaContainer = ctaContainerLightHighContrast,
    onCtaContainer = onCtaContainerLightHighContrast
)

val extendedDarkMediumContrast = ExtendedColorScheme(
    tmdbGradientStart = tmdbGradientStartDarkMediumContrast,
    onTmdbGradientStart = onTmdbGradientStartDarkMediumContrast,
    tmdbGradientStartContainer = tmdbGradientStartContainerDarkMediumContrast,
    onTmdbGradientStartContainer = onTmdbGradientStartContainerDarkMediumContrast,
    tmdbGradientEnd = tmdbGradientEndDarkMediumContrast,
    onTmdbGradientEnd = onTmdbGradientEndDarkMediumContrast,
    tmdbGradientEndContainer = tmdbGradientEndContainerDarkMediumContrast,
    onTmdbGradientEndContainer = onTmdbGradientEndContainerDarkMediumContrast,
    coolGradientStart = coolGradientStartDarkMediumContrast,
    onCoolGradientStart = onCoolGradientStartDarkMediumContrast,
    coolGradientStartContainer = coolGradientStartContainerDarkMediumContrast,
    onCoolGradientStartContainer = onCoolGradientStartContainerDarkMediumContrast,
    coolGradientEnd = coolGradientEndDarkMediumContrast,
    onCoolGradientEnd = onCoolGradientEndDarkMediumContrast,
    coolGradientEndContainer = coolGradientEndContainerDarkMediumContrast,
    onCoolGradientEndContainer = onCoolGradientEndContainerDarkMediumContrast,
    warmGradientStart = warmGradientStartDarkMediumContrast,
    onWarmGradientStart = onWarmGradientStartDarkMediumContrast,
    warmGradientStartContainer = warmGradientStartContainerDarkMediumContrast,
    onWarmGradientStartContainer = onWarmGradientStartContainerDarkMediumContrast,
    warmGradientEnd = warmGradientEndDarkMediumContrast,
    onWarmGradientEnd = onWarmGradientEndDarkMediumContrast,
    warmGradientEndContainer = warmGradientEndContainerDarkMediumContrast,
    onWarmGradientEndContainer = onWarmGradientEndContainerDarkMediumContrast,
    hotGradientStart = hotGradientStartDarkMediumContrast,
    onHotGradientStart = onHotGradientStartDarkMediumContrast,
    hotGradientStartContainer = hotGradientStartContainerDarkMediumContrast,
    onHotGradientStartContainer = onHotGradientStartContainerDarkMediumContrast,
    hotGradientEnd = hotGradientEndDarkMediumContrast,
    onHotGradientEnd = onHotGradientEndDarkMediumContrast,
    hotGradientEndContainer = hotGradientEndContainerDarkMediumContrast,
    onHotGradientEndContainer = onHotGradientEndContainerDarkMediumContrast,
    cta = ctaDarkMediumContrast,
    onCta = onCtaDarkMediumContrast,
    ctaContainer = ctaContainerDarkMediumContrast,
    onCtaContainer = onCtaContainerDarkMediumContrast
)

val extendedDarkHighContrast = ExtendedColorScheme(
    tmdbGradientStart = tmdbGradientStartDarkHighContrast,
    onTmdbGradientStart = onTmdbGradientStartDarkHighContrast,
    tmdbGradientStartContainer = tmdbGradientStartContainerDarkHighContrast,
    onTmdbGradientStartContainer = onTmdbGradientStartContainerDarkHighContrast,
    tmdbGradientEnd = tmdbGradientEndDarkHighContrast,
    onTmdbGradientEnd = onTmdbGradientEndDarkHighContrast,
    tmdbGradientEndContainer = tmdbGradientEndContainerDarkHighContrast,
    onTmdbGradientEndContainer = onTmdbGradientEndContainerDarkHighContrast,
    coolGradientStart = coolGradientStartDarkHighContrast,
    onCoolGradientStart = onCoolGradientStartDarkHighContrast,
    coolGradientStartContainer = coolGradientStartContainerDarkHighContrast,
    onCoolGradientStartContainer = onCoolGradientStartContainerDarkHighContrast,
    coolGradientEnd = coolGradientEndDarkHighContrast,
    onCoolGradientEnd = onCoolGradientEndDarkHighContrast,
    coolGradientEndContainer = coolGradientEndContainerDarkHighContrast,
    onCoolGradientEndContainer = onCoolGradientEndContainerDarkHighContrast,
    warmGradientStart = warmGradientStartDarkHighContrast,
    onWarmGradientStart = onWarmGradientStartDarkHighContrast,
    warmGradientStartContainer = warmGradientStartContainerDarkHighContrast,
    onWarmGradientStartContainer = onWarmGradientStartContainerDarkHighContrast,
    warmGradientEnd = warmGradientEndDarkHighContrast,
    onWarmGradientEnd = onWarmGradientEndDarkHighContrast,
    warmGradientEndContainer = warmGradientEndContainerDarkHighContrast,
    onWarmGradientEndContainer = onWarmGradientEndContainerDarkHighContrast,
    hotGradientStart = hotGradientStartDarkHighContrast,
    onHotGradientStart = onHotGradientStartDarkHighContrast,
    hotGradientStartContainer = hotGradientStartContainerDarkHighContrast,
    onHotGradientStartContainer = onHotGradientStartContainerDarkHighContrast,
    hotGradientEnd = hotGradientEndDarkHighContrast,
    onHotGradientEnd = onHotGradientEndDarkHighContrast,
    hotGradientEndContainer = hotGradientEndContainerDarkHighContrast,
    onHotGradientEndContainer = onHotGradientEndContainerDarkHighContrast,
    cta = ctaDarkHighContrast,
    onCta = onCtaDarkHighContrast,
    ctaContainer = ctaContainerDarkHighContrast,
    onCtaContainer = onCtaContainerDarkHighContrast
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
        tmdbGradientStart = Color.Unspecified,
        onTmdbGradientStart = Color.Unspecified,
        tmdbGradientStartContainer = Color.Unspecified,
        onTmdbGradientStartContainer = Color.Unspecified,
        tmdbGradientEnd = Color.Unspecified,
        onTmdbGradientEnd = Color.Unspecified,
        tmdbGradientEndContainer = Color.Unspecified,
        onTmdbGradientEndContainer = Color.Unspecified,
        coolGradientStart = Color.Unspecified,
        onCoolGradientStart = Color.Unspecified,
        coolGradientStartContainer = Color.Unspecified,
        onCoolGradientStartContainer = Color.Unspecified,
        coolGradientEnd = Color.Unspecified,
        onCoolGradientEnd = Color.Unspecified,
        coolGradientEndContainer = Color.Unspecified,
        onCoolGradientEndContainer = Color.Unspecified,
        warmGradientStart = Color.Unspecified,
        onWarmGradientStart = Color.Unspecified,
        warmGradientStartContainer = Color.Unspecified,
        onWarmGradientStartContainer = Color.Unspecified,
        warmGradientEnd = Color.Unspecified,
        onWarmGradientEnd = Color.Unspecified,
        warmGradientEndContainer = Color.Unspecified,
        onWarmGradientEndContainer = Color.Unspecified,
        hotGradientStart = Color.Unspecified,
        onHotGradientStart = Color.Unspecified,
        hotGradientStartContainer = Color.Unspecified,
        onHotGradientStartContainer = Color.Unspecified,
        hotGradientEnd = Color.Unspecified,
        onHotGradientEnd = Color.Unspecified,
        hotGradientEndContainer = Color.Unspecified,
        onHotGradientEndContainer = Color.Unspecified,
        cta = Color.Unspecified,
        onCta = Color.Unspecified,
        ctaContainer = Color.Unspecified,
        onCtaContainer = Color.Unspecified
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
