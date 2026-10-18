package com.timeback.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = CreamAccent,
    onPrimary = InkDark,
    background = BgMidnight,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    secondary = ApricotAccent,
    error = RotRed
)

@Composable
fun TimeBackTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
