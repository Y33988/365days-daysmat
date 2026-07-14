package com.remcalendar.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remcalendar.data.model.Event
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import com.remcalendar.util.adaptivePadding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun MonthView(
    onDateClick: (Calendar) -> Unit,
    onEventClick: (Event) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val currentDate by viewModel.currentDate.collectAsState()
    val events by viewModel.events.collectAsState()
    val startOfWeek by viewModel.startOfWeek.collectAsState()

    val padding = com.remcalendar.util.adaptivePadding()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = padding.screenPadding)
    ) {
        // 月份标题
        MonthHeader(
            currentDate = currentDate,
            onPreviousClick = { viewModel.previousPeriod() },
            onNextClick = { viewModel.nextPeriod() },
            onTodayClick = { viewModel.goToToday() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 星期标题
        WeekDayHeader(startOfWeek = startOfWeek)

        Spacer(modifier = Modifier.height(8.dp))

        // 日期网格
        MonthGrid(
            currentDate = currentDate,
            events = events,
            startOfWeek = startOfWeek,
            onDateClick = onDateClick,
            onEventClick = onEventClick
        )
    }
}

@Composable
private fun MonthHeader(
    currentDate: Calendar,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onTodayClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "←",
            color = TextPrimary,
            fontSize = 24.sp,
            modifier = Modifier.clickable { onPreviousClick() }
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = SimpleDateFormat("yyyy年 M月", Locale.CHINESE).format(currentDate.time),
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "今天",
                color = TextSecondary,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onTodayClick() }
            )
        }

        Text(
            text = "→",
            color = TextPrimary,
            fontSize = 24.sp,
            modifier = Modifier.clickable { onNextClick() }
        )
    }
}

@Composable
private fun WeekDayHeader(startOfWeek: Int) {
    val weekDays = if (startOfWeek == 0) {
        listOf("日", "一", "二", "三", "四", "五", "六")
    } else {
        listOf("一", "二", "三", "四", "五", "六", "日")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        weekDays.forEach { day ->
            Text(
                text = day,
                color = TextSecondary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MonthGrid(
    currentDate: Calendar,
    events: List<Event>,
    startOfWeek: Int,
    onDateClick: (Calendar) -> Unit,
    onEventClick: (Event) -> Unit
) {
    val calendar = currentDate.clone() as Calendar
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    val offset = if (startOfWeek == 0) {
        firstDayOfMonth
    } else {
        if (firstDayOfMonth == 0) 6 else firstDayOfMonth - 1
    }

    val dates = List(offset + daysInMonth) { index ->
        if (index < offset) null
        else {
            val day = index - offset + 1
            val dateCalendar = calendar.clone() as Calendar
            dateCalendar.set(Calendar.DAY_OF_MONTH, day)
            dateCalendar
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(dates) { date ->
            if (date == null) {
                Box(modifier = Modifier.aspectRatio(1f))
            } else {
                DateCell(
                    date = date,
                    events = events.filter { event ->
                        val eventCalendar = Calendar.getInstance()
                        eventCalendar.timeInMillis = event.date
                        isSameDay(date, eventCalendar)
                    },
                    isToday = isSameDay(date, Calendar.getInstance()),
                    onClick = { onDateClick(date) },
                    onEventClick = onEventClick
                )
            }
        }
    }
}

@Composable
private fun DateCell(
    date: Calendar,
    events: List<Event>,
    isToday: Boolean,
    onClick: () -> Unit,
    onEventClick: (Event) -> Unit
) {
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(CircleShape)
            .background(
                if (isToday) Color.White.copy(alpha = 0.2f)
                else Color.Transparent
            )
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = date.get(Calendar.DAY_OF_MONTH).toString(),
            color = if (isToday) Color.White else TextPrimary,
            fontSize = 16.sp,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
        )

        if (events.isNotEmpty()) {
            Spacer(modifier = Modifier.height(2.dp))
            events.take(3).forEach { event ->
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(Color(event.color))
                )
                Spacer(modifier = Modifier.height(1.dp))
            }
        }
    }
}

private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
}
