package com.remcalendar.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun rememberAccentColor(): Color {
    val viewModel: BackgroundViewModel = hiltViewModel()
    val backgroundConfig by viewModel.backgroundConfig.collectAsState()
    return Color(backgroundConfig.accentColor)
}
