package com.remcalendar.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remcalendar.data.model.Event
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun WeekView(
    onEventClick: (Event) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val currentDate by viewModel.currentDate.collectAsState()
    val events by viewModel.events.collectAsState()
    val startOfWeek by viewModel.startOfWeek.collectAsState()

    val weekDays = getWeekDays(currentDate, startOfWeek)
    val weekEvents = events.filter { event ->
        val eventCalendar = Calendar.getInstance()
        eventCalendar.timeInMillis = event.date
        weekDays.any { isSameDay(it, eventCalendar) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 周标题
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

            Text(
                text = SimpleDateFormat("yyyy年 M月", Locale.CHINESE).format(currentDate.time),
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "→",
                color = TextPrimary,
                fontSize = 24.sp,
                modifier = Modifier.clickable { viewModel.nextPeriod() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 周日期显示
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            weekDays.forEach { date ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = SimpleDateFormat("E", Locale.CHINESE).format(date.time),
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSameDay(date, Calendar.getInstance()))
                                    Color.White.copy(alpha = 0.2f)
                                else Color.Transparent
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date.get(Calendar.DAY_OF_MONTH).toString(),
                            color = if (isSameDay(date, Calendar.getInstance()))
                                Color.White
                            else TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = if (isSameDay(date, Calendar.getInstance()))
                                FontWeight.Bold
                            else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 事件列表
        if (weekEvents.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "本周暂无事件",
                    color = TextSecondary,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(weekEvents.sortedBy { it.date }) { event ->
                    WeekEventCard(event = event, onClick = { onEventClick(event) })
                }
            }
        }
    }
}

@Composable
private fun WeekEventCard(
    event: Event,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.1f))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = event.title,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color(event.color))
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = SimpleDateFormat("M月d日 EEEE", Locale.CHINESE).format(
                Calendar.getInstance().apply { timeInMillis = event.date }.time
            ),
            color = TextSecondary,
            fontSize = 14.sp
        )
    }
}

private fun getWeekDays(currentDate: Calendar, startOfWeek: Int): List<Calendar> {
    val calendar = currentDate.clone() as Calendar
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    val offset = if (startOfWeek == 0) {
        dayOfWeek - Calendar.SUNDAY
    } else {
        if (dayOfWeek == Calendar.SUNDAY) 6 else dayOfWeek - Calendar.MONDAY
    }

    calendar.add(Calendar.DAY_OF_MONTH, -offset)

    return List(7) { index ->
        val day = calendar.clone() as Calendar
        day.add(Calendar.DAY_OF_MONTH, index)
        day
    }
}

private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
}
