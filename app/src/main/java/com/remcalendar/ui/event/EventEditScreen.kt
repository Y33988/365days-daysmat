package com.remcalendar.ui.event

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remcalendar.ui.components.GlassCard
import com.remcalendar.ui.components.GlassDatePicker
import com.remcalendar.ui.components.GlassTimePicker
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import com.remcalendar.util.adaptivePadding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventEditScreen(
    eventId: Long?,
    onNavigateBack: () -> Unit,
    viewModel: EventViewModel = hiltViewModel()
) {
    val editingEvent by viewModel.editingEvent.collectAsState()
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
    var time by remember { mutableStateOf<Long?>(null) }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("默认") }
    var color by remember { mutableStateOf(0xFF2196F3) }
    var isAllDay by remember { mutableStateOf(false) }
    var reminderMinutes by remember { mutableStateOf<Int?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(eventId) {
        if (eventId != null) {
            viewModel.loadEvent(eventId)
        }
    }

    LaunchedEffect(editingEvent) {
        editingEvent?.let { event ->
            title = event.title
            date = Calendar.getInstance().apply {
                timeInMillis = event.date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            time = event.time
            location = event.location ?: ""
            description = event.description ?: ""
            category = event.category
            color = event.color
            isAllDay = event.isAllDay
            reminderMinutes = event.reminderMinutes
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
                title = { Text(if (eventId == null) "添加事件" else "编辑事件") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            viewModel.saveEvent(
                                title = title,
                                date = date,
                                time = if (isAllDay) null else time,
                                location = location.ifBlank { null },
                                description = description.ifBlank { null },
                                category = category,
                                color = color,
                                isAllDay = isAllDay,
                                reminderMinutes = reminderMinutes
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
                        text = "事件标题",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("输入事件标题") },
                        colors = TextFieldDefaults.colors(
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
                    )
                }
            }

            // 日期和时间
            GlassCard {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("全天事件", color = TextPrimary)
                        Switch(
                            checked = isAllDay,
                            onCheckedChange = { isAllDay = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

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

                    if (!isAllDay) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "时间",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = time?.let {
                                SimpleDateFormat("HH:mm", Locale.CHINESE).format(
                                    Calendar.getInstance().apply { timeInMillis = it }.time
                                )
                            } ?: "点击设置时间",
                            color = TextPrimary,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable { showTimePicker = true }
                        )
                    }
                }
            }

            // 地点和描述
            GlassCard {
                Column(modifier = Modifier.padding(padding.cardPadding)) {
                    Text(
                        text = "地点",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("输入地点（可选）") },
                        colors = TextFieldDefaults.colors(
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
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "描述",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("输入描述（可选）") },
                        maxLines = 3,
                        colors = TextFieldDefaults.colors(
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
                    )
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
                                    .size(40.dp)
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

            if (showDatePicker) {
                GlassDatePicker(
                    initialDate = date,
                    onDateSelected = { date = it },
                    onDismiss = { showDatePicker = false }
                )
            }

            if (showTimePicker) {
                GlassTimePicker(
                    initialTime = time,
                    onTimeSelected = { time = it },
                    onDismiss = { showTimePicker = false }
                )
            }
        }
    }
}
