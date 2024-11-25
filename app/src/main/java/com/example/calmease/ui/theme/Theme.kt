package com.example.calmease.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


// Light Color Scheme
val LightColors = lightColorScheme(
    primary = CalmPrimaryLight,
    secondary = CalmSecondaryLight,
    tertiary = CalmTertiaryLight,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black
)

// Dark Color Scheme
val DarkColors = darkColorScheme(
    primary = CalmPrimaryDark,
    secondary = CalmSecondaryDark,
    tertiary = CalmTertiaryDark,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White
)



@Composable
fun CalmEaseTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = LightColors


    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
