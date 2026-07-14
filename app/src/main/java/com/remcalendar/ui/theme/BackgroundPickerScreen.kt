package com.remcalendar.ui.theme

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.remcalendar.data.model.BackgroundType
import com.remcalendar.ui.components.GlassCard
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import com.remcalendar.util.adaptivePadding
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundPickerScreen(
    onNavigateBack: () -> Unit,
    viewModel: BackgroundPickerViewModel = hiltViewModel()
) {
    val backgroundConfig by viewModel.backgroundConfig.collectAsState()
    val padding = adaptivePadding()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.setCustomImage(it) }
    }

    val builtinTemplates = listOf(
        "default" to "默认深蓝",
        "sunset" to "日落橙红",
        "forest" to "森林绿意",
        "purple" to "梦幻紫霞",
        "ocean" to "海洋之心",
        "minimal" to "极简灰白"
    )

    val templateGradients = mapOf(
        "default" to listOf(Color(0xFF0D1B2A), Color(0xFF1B263B), Color(0xFF2A3F54)),
        "sunset" to listOf(Color(0xFF2C1A3D), Color(0xFF5D2E46), Color(0xFFB85C38)),
        "forest" to listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364)),
        "purple" to listOf(Color(0xFF1A0B2E), Color(0xFF4A1C6F), Color(0xFF8034A0)),
        "ocean" to listOf(Color(0xFF000428), Color(0xFF004E92), Color(0xFF2196F3)),
        "minimal" to listOf(Color(0xFF232526), Color(0xFF414345), Color(0xFF666666))
    )

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color.Transparent),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = TextPrimary,
                    navigationIconContentColor = TextPrimary,
                    actionIconContentColor = TextPrimary
                ),
                title = { Text("自定义背景") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = padding.screenPadding),
            verticalArrangement = Arrangement.spacedBy(padding.cardSpacing)
        ) {
            // 预览区域
            GlassCard(cornerRadius = padding.cornerRadius) {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Text(
                        text = "预览",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(padding.cornerRadius - 4.dp))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = templateGradients[backgroundConfig.builtinTemplate]
                                        ?: templateGradients["default"]!!
                                )
                            )
                    ) {
                        if (backgroundConfig.type == BackgroundType.CUSTOM && backgroundConfig.imagePath != null) {
                            val canBlur = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                            Image(
                                painter = rememberAsyncImagePainter(model = backgroundConfig.imagePath),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .then(
                                        if (canBlur && backgroundConfig.blurRadius > 0) {
                                            Modifier.blur(radius = backgroundConfig.blurRadius.dp)
                                        } else Modifier
                                    ),
                                contentScale = ContentScale.Crop,
                                alpha = backgroundConfig.transparency
                            )
                        }

                        // 模拟遮罩和光晕
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.35f))
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0.08f),
                                            Color.Transparent
                                        ),
                                        radius = 350f
                                    )
                                )
                        )

                        // 示例文字
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "365days",
                                color = TextPrimary,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "液态玻璃效果预览",
                                color = TextSecondary,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // 背景类型选择
            GlassCard {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Text(
                        text = "背景来源",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        BackgroundTypeButton(
                            text = "内置模板",
                            selected = backgroundConfig.type == BackgroundType.BUILTIN,
                            onClick = { viewModel.setBackgroundType(BackgroundType.BUILTIN) },
                            modifier = Modifier.weight(1f),
                            padding = padding
                        )
                        BackgroundTypeButton(
                            text = "相册图片",
                            selected = backgroundConfig.type == BackgroundType.CUSTOM,
                            onClick = { galleryLauncher.launch("image/*") },
                            modifier = Modifier.weight(1f),
                            padding = padding
                        )
                    }
                }
            }

            // 内置模板选择
            if (backgroundConfig.type == BackgroundType.BUILTIN) {
                GlassCard {
                    Column(modifier = Modifier.padding(padding.cardPadding)) {
                        Text(
                            text = "选择模板",
                            color = TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        builtinTemplates.chunked(3).forEach { rowTemplates ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                rowTemplates.forEach { (key, name) ->
                                    TemplateItem(
                                        name = name,
                                        colors = templateGradients[key] ?: templateGradients["default"]!!,
                                        selected = backgroundConfig.builtinTemplate == key,
                                        onClick = { viewModel.setBuiltinTemplate(key) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

            // 透明度调节
            GlassCard {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "背景透明度",
                            color = TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${(backgroundConfig.transparency * 100).roundToInt()}%",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Slider(
                        value = backgroundConfig.transparency,
                        onValueChange = { viewModel.setTransparency(it) },
                        valueRange = 0f..1f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color.White.copy(alpha = 0.7f),
                            inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                        )
                    )
                }
            }

            // 模糊度调节
            GlassCard {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "模糊程度",
                            color = TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${backgroundConfig.blurRadius.roundToInt()} dp",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Slider(
                        value = backgroundConfig.blurRadius,
                        onValueChange = { viewModel.setBlurRadius(it) },
                        valueRange = 0f..60f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color.White.copy(alpha = 0.7f),
                            inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                        )
                    )
                }
            }

            // 强调色选择
            GlassCard {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Text(
                        text = "强调色",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val colors = listOf(
                            0xFF2196F3L to "蓝",
                            0xFF9C27B0L to "紫",
                            0xFFE91E63L to "粉",
                            0xFFF44336L to "红",
                            0xFFFF9800L to "橙",
                            0xFF4CAF50L to "绿",
                            0xFF009688L to "青",
                            0xFF00BCD4L to " cyan"
                        )
                        colors.forEach { (colorValue, _) ->
                            Box(
                                modifier = Modifier
                                    .size(if (padding.isCompact) 38.dp else 44.dp)
                                    .clip(CircleShape)
                                    .background(Color(colorValue))
                                    .border(
                                        width = if (backgroundConfig.accentColor == colorValue) 3.dp else 1.dp,
                                        color = if (backgroundConfig.accentColor == colorValue)
                                            Color.White else Color.White.copy(alpha = 0.4f),
                                        shape = CircleShape
                                    )
                                    .clickable { viewModel.setAccentColor(colorValue) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun BackgroundTypeButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    padding: com.remcalendar.util.AdaptivePadding = adaptivePadding()
) {
    Box(
        modifier = modifier
            .height(if (padding.isCompact) 44.dp else 48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) Color.White.copy(alpha = 0.25f)
                else Color.White.copy(alpha = 0.08f)
            )
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) Color.White.copy(alpha = 0.7f)
                else Color.White.copy(alpha = 0.25f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) TextPrimary else TextSecondary,
            fontSize = if (padding.isCompact) 14.sp else 15.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
        )
    }
}

@Composable
private fun TemplateItem(
    name: String,
    colors: List<Color>,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(brush = Brush.verticalGradient(colors))
                .border(
                    width = if (selected) 3.dp else 1.dp,
                    color = if (selected) Color.White.copy(alpha = 0.8f)
                    else Color.White.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(12.dp)
                )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = name,
            color = if (selected) TextPrimary else TextSecondary,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}
