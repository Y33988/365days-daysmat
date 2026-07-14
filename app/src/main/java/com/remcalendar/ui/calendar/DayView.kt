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
import com.remcalendar.ui.components.GlassCard
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DayView(
    onEventClick: (Event) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val currentDate by viewModel.currentDate.collectAsState()
    val events by viewModel.events.collectAsState()
    val padding = com.remcalendar.util.adaptivePadding()

    val dayEvents = events.filter { event ->
        val eventCalendar = Calendar.getInstance().apply { timeInMillis = event.date }
        isSameDay(currentDate, eventCalendar)
    }.sortedBy { it.date }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding.screenPadding)
    ) {
        // 日期标题
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
                    text = SimpleDateFormat("M月d日", Locale.CHINESE).format(currentDate.time),
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = SimpleDateFormat("EEEE", Locale.CHINESE).format(currentDate.time),
                    color = TextSecondary,
                    fontSize = 14.sp
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

        // 事件列表
        if (dayEvents.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "今天暂无事件",
                    color = TextSecondary,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(padding.cardSpacing)
            ) {
                items(dayEvents) { event ->
                    DayEventCard(
                        event = event,
                        onClick = { onEventClick(event) },
                        padding = padding
                    )
                }
            }
        }
    }
}

@Composable
private fun DayEventCard(
    event: Event,
    onClick: () -> Unit,
    padding: com.remcalendar.util.AdaptivePadding
) {
    GlassCard(cornerRadius = padding.cornerRadius) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(padding.cardPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color(event.color))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    color = TextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                event.description?.let { desc ->
                    if (desc.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = desc,
                            color = TextSecondary,
                            fontSize = 14.sp,
                            maxLines = 2
                        )
                    }
                }
                event.location?.let { loc ->
                    if (loc.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "📍 $loc",
                            color = TextSecondary,
                            fontSize = 13.sp
                        )
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
