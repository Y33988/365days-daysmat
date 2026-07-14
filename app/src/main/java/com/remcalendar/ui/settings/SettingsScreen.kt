package com.remcalendar.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remcalendar.ui.components.GlassCard
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import com.remcalendar.util.adaptivePadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onBackgroundClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val startOfWeek by viewModel.startOfWeek.collectAsState()
    val defaultReminderMinutes by viewModel.defaultReminderMinutes.collectAsState()
    val padding = adaptivePadding()

    var showStartOfWeekDialog by remember { mutableStateOf(false) }
    var showReminderDialog by remember { mutableStateOf(false) }

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
                title = { Text("设置") },
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
            // 背景自定义
            GlassCard {
                SettingItem(
                    icon = Icons.Default.FormatPaint,
                    title = "背景自定义",
                    subtitle = "更换背景、调整透明度和模糊度",
                    onClick = onBackgroundClick
                )
            }

            // 一周开始日
            GlassCard {
                SettingItem(
                    icon = Icons.Default.Today,
                    title = "一周开始日",
                    subtitle = if (startOfWeek == 0) "周日" else "周一",
                    onClick = { showStartOfWeekDialog = true }
                )
            }

            // 默认提醒时间
            GlassCard {
                SettingItem(
                    icon = Icons.Default.Notifications,
                    title = "默认提醒时间",
                    subtitle = reminderLabel(defaultReminderMinutes),
                    onClick = { showReminderDialog = true }
                )
            }

            // 关于
            GlassCard {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Text("关于", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("365days v1.0.0", color = TextSecondary, fontSize = 14.sp)
                    Text("开源许可：BSD-3", color = TextSecondary, fontSize = 14.sp)
                }
            }
        }
    }

    if (showStartOfWeekDialog) {
        SelectionDialog(
            title = "一周开始日",
            options = listOf("周日" to 0, "周一" to 1),
            selectedValue = startOfWeek,
            onSelect = { value ->
                viewModel.setStartOfWeek(value)
                showStartOfWeekDialog = false
            },
            onDismiss = { showStartOfWeekDialog = false }
        )
    }

    if (showReminderDialog) {
        SelectionDialog(
            title = "默认提醒时间",
            options = listOf(
                "不提醒" to null,
                "准时" to 0,
                "提前 5 分钟" to 5,
                "提前 15 分钟" to 15,
                "提前 30 分钟" to 30,
                "提前 1 小时" to 60
            ),
            selectedValue = defaultReminderMinutes,
            onSelect = { value ->
                viewModel.setDefaultReminderMinutes(value)
                showReminderDialog = false
            },
            onDismiss = { showReminderDialog = false }
        )
    }
}

@Composable
private fun SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val padding = adaptivePadding()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(padding.cardPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = TextPrimary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun <T> SelectionDialog(
    title: String,
    options: List<Pair<String, T>>,
    selectedValue: T,
    onSelect: (T) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF162536).copy(alpha = 0.88f),
        title = {
            Text(
                text = title,
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                options.forEach { (label, value) ->
                    val selected = value == selectedValue
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (selected) Color.White.copy(alpha = 0.15f)
                                else Color.Transparent
                            )
                            .clickable { onSelect(value) }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = label,
                            color = if (selected) TextPrimary else TextSecondary,
                            fontSize = 16.sp,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                        )
                        if (selected) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color.White, MaterialTheme.shapes.small)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消", color = TextSecondary)
            }
        }
    )
}

private fun reminderLabel(minutes: Int?): String {
    return when (minutes) {
        null -> "不提醒"
        0 -> "准时"
        5 -> "提前 5 分钟"
        15 -> "提前 15 分钟"
        30 -> "提前 30 分钟"
        60 -> "提前 1 小时"
        else -> "提前 $minutes 分钟"
    }
}
