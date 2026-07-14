package com.remcalendar.ui.theme

import androidx.compose.ui.graphics.Color

// 主色调 - 蓝色系
val Blue200 = Color(0xFF90CAF9)
val Blue500 = Color(0xFF2196F3)
val Blue700 = Color(0xFF1976D2)

// 强调色变体
val AccentBlue = Color(0xFF2196F3)
val AccentPurple = Color(0xFF9C27B0)
val AccentPink = Color(0xFFE91E63)
val AccentRed = Color(0xFFF44336)
val AccentOrange = Color(0xFFFF9800)
val AccentGreen = Color(0xFF4CAF50)
val AccentTeal = Color(0xFF009688)
val AccentCyan = Color(0xFF00BCD4)

// 液态玻璃颜色 - iOS 26/27 风格
// 更亮的白色透射，模拟光线穿透玻璃的质感
val GlassWhite = Color.White.copy(alpha = 0.24f)
val GlassWhiteStrong = Color.White.copy(alpha = 0.42f)
val GlassWhiteGlow = Color.White.copy(alpha = 0.60f)
val GlassBorder = Color.White.copy(alpha = 0.52f)
val GlassBorderHighlight = Color.White.copy(alpha = 0.88f)
val GlassBorderSoft = Color.White.copy(alpha = 0.30f)
val GlassBackground = Color.White.copy(alpha = 0.12f)
// 玻璃折射色 - 微妙的蓝紫色调
val GlassTintBlue = Color(0xFF64B5F6).copy(alpha = 0.14f)
val GlassTintPurple = Color(0xFFBA68C8).copy(alpha = 0.10f)

// 文本颜色 - 增加对比度确保清晰可读
val TextPrimary = Color.White.copy(alpha = 0.96f)
val TextSecondary = Color.White.copy(alpha = 0.84f)
val TextTertiary = Color.White.copy(alpha = 0.62f)

// 深色模式文本
val TextPrimaryDark = Color.Black.copy(alpha = 0.95f)
val TextSecondaryDark = Color.Black.copy(alpha = 0.82f)

// 背景渐变 - 更深的底色让玻璃效果更突出
val BackgroundGradientStart = Color(0xFF050A10)
val BackgroundGradientMid = Color(0xFF0D1B2A)
val BackgroundGradientEnd = Color(0xFF162536)
