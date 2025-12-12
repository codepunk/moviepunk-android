@file:Suppress("unused")

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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Immutable
data class ExtendedColorScheme(
    val darkGray: ColorFamily,
    val blue: ColorFamily,
    val blueGray: ColorFamily,
    val orange: ColorFamily,
    val yellow: ColorFamily,
    val lightGreen: ColorFamily,
    val lilac: ColorFamily,
    val magenta: ColorFamily
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
    darkGray = ColorFamily(
        darkGrayLight,
        onDarkGrayLight,
        darkGrayContainerLight,
        onDarkGrayContainerLight,
    ),
    blue = ColorFamily(
        blueLight,
        onBlueLight,
        blueContainerLight,
        onBlueContainerLight,
    ),
    blueGray = ColorFamily(
        blueGrayLight,
        onBlueGrayLight,
        blueGrayContainerLight,
        onBlueGrayContainerLight,
    ),
    orange = ColorFamily(
        orangeLight,
        onOrangeLight,
        orangeContainerLight,
        onOrangeContainerLight,
    ),
    yellow = ColorFamily(
        yellowLight,
        onYellowLight,
        yellowContainerLight,
        onYellowContainerLight,
    ),
    lightGreen = ColorFamily(
        lightGreenLight,
        onLightGreenLight,
        lightGreenContainerLight,
        onLightGreenContainerLight,
    ),
    lilac = ColorFamily(
        lilacLight,
        onLilacLight,
        lilacContainerLight,
        onLilacContainerLight,
    ),
    magenta = ColorFamily(
        magentaLight,
        onMagentaLight,
        magentaContainerLight,
        onMagentaContainerLight,
    )
)

val extendedDark = ExtendedColorScheme(
    darkGray = ColorFamily(
        darkGrayDark,
        onDarkGrayDark,
        darkGrayContainerDark,
        onDarkGrayContainerDark,
    ),
    blue = ColorFamily(
        blueDark,
        onBlueDark,
        blueContainerDark,
        onBlueContainerDark,
    ),
    blueGray = ColorFamily(
        blueGrayDark,
        onBlueGrayDark,
        blueGrayContainerDark,
        onBlueGrayContainerDark,
    ),
    orange = ColorFamily(
        orangeDark,
        onOrangeDark,
        orangeContainerDark,
        onOrangeContainerDark,
    ),
    yellow = ColorFamily(
        yellowDark,
        onYellowDark,
        yellowContainerDark,
        onYellowContainerDark,
    ),
    lightGreen = ColorFamily(
        lightGreenDark,
        onLightGreenDark,
        lightGreenContainerDark,
        onLightGreenContainerDark,
    ),
    lilac = ColorFamily(
        lilacDark,
        onLilacDark,
        lilacContainerDark,
        onLilacContainerDark,
    ),
    magenta = ColorFamily(
        magentaDark,
        onMagentaDark,
        magentaContainerDark,
        onMagentaContainerDark,
    )
)

val extendedLightMediumContrast = ExtendedColorScheme(
    darkGray = ColorFamily(
        darkGrayLightMediumContrast,
        onDarkGrayLightMediumContrast,
        darkGrayContainerLightMediumContrast,
        onDarkGrayContainerLightMediumContrast,
    ),
    blue = ColorFamily(
        blueLightMediumContrast,
        onBlueLightMediumContrast,
        blueContainerLightMediumContrast,
        onBlueContainerLightMediumContrast,
    ),
    blueGray = ColorFamily(
        blueGrayLightMediumContrast,
        onBlueGrayLightMediumContrast,
        blueGrayContainerLightMediumContrast,
        onBlueGrayContainerLightMediumContrast,
    ),
    orange = ColorFamily(
        orangeLightMediumContrast,
        onOrangeLightMediumContrast,
        orangeContainerLightMediumContrast,
        onOrangeContainerLightMediumContrast,
    ),
    yellow = ColorFamily(
        yellowLightMediumContrast,
        onYellowLightMediumContrast,
        yellowContainerLightMediumContrast,
        onYellowContainerLightMediumContrast,
    ),
    lightGreen = ColorFamily(
        lightGreenLightMediumContrast,
        onLightGreenLightMediumContrast,
        lightGreenContainerLightMediumContrast,
        onLightGreenContainerLightMediumContrast,
    ),
    lilac = ColorFamily(
        lilacLightMediumContrast,
        onLilacLightMediumContrast,
        lilacContainerLightMediumContrast,
        onLilacContainerLightMediumContrast,
    ),
    magenta = ColorFamily(
        magentaLightMediumContrast,
        onMagentaLightMediumContrast,
        magentaContainerLightMediumContrast,
        onMagentaContainerLightMediumContrast
    )
)

val extendedLightHighContrast = ExtendedColorScheme(
    darkGray = ColorFamily(
        darkGrayLightHighContrast,
        onDarkGrayLightHighContrast,
        darkGrayContainerLightHighContrast,
        onDarkGrayContainerLightHighContrast,
    ),
    blue = ColorFamily(
        blueLightHighContrast,
        onBlueLightHighContrast,
        blueContainerLightHighContrast,
        onBlueContainerLightHighContrast,
    ),
    blueGray = ColorFamily(
        blueGrayLightHighContrast,
        onBlueGrayLightHighContrast,
        blueGrayContainerLightHighContrast,
        onBlueGrayContainerLightHighContrast,
    ),
    orange = ColorFamily(
        orangeLightHighContrast,
        onOrangeLightHighContrast,
        orangeContainerLightHighContrast,
        onOrangeContainerLightHighContrast,
    ),
    yellow = ColorFamily(
        yellowLightHighContrast,
        onYellowLightHighContrast,
        yellowContainerLightHighContrast,
        onYellowContainerLightHighContrast,
    ),
    lightGreen = ColorFamily(
        lightGreenLightHighContrast,
        onLightGreenLightHighContrast,
        lightGreenContainerLightHighContrast,
        onLightGreenContainerLightHighContrast,
    ),
    lilac = ColorFamily(
        lilacLightHighContrast,
        onLilacLightHighContrast,
        lilacContainerLightHighContrast,
        onLilacContainerLightHighContrast,
    ),
    magenta = ColorFamily(
        magentaLightHighContrast,
        onMagentaLightHighContrast,
        magentaContainerLightHighContrast,
        onMagentaContainerLightHighContrast
    )
)

val extendedDarkMediumContrast = ExtendedColorScheme(
    darkGray = ColorFamily(
        darkGrayDarkMediumContrast,
        onDarkGrayDarkMediumContrast,
        darkGrayContainerDarkMediumContrast,
        onDarkGrayContainerDarkMediumContrast,
    ),
    blue = ColorFamily(
        blueDarkMediumContrast,
        onBlueDarkMediumContrast,
        blueContainerDarkMediumContrast,
        onBlueContainerDarkMediumContrast,
    ),
    blueGray = ColorFamily(
        blueGrayDarkMediumContrast,
        onBlueGrayDarkMediumContrast,
        blueGrayContainerDarkMediumContrast,
        onBlueGrayContainerDarkMediumContrast,
    ),
    orange = ColorFamily(
        orangeDarkMediumContrast,
        onOrangeDarkMediumContrast,
        orangeContainerDarkMediumContrast,
        onOrangeContainerDarkMediumContrast,
    ),
    yellow = ColorFamily(
        yellowDarkMediumContrast,
        onYellowDarkMediumContrast,
        yellowContainerDarkMediumContrast,
        onYellowContainerDarkMediumContrast,
    ),
    lightGreen = ColorFamily(
        lightGreenDarkMediumContrast,
        onLightGreenDarkMediumContrast,
        lightGreenContainerDarkMediumContrast,
        onLightGreenContainerDarkMediumContrast,
    ),
    lilac = ColorFamily(
        lilacDarkMediumContrast,
        onLilacDarkMediumContrast,
        lilacContainerDarkMediumContrast,
        onLilacContainerDarkMediumContrast,
    ),
    magenta = ColorFamily(
        magentaDarkMediumContrast,
        onMagentaDarkMediumContrast,
        magentaContainerDarkMediumContrast,
        onMagentaContainerDarkMediumContrast
    )
)

val extendedDarkHighContrast = ExtendedColorScheme(
    darkGray = ColorFamily(
        darkGrayDarkHighContrast,
        onDarkGrayDarkHighContrast,
        darkGrayContainerDarkHighContrast,
        onDarkGrayContainerDarkHighContrast,
    ),
    blue = ColorFamily(
        blueDarkHighContrast,
        onBlueDarkHighContrast,
        blueContainerDarkHighContrast,
        onBlueContainerDarkHighContrast,
    ),
    blueGray = ColorFamily(
        blueGrayDarkHighContrast,
        onBlueGrayDarkHighContrast,
        blueGrayContainerDarkHighContrast,
        onBlueGrayContainerDarkHighContrast,
    ),
    orange = ColorFamily(
        orangeDarkHighContrast,
        onOrangeDarkHighContrast,
        orangeContainerDarkHighContrast,
        onOrangeContainerDarkHighContrast,
    ),
    yellow = ColorFamily(
        yellowDarkHighContrast,
        onYellowDarkHighContrast,
        yellowContainerDarkHighContrast,
        onYellowContainerDarkHighContrast,
    ),
    lightGreen = ColorFamily(
        lightGreenDarkHighContrast,
        onLightGreenDarkHighContrast,
        lightGreenContainerDarkHighContrast,
        onLightGreenContainerDarkHighContrast,
    ),
    lilac = ColorFamily(
        lilacDarkHighContrast,
        onLilacDarkHighContrast,
        lilacContainerDarkHighContrast,
        onLilacContainerDarkHighContrast,
    ),
    magenta = ColorFamily(
        magentaDarkHighContrast,
        onMagentaDarkHighContrast,
        magentaContainerDarkHighContrast,
        onMagentaContainerDarkHighContrast
    )
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

        darkTheme -> darkScheme
        else -> lightScheme
    }

    CompositionLocalProvider(LocalDimens provides fixedDimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}
