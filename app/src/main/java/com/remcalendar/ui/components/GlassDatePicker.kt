package com.remcalendar.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import java.util.Calendar

@Composable
fun GlassDatePicker(
    initialDate: Long,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val calendar = Calendar.getInstance().apply { timeInMillis = initialDate }
    var selectedYear by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    var selectedMonth by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    var selectedDay by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "选择日期",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WheelPicker(
                        label = "年",
                        items = (2020..2036).toList(),
                        selectedItem = selectedYear,
                        onItemSelected = { selectedYear = it }
                    )
                    WheelPicker(
                        label = "月",
                        items = (1..12).toList(),
                        selectedItem = selectedMonth + 1,
                        onItemSelected = { selectedMonth = it - 1 },
                        formatter = { "%02d".format(it) }
                    )
                    WheelPicker(
                        label = "日",
                        items = (1..getDaysInMonth(selectedYear, selectedMonth)).toList(),
                        selectedItem = selectedDay,
                        onItemSelected = { selectedDay = it },
                        formatter = { "%02d".format(it) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("取消", color = TextSecondary)
                    }
                    TextButton(
                        onClick = {
                            val result = Calendar.getInstance().apply {
                                set(selectedYear, selectedMonth, selectedDay, 0, 0, 0)
                                set(Calendar.MILLISECOND, 0)
                            }
                            onDateSelected(result.timeInMillis)
                            onDismiss()
                        }
                    ) {
                        Text("确定", color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

private fun getDaysInMonth(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}
