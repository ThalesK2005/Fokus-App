package com.thales.fokus.ui.theme

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

// Configuração de cores escuras (Usando as mesmas do claro por enquanto para evitar erros)
private val DarkColorScheme = darkColorScheme(
    primary = PurplePrimary,
    secondary = PurpleDark,
    tertiary = CategoryPersonal
)

// Configuração de cores claras (Conforme o protótipo)
private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    secondary = PurpleDark,
    background = BackgroundWhite,
    surface = BackgroundWhite,
    onPrimary = Color.White,
    onBackground = TextBlack,
    onSurface = TextBlack
)

@Composable
fun FokusTheme( // MUDAMOS O NOME DE "Fokus1Theme" PARA "FokusTheme"
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Desliguei o dynamic color para garantir que nossas cores roxas apareçam
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}