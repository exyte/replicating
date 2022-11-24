package com.exyte.composesample.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Haiti,
    primaryVariant = CornflowerBlue,
    onPrimary = White,
    secondary = FrenchRose,
    onSecondary = White,
    background = White,
    onSurface = White,
)

private val LightColorPalette = lightColors(
    primary = Haiti,
    primaryVariant = CornflowerBlue,
    onPrimary = White,
    secondary = FrenchRose,
    onSecondary = White,
    background = White,
    onSurface = Haiti,
)

@Composable
fun PlayerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}