package com.remcalendar.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remcalendar.data.model.Event
import com.remcalendar.ui.components.GlassCard
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun YearView(
    onMonthClick: (Calendar) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val currentDate by viewModel.currentDate.collectAsState()
    val events by viewModel.events.collectAsState()
    val startOfWeek by viewModel.startOfWeek.collectAsState()
    val padding = com.remcalendar.util.adaptivePadding()

    val year = currentDate.get(Calendar.YEAR)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding.screenPadding)
    ) {
        // 年份标题
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                color = TextPrimary,
                fontSize = 24.sp,
                modifier = Modifier.clickable { viewModel.previousPeriod() }
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${year}年",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "今天",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { viewModel.goToToday() }
                )
            }

            Text(
                text = "→",
                color = TextPrimary,
                fontSize = 24.sp,
                modifier = Modifier.clickable { viewModel.nextPeriod() }
            )
        }

        Spacer(modifier = Modifier.height(padding.cardSpacing))

        LazyVerticalGrid(
            columns = GridCells.Fixed(if (padding.screenPadding > 16.dp) 3 else 2),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(padding.cardSpacing),
            verticalArrangement = Arrangement.spacedBy(padding.cardSpacing)
        ) {
            items(12) { monthIndex ->
                val monthCalendar = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, monthIndex)
                    set(Calendar.DAY_OF_MONTH, 1)
                }
                MonthMiniCard(
                    monthCalendar = monthCalendar,
                    events = events,
                    startOfWeek = startOfWeek,
                    isCurrentMonth = monthIndex == Calendar.getInstance().get(Calendar.MONTH)
                            && year == Calendar.getInstance().get(Calendar.YEAR),
                    onClick = { onMonthClick(monthCalendar) }
                )
            }
        }
    }
}

@Composable
private fun MonthMiniCard(
    monthCalendar: Calendar,
    events: List<Event>,
    startOfWeek: Int,
    isCurrentMonth: Boolean,
    onClick: () -> Unit
) {
    GlassCard(cornerRadius = 16.dp) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(10.dp)
        ) {
            Text(
                text = SimpleDateFormat("M月", Locale.CHINESE).format(monthCalendar.time),
                color = if (isCurrentMonth) Color.White else TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            // 星期标题
            Row(modifier = Modifier.fillMaxWidth()) {
                val weekDays = if (startOfWeek == 0) {
                    listOf("日", "一", "二", "三", "四", "五", "六")
                } else {
                    listOf("一", "二", "三", "四", "五", "六", "日")
                }
                weekDays.forEach { day ->
                    Text(
                        text = day,
                        color = TextSecondary,
                        fontSize = 8.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 日期网格
            val daysInMonth = monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
            val offset = if (startOfWeek == 0) {
                firstDayOfMonth
            } else {
                if (firstDayOfMonth == 0) 6 else firstDayOfMonth - 1
            }

            val totalCells = ((offset + daysInMonth + 6) / 7) * 7

            Column(modifier = Modifier.fillMaxWidth()) {
                for (row in 0 until totalCells / 7) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        for (col in 0..6) {
                            val index = row * 7 + col
                            val day = index - offset + 1
                            if (index < offset || day > daysInMonth) {
                                Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                            } else {
                                val dateCalendar = monthCalendar.clone() as Calendar
                                dateCalendar.set(Calendar.DAY_OF_MONTH, day)
                                val isToday = isSameDay(dateCalendar, Calendar.getInstance())
                                val hasEvent = events.any { event ->
                                    val eventCalendar = Calendar.getInstance().apply {
                                        timeInMillis = event.date
                                    }
                                    isSameDay(dateCalendar, eventCalendar)
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .clip(CircleShape)
                                        .background(
                                            if (isToday) Color.White.copy(alpha = 0.25f)
                                            else Color.Transparent
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day.toString(),
                                        color = if (isToday) Color.White else TextPrimary,
                                        fontSize = 10.sp,
                                        fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
                                    )
                                    if (hasEvent && !isToday) {
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.BottomCenter)
                                                .padding(bottom = 2.dp)
                                                .size(3.dp)
                                                .clip(CircleShape)
                                                .background(Color.White.copy(alpha = 0.6f))
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
}
