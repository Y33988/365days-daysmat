package com.remcalendar.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSizeClass { COMPACT, MEDIUM, EXPANDED }
enum class Orientation { PORTRAIT, LANDSCAPE }

@Composable
fun rememberWindowSize(): WindowSizeInfo {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    return WindowSizeInfo(
        widthSizeClass = when {
            screenWidth < 600.dp -> WindowSizeClass.COMPACT
            screenWidth < 840.dp -> WindowSizeClass.MEDIUM
            else -> WindowSizeClass.EXPANDED
        },
        heightSizeClass = when {
            screenHeight < 480.dp -> WindowSizeClass.COMPACT
            screenHeight < 900.dp -> WindowSizeClass.MEDIUM
            else -> WindowSizeClass.EXPANDED
        },
        screenWidth = screenWidth,
        screenHeight = screenHeight,
        orientation = if (configuration.screenWidthDp > configuration.screenHeightDp)
            Orientation.LANDSCAPE else Orientation.PORTRAIT
    )
}

data class WindowSizeInfo(
    val widthSizeClass: WindowSizeClass,
    val heightSizeClass: WindowSizeClass,
    val screenWidth: Dp,
    val screenHeight: Dp,
    val orientation: Orientation
) {
    val isCompact: Boolean get() = widthSizeClass == WindowSizeClass.COMPACT
    val isMediumOrExpanded: Boolean get() = widthSizeClass != WindowSizeClass.COMPACT
    val isLandscape: Boolean get() = orientation == Orientation.LANDSCAPE
}

@Composable
fun adaptivePadding(): AdaptivePadding {
    val windowSize = rememberWindowSize()
    return when (windowSize.widthSizeClass) {
        WindowSizeClass.COMPACT -> AdaptivePadding(
            screenPadding = 12.dp,
            cardPadding = 12.dp,
            cardSpacing = 10.dp,
            cornerRadius = 16.dp
        )
        WindowSizeClass.MEDIUM -> AdaptivePadding(
            screenPadding = 20.dp,
            cardPadding = 18.dp,
            cardSpacing = 16.dp,
            cornerRadius = 20.dp
        )
        WindowSizeClass.EXPANDED -> AdaptivePadding(
            screenPadding = 32.dp,
            cardPadding = 24.dp,
            cardSpacing = 20.dp,
            cornerRadius = 24.dp
        )
    }
}

data class AdaptivePadding(
    val screenPadding: Dp,
    val cardPadding: Dp,
    val cardSpacing: Dp,
    val cornerRadius: Dp,
    val isCompact: Boolean = screenPadding < 16.dp
)
