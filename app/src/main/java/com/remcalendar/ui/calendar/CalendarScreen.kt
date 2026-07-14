package com.remcalendar.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import com.remcalendar.ui.components.GlassFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remcalendar.data.model.Event
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import com.remcalendar.util.adaptivePadding
import com.remcalendar.util.rememberWindowSize
import java.util.Calendar

@Composable
fun CalendarScreen(
    onAddEventClick: () -> Unit,
    onEventClick: (Event) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val viewMode by viewModel.viewMode.collectAsState()
    val padding = adaptivePadding()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding.screenPadding)
        ) {
            // 视图切换器
            ViewModeSelector(
                currentMode = viewMode,
                onModeSelected = { viewModel.setViewMode(it) },
                padding = padding
            )

            Spacer(modifier = Modifier.height(padding.cardSpacing))

            // 根据视图模式显示不同内容
            when (viewMode) {
                CalendarViewMode.YEAR -> YearView(
                    onMonthClick = { monthCalendar ->
                        viewModel.setSelectedDate(monthCalendar)
                        viewModel.setViewMode(CalendarViewMode.MONTH)
                    }
                )
                CalendarViewMode.MONTH -> MonthView(
                    onDateClick = { date ->
                        viewModel.setSelectedDate(date)
                        viewModel.setViewMode(CalendarViewMode.DAY)
                    },
                    onEventClick = onEventClick
                )
                CalendarViewMode.WEEK -> WeekView(
                    onEventClick = onEventClick
                )
                CalendarViewMode.DAY -> DayView(
                    onEventClick = onEventClick
                )
            }
        }

        // 添加事件按钮
        GlassFloatingActionButton(
            onClick = onAddEventClick,
            icon = Icons.Default.Add,
            contentDescription = "添加事件",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(padding.screenPadding),
            size = if (padding.isCompact) 52.dp else 60.dp,
            cornerRadius = if (padding.isCompact) 16.dp else 18.dp
        )
    }
}

@Composable
private fun ViewModeSelector(
    currentMode: CalendarViewMode,
    onModeSelected: (CalendarViewMode) -> Unit,
    padding: com.remcalendar.util.AdaptivePadding
) {
    val modes = listOf(
        CalendarViewMode.YEAR to "年",
        CalendarViewMode.MONTH to "月",
        CalendarViewMode.WEEK to "周",
        CalendarViewMode.DAY to "日"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(padding.cornerRadius))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.14f),
                        Color.White.copy(alpha = 0.08f),
                        Color.White.copy(alpha = 0.04f)
                    )
                )
            )
            .border(
                width = 0.5.dp,
                color = Color.White.copy(alpha = 0.22f),
                shape = RoundedCornerShape(padding.cornerRadius)
            )
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            modes.forEach { (mode, label) ->
                val selected = currentMode == mode
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(padding.cornerRadius - 4.dp))
                        .background(
                            if (selected) Color.White.copy(alpha = 0.2f)
                            else Color.Transparent
                        )
                        .border(
                            width = if (selected) 1.dp else 0.dp,
                            color = if (selected) Color.White.copy(alpha = 0.5f)
                            else Color.Transparent,
                            shape = RoundedCornerShape(padding.cornerRadius - 4.dp)
                        )
                        .clickable { onModeSelected(mode) },
                    contentAlignment = Alignment.Center
                ) {
                    val windowSize = rememberWindowSize()
                        Text(
                            text = label,
                            color = if (selected) TextPrimary else TextSecondary,
                            fontSize = if (windowSize.isCompact) 14.sp else 15.sp,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                        )
                }
            }
        }
    }
}
