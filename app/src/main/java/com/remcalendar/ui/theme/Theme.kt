package com.remcalendar.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RemCalendarTheme(
    darkTheme: Boolean = true, // 默认使用深色主题以配合玻璃效果
    content: @Composable () -> Unit
) {
    val accentColor = rememberAccentColor()

    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme(
            primary = accentColor,
            secondary = accentColor.copy(alpha = 0.7f),
            tertiary = accentColor.copy(alpha = 0.5f),
            background = Color.Black,
            surface = Color(0xFF121212),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onTertiary = Color.White,
            onBackground = TextPrimary,
            onSurface = TextPrimary,
            surfaceVariant = Color.White.copy(alpha = 0.1f)
        )
        else -> lightColorScheme(
            primary = accentColor,
            secondary = accentColor.copy(alpha = 0.7f),
            tertiary = accentColor.copy(alpha = 0.5f),
            background = Color.White,
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onTertiary = Color.White,
            onBackground = TextPrimaryDark,
            onSurface = TextPrimaryDark,
            surfaceVariant = Color.Black.copy(alpha = 0.05f)
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
