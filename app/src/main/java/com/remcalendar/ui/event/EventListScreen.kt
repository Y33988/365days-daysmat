package com.remcalendar.ui.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import com.remcalendar.ui.components.GlassFloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remcalendar.data.model.Event
import com.remcalendar.ui.calendar.CalendarViewModel
import com.remcalendar.ui.components.GlassCard
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import com.remcalendar.util.adaptivePadding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    onAddEvent: () -> Unit,
    onEditEvent: (Long) -> Unit,
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val events by calendarViewModel.events.collectAsState()
    val padding = adaptivePadding()

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        floatingActionButton = {
            GlassFloatingActionButton(
                onClick = onAddEvent,
                icon = Icons.Default.Add,
                contentDescription = "添加事件",
                size = if (padding.isCompact) 52.dp else 60.dp,
                cornerRadius = if (padding.isCompact) 16.dp else 18.dp
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = padding.screenPadding),
            verticalArrangement = Arrangement.spacedBy(padding.cardSpacing)
        ) {
            if (events.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "暂无事件\n点击右下角按钮添加",
                            color = TextSecondary,
                            fontSize = 16.sp
                        )
                    }
                }
            } else {
                items(events) { event ->
                    EventCard(
                        event = event,
                        onClick = { onEditEvent(event.id) },
                        onDelete = { calendarViewModel.deleteEvent(event) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val padding = adaptivePadding()

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(padding.cardPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = event.title,
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = SimpleDateFormat("yyyy年M月d日 EEEE", Locale.CHINESE).format(
                            Calendar.getInstance().apply { timeInMillis = event.date }.time
                        ),
                        color = TextSecondary,
                        fontSize = 14.sp
                    )

                    if (event.time != null && !event.isAllDay) {
                        Text(
                            text = SimpleDateFormat("HH:mm").format(
                                Calendar.getInstance().apply { timeInMillis = event.time!! }.time
                            ),
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }

                    if (!event.location.isNullOrEmpty()) {
                        Text(
                            text = "📍 ${event.location}",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color(event.color))
                )
            }

            if (!event.description.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = event.description,
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "删除",
                        tint = Color.Red.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            containerColor = Color(0xFF162536).copy(alpha = 0.88f),
            title = { Text("删除事件") },
            text = { Text("确定要删除这个事件吗？") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) {
                    Text("删除", color = Color.Red.copy(alpha = 0.9f))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("取消", color = TextSecondary)
                }
            }
        )
    }
}
