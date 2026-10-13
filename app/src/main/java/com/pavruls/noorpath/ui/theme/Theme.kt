package com.pavruls.noorpath.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.pavruls.noorpath.data.preferences.ThemeSettings

// ============ Light Color Scheme ============
private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = GreenOnPrimary,
    primaryContainer = GreenContainer,
    onPrimaryContainer = GreenOnContainer,
    secondary = GreenSecondary,
    onSecondary = GreenOnSecondary,
    tertiary = GoldTertiary,
    onTertiary = GoldOnTertiary,
    tertiaryContainer = GoldContainer,
    onTertiaryContainer = GoldOnContainer,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    error = ErrorRed,
    onError = OnErrorWhite
)

// ============ Dark Color Scheme ============
private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimaryDark,
    onPrimary = GreenOnPrimary,
    primaryContainer = GreenPrimary,
    onPrimaryContainer = GreenContainer,
    secondary = GreenContainer,
    onSecondary = GreenOnContainer,
    tertiary = GoldTertiary,
    onTertiary = GoldOnTertiary,
    tertiaryContainer = GoldContainer,
    onTertiaryContainer = GoldOnContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    error = ErrorRedDark,
    onError = OnErrorWhite
)

@Composable
fun NoorPathTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = NoorPathTypography,
        content = content
    )
}

// ============ Helper: Determine Dark Mode from Settings ============
@Composable
fun shouldUseDarkTheme(): Boolean {
    val context = LocalContext.current
    val mode = ThemeSettings.getDarkMode(context)
    return when (mode) {
        ThemeSettings.MODE_DARK -> true
        ThemeSettings.MODE_LIGHT -> false
        else -> isSystemInDarkTheme()
    }
}