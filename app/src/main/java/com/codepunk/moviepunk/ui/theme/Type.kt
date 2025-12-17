package com.codepunk.moviepunk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.codepunk.moviepunk.R

val sourceSans3Family = FontFamily(
    Font(
        resId = R.font.source_sans_3_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.source_sans_3_extra_light,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.source_sans_3_light,
        weight = FontWeight.Light,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.source_sans_3_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.source_sans_3_semibold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.source_sans_3_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.source_sans_3_extra_bold,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.source_sans_3_black,
        weight = FontWeight.Black,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.source_sans_3_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.source_sans_3_extra_light_italic,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.source_sans_3_light_italic,
        weight = FontWeight.Light,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.source_sans_3_medium_italic,
        weight = FontWeight.Medium,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.source_sans_3_semibold_italic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.source_sans_3_bold_italic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.source_sans_3_extra_bold_italic,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.source_sans_3_black_italic,
        weight = FontWeight.Black,
        style = FontStyle.Italic
    )
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(
        fontFamily = sourceSans3Family,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = baseline.displayMedium.copy(
        fontFamily = sourceSans3Family,
        fontWeight = FontWeight.Bold
    ),
    displaySmall = baseline.displaySmall.copy(
        fontFamily = sourceSans3Family,
        fontWeight = FontWeight.Bold
    ),
    headlineLarge = baseline.headlineLarge.copy(
        fontFamily = sourceSans3Family,
        fontWeight = FontWeight.SemiBold
    ),
    headlineMedium = baseline.headlineMedium.copy(
        fontFamily = sourceSans3Family,
        fontWeight = FontWeight.SemiBold
    ),
    headlineSmall = baseline.headlineSmall.copy(
        fontFamily = sourceSans3Family,
        fontWeight = FontWeight.SemiBold
    ),
    titleLarge = baseline.titleLarge.copy(
        fontFamily = sourceSans3Family,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = baseline.titleMedium.copy(
        fontFamily = sourceSans3Family,
        fontWeight = FontWeight.SemiBold
    ),
    titleSmall = baseline.titleSmall.copy(
        fontFamily = sourceSans3Family,
        fontWeight = FontWeight.SemiBold
    ),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = sourceSans3Family),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = sourceSans3Family),
    bodySmall = baseline.bodySmall.copy(fontFamily = sourceSans3Family),
    labelLarge = baseline.labelLarge.copy(fontFamily = sourceSans3Family),
    labelMedium = baseline.labelMedium.copy(fontFamily = sourceSans3Family),
    labelSmall = baseline.labelSmall.copy(fontFamily = sourceSans3Family),
)