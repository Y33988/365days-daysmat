package com.remcalendar.ui.theme

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.remcalendar.data.model.BackgroundType

private val templateGradients = mapOf(
    "default" to listOf(BackgroundGradientStart, BackgroundGradientMid, BackgroundGradientEnd),
    "sunset" to listOf(Color(0xFF1A0F24), Color(0xFF3D1E32), Color(0xFF6B2E2A)),
    "forest" to listOf(Color(0xFF071215), Color(0xFF112226), Color(0xFF1A333A)),
    "purple" to listOf(Color(0xFF120820), Color(0xFF2E1248), Color(0xFF4E1B66)),
    "ocean" to listOf(Color(0xFF000216), Color(0xFF001A3A), Color(0xFF003060)),
    "minimal" to listOf(Color(0xFF111214), Color(0xFF1E1F21), Color(0xFF2D2E30))
)

@Composable
fun BackgroundLayer(
    modifier: Modifier = Modifier,
    viewModel: BackgroundViewModel = hiltViewModel()
) {
    val backgroundConfig by viewModel.backgroundConfig.collectAsState()
    val gradientColors = templateGradients[backgroundConfig.builtinTemplate]
        ?: templateGradients["default"]!!

    val transparency = backgroundConfig.transparency.coerceIn(0.15f, 1f)
    val blurRadius = backgroundConfig.blurRadius.coerceIn(0f, 80f)
    val accentColor = Color(backgroundConfig.accentColor)
    val canBlur = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // 基础渐变层 - 应用透明度
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = transparency }
                .background(brush = Brush.verticalGradient(colors = gradientColors))
        )

        // 自定义图片层
        if (backgroundConfig.type == BackgroundType.CUSTOM && backgroundConfig.imagePath != null) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(backgroundConfig.imagePath)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = backgroundConfig.transparency
                        if (!canBlur && blurRadius > 0) {
                            scaleX = 1.08f
                            scaleY = 1.08f
                        }
                    }
                    .then(
                        if (canBlur && blurRadius > 0) {
                            Modifier.blur(radius = blurRadius.dp)
                        } else Modifier
                    ),
                contentScale = ContentScale.Crop
            )

            // 低版本额外遮罩增强可读性
            if (!canBlur && blurRadius > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = (blurRadius / 100f).coerceIn(0f, 0.55f)))
                )
            }
        }

        // 内置模板模糊度模拟遮罩（低版本 fallback）
        if (backgroundConfig.type != BackgroundType.CUSTOM && blurRadius > 0 && !canBlur) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = (blurRadius / 160f).coerceIn(0f, 0.35f)))
            )
        }

        // 全局遮罩 - 增强玻璃效果对比度
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.42f))
        )

        // 顶部强光晕 - iOS 风格
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.12f),
                                Color.White.copy(alpha = 0.04f),
                                Color.Transparent
                            ),
                            center = Offset(size.width * 0.5f, size.height * 0.12f),
                            radius = size.width * 0.9f
                        ),
                        radius = size.width * 0.9f,
                        center = Offset(size.width * 0.5f, size.height * 0.12f)
                    )
                }
        )

        // 底部暗角 - 增加层次感
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.35f)
                        ),
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0f
                    )
                )
        )

        // 强调色环境光晕 - 模拟环境反射
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                accentColor.copy(alpha = 0.09f),
                                Color.Transparent
                            ),
                            center = Offset(size.width * 0.2f, size.height * 0.7f),
                            radius = size.width * 0.7f
                        ),
                        radius = size.width * 0.7f,
                        center = Offset(size.width * 0.2f, size.height * 0.7f)
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                accentColor.copy(alpha = 0.06f),
                                Color.Transparent
                            ),
                            center = Offset(size.width * 0.85f, size.height * 0.25f),
                            radius = size.width * 0.55f
                        ),
                        radius = size.width * 0.55f,
                        center = Offset(size.width * 0.85f, size.height * 0.25f)
                    )
                }
        )
    }
}
