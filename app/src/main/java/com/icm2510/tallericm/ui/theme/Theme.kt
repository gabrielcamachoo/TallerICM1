package com.icm2510.tallericm.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color               // <-- IMPORT CLAVE

// Colores simples (o usa los de Color.kt si ya los tienes)
private val LightColors = lightColorScheme(
    primary = Color(0xFF6A4C93),
    onPrimary = Color.White
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFB1E5A4),
    onPrimary = Color.Black
)

@Composable
fun TallerICMTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),   // si tienes Type.kt con un val Typography, puedes usarlo aquí
        content = content
    )
}
