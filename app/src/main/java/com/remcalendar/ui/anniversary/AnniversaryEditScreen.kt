package com.remcalendar.ui.anniversary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remcalendar.data.model.RepeatInterval
import com.remcalendar.ui.components.GlassCard
import com.remcalendar.ui.components.GlassDatePicker
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import com.remcalendar.util.adaptivePadding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnniversaryEditScreen(
    anniversaryId: Long?,
    onNavigateBack: () -> Unit,
    viewModel: AnniversaryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val editingAnniversary by viewModel.editingAnniversary.collectAsState()
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var date by remember {
        mutableStateOf(
            Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
        )
    }
    var category by remember { mutableStateOf("纪念日") }
    var icon by remember { mutableStateOf("🎉") }
    var color by remember { mutableStateOf(0xFF2196F3) }
    var isTop by remember { mutableStateOf(false) }
    var repeatInterval by remember { mutableStateOf(RepeatInterval.NONE) }
    var isLunar by remember { mutableStateOf(false) }
    var showRepeatDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(anniversaryId) {
        if (anniversaryId != null) {
            viewModel.loadAnniversary(anniversaryId)
        }
    }

    LaunchedEffect(editingAnniversary) {
        editingAnniversary?.let { anniversary ->
            title = anniversary.title
            date = Calendar.getInstance().apply {
                timeInMillis = anniversary.date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            category = anniversary.category
            icon = anniversary.icon
            color = anniversary.color
            isTop = anniversary.isTop
            repeatInterval = anniversary.repeatInterval
            isLunar = anniversary.isLunar
        }
    }

    val padding = adaptivePadding()

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
                title = { Text(if (anniversaryId == null) "添加纪念日" else "编辑纪念日") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            viewModel.saveAnniversary(
                                title = title,
                                date = date,
                                category = category,
                                icon = icon,
                                color = color,
                                isTop = isTop,
                                repeatInterval = repeatInterval,
                                isLunar = isLunar
                            )
                            onNavigateBack()
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "保存")
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
            // 标题
            GlassCard {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Text(
                        text = "纪念日标题",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("输入纪念日标题") },
                        colors = textFieldColors()
                    )
                }
            }

            // 日期
            GlassCard {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Text(
                        text = "日期",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = SimpleDateFormat("yyyy年M月d日", Locale.CHINESE).format(
                            Calendar.getInstance().apply { timeInMillis = date }.time
                        ),
                        color = TextPrimary,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable { showDatePicker = true }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("农历日期", color = TextPrimary)
                        Switch(
                            checked = isLunar,
                            onCheckedChange = { isLunar = it }
                        )
                    }
                }
            }

            // 图标选择
            GlassCard {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Text(
                        text = "图标",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val icons = listOf("🎉", "🎂", "💕", "🎓", "💼", "🏠", "✈️", "🎁")
                        icons.forEach { iconValue ->
                            Box(
                                modifier = Modifier
                                    .size(if (padding.isCompact) 40.dp else 48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (icon == iconValue) Color.White.copy(alpha = 0.2f)
                                        else Color.Transparent
                                    )
                                    .clickable { icon = iconValue },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = iconValue, fontSize = if (padding.isCompact) 20.sp else 24.sp)
                            }
                        }
                    }
                }
            }

            // 颜色选择
            GlassCard {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Text(
                        text = "颜色",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val colors = listOf(
                            0xFF2196F3,
                            0xFF9C27B0,
                            0xFFE91E63,
                            0xFFF44336,
                            0xFFFF9800,
                            0xFF4CAF50,
                            0xFF009688,
                            0xFF00BCD4
                        )
                        colors.forEach { colorValue ->
                            Box(
                                modifier = Modifier
                                    .size(if (padding.isCompact) 36.dp else 40.dp)
                                    .clip(CircleShape)
                                    .background(Color(colorValue))
                                    .clickable { color = colorValue }
                            ) {
                                if (color == colorValue) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 重复间隔
            GlassCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showRepeatDialog = true }
                        .padding(padding.cardPadding)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "重复",
                            color = TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = repeatIntervalLabel(repeatInterval),
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // 置顶
            GlassCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding.cardPadding),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("置顶显示", color = TextPrimary)
                    Switch(
                        checked = isTop,
                        onCheckedChange = { isTop = it }
                    )
                }
            }
        }
    }

    if (showRepeatDialog) {
        AlertDialog(
            onDismissRequest = { showRepeatDialog = false },
            containerColor = Color(0xFF162536).copy(alpha = 0.88f),
            title = {
                Text(
                    text = "重复",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    val intervals = listOf(
                        RepeatInterval.NONE to "不重复",
                        RepeatInterval.YEARLY to "每年",
                        RepeatInterval.MONTHLY to "每月",
                        RepeatInterval.WEEKLY to "每周"
                    )
                    intervals.forEach { (interval, label) ->
                        val selected = repeatInterval == interval
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.medium)
                                .background(
                                    if (selected) Color.White.copy(alpha = 0.15f)
                                    else Color.Transparent
                                )
                                .clickable {
                                    repeatInterval = interval
                                    showRepeatDialog = false
                                }
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
                TextButton(onClick = { showRepeatDialog = false }) {
                    Text("取消", color = TextSecondary)
                }
            }
        )
    }

    if (showDatePicker) {
        GlassDatePicker(
            initialDate = date,
            onDateSelected = { date = it },
            onDismiss = { showDatePicker = false }
        )
    }
}

@Composable
private fun textFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = TextPrimary.copy(alpha = 0.5f),
        unfocusedIndicatorColor = TextSecondary.copy(alpha = 0.3f),
        focusedPlaceholderColor = TextSecondary,
        unfocusedPlaceholderColor = TextSecondary.copy(alpha = 0.5f),
        focusedLabelColor = TextSecondary,
        unfocusedLabelColor = TextSecondary
    )
}

private fun repeatIntervalLabel(interval: RepeatInterval): String {
    return when (interval) {
        RepeatInterval.NONE -> "不重复"
        RepeatInterval.YEARLY -> "每年"
        RepeatInterval.MONTHLY -> "每月"
        RepeatInterval.WEEKLY -> "每周"
    }
}
