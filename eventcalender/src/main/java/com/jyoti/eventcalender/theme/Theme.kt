package com.jyoti.eventcalender.theme

import android.os.Build
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import com.jyoti.eventcalender.R

/**
 * Light default theme color scheme
 */
@VisibleForTesting
val LightDefaultColorScheme = lightColorScheme(
    primary = ColorPrimary.Base.id,
    onPrimary = ColorMain.White.id,
    primaryContainer = ColorMain.White.id,
    onPrimaryContainer = ColorPrimary.Base.id,
    secondary = ColorPrimary.Base.id,
    onSecondary = ColorMain.White.id,
    secondaryContainer = ColorMain.White.id,
    onSecondaryContainer = ColorPrimary.Base.id,
    tertiary = ColorPrimary.Base.id,
    onTertiary = ColorMain.White.id,
    tertiaryContainer = ColorMain.White.id,
    onTertiaryContainer = ColorPrimary.Base.id,
    error = ColorError.Base.id,
    onError = ColorError.Base.id,
    errorContainer = ColorError.Base.id,
    onErrorContainer = ColorError.Base.id,
    background = ColorBackground.Body.id,
    onBackground = ColorText.Base.id,
    surface = ColorBackground.Body.id,
    onSurface = ColorText.Base.id,
    surfaceVariant = ColorMain.White.id,
    onSurfaceVariant = ColorText.DISABLED.id,
    outline = ColorGrey._50.id
)

/**
 * Dark default theme color scheme
 */
@VisibleForTesting
val DarkDefaultColorScheme = darkColorScheme(
    primary = ColorPrimary.Base.id,
    onPrimary = ColorMain.White.id,
    primaryContainer = ColorMain.White.id,
    onPrimaryContainer = ColorPrimary.Base.id,
    secondary = ColorPrimary.Base.id,
    onSecondary = ColorMain.White.id,
    secondaryContainer = ColorMain.White.id,
    onSecondaryContainer = ColorPrimary.Base.id,
    tertiary = ColorPrimary.Base.id,
    onTertiary = ColorMain.White.id,
    tertiaryContainer = ColorMain.White.id,
    onTertiaryContainer = ColorPrimary.Base.id,
    error = ColorError.Base.id,
    onError = ColorError.Base.id,
    errorContainer = ColorError.Base.id,
    onErrorContainer = ColorError.Base.id,
    background = ColorBackground.Body.id,
    onBackground = ColorText.Base.id,
    surface = ColorBackground.Body.id,
    onSurface = ColorText.Base.id,
    surfaceVariant = ColorMain.White.id,
    onSurfaceVariant = ColorText.DISABLED.id,
    outline = ColorGrey._50.id
)

/**
 * Light Android theme color scheme
 */
@VisibleForTesting
val LightAndroidColorScheme = lightColorScheme(
    primary = ColorPrimary.Base.id,
    onPrimary = ColorMain.White.id,
    primaryContainer = ColorMain.White.id,
    onPrimaryContainer = ColorPrimary.Base.id,
    secondary = ColorPrimary.Base.id,
    onSecondary = ColorMain.White.id,
    secondaryContainer = ColorMain.White.id,
    onSecondaryContainer = ColorPrimary.Base.id,
    tertiary = ColorPrimary.Base.id,
    onTertiary = ColorMain.White.id,
    tertiaryContainer = ColorMain.White.id,
    onTertiaryContainer = ColorPrimary.Base.id,
    error = ColorError.Base.id,
    onError = ColorError.Base.id,
    errorContainer = ColorError.Base.id,
    onErrorContainer = ColorError.Base.id,
    background = ColorBackground.Body.id,
    onBackground = ColorText.Base.id,
    surface = ColorBackground.Body.id,
    onSurface = ColorText.Base.id,
    surfaceVariant = ColorMain.White.id,
    onSurfaceVariant = ColorText.DISABLED.id,
    outline = ColorGrey._50.id
)

/**
 * Dark Android theme color scheme
 */
@VisibleForTesting
val DarkAndroidColorScheme = darkColorScheme(
    primary = ColorPrimary.Base.id,
    onPrimary = ColorMain.White.id,
    primaryContainer = ColorMain.White.id,
    onPrimaryContainer = ColorPrimary.Base.id,
    secondary = ColorPrimary.Base.id,
    onSecondary = ColorMain.White.id,
    secondaryContainer = ColorMain.White.id,
    onSecondaryContainer = ColorPrimary.Base.id,
    tertiary = ColorPrimary.Base.id,
    onTertiary = ColorMain.White.id,
    tertiaryContainer = ColorMain.White.id,
    onTertiaryContainer = ColorPrimary.Base.id,
    error = ColorError.Base.id,
    onError = ColorError.Base.id,
    errorContainer = ColorError.Base.id,
    onErrorContainer = ColorError.Base.id,
    background = ColorBackground.Body.id,
    onBackground = ColorText.Base.id,
    surface = ColorBackground.Body.id,
    onSurface = ColorText.Base.id,
    surfaceVariant = ColorMain.White.id,
    onSurfaceVariant = ColorText.DISABLED.id,
    outline = ColorGrey._50.id
)

/**
 * Light default gradient colors
 */
val LightDefaultGradientColors = GradientColors(
    primary = ColorPrimary.Base.id,
    secondary = ColorPrimary.Base.id,
    tertiary = ColorPrimary.Base.id,
    neutral = ColorPrimary.Base.id
)

/**
 * Light Android background theme
 */
val LightAndroidBackgroundTheme = BackgroundTheme(color = ColorBackground.Body.id)

/**
 * Dark Android background theme
 */
val DarkAndroidBackgroundTheme = BackgroundTheme(color = ColorBackground.Body.id)

/**
 * Theme.
 *
 * The order of precedence for the color scheme is: Dynamic color > Android theme > Default theme.
 * Dark theme is independent as all the aforementioned color schemes have light and dark versions.
 * The default theme color scheme is used by default.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 * @param dynamicColor Whether the theme should use a dynamic color scheme (Android 12+ only).
 * @param androidTheme Whether the theme should use the Android theme color scheme.
 */
@Composable
fun CalenderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    androidTheme: Boolean = false,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            } else {
                if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
            }
        }
        androidTheme -> if (darkTheme) DarkAndroidColorScheme else LightAndroidColorScheme
        else -> if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
    }

//    val colorScheme = LightDefaultColorScheme

    val defaultGradientColors = GradientColors()
    val gradientColors = when {
        dynamicColor -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                defaultGradientColors
            } else {
                if (darkTheme) defaultGradientColors else LightDefaultGradientColors
            }
        }
        androidTheme -> defaultGradientColors
        else -> if (darkTheme) defaultGradientColors else LightDefaultGradientColors
    }

    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = dimensionResource(id = R.dimen.spacing_tiny)
    )
    val backgroundTheme = when {
        dynamicColor -> defaultBackgroundTheme
        androidTheme -> if (darkTheme) DarkAndroidBackgroundTheme else LightAndroidBackgroundTheme
        else -> defaultBackgroundTheme
    }

    CompositionLocalProvider(
        LocalGradientColors provides gradientColors,
        LocalBackgroundTheme provides backgroundTheme,
        LocalCalenderTypography provides calenderTypography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

object CalenderTheme {
    val typography: CalenderTypography
        @Composable
        get() = LocalCalenderTypography.current
}
