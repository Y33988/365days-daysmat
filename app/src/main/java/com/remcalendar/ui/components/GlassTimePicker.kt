package com.remcalendar.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import java.util.Calendar

@Composable
fun GlassTimePicker(
    initialTime: Long?,
    onTimeSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val calendar = Calendar.getInstance().apply {
        initialTime?.let { timeInMillis = it } ?: run {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
        }
    }
    var selectedHour by remember { mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY)) }
    var selectedMinute by remember { mutableStateOf(calendar.get(Calendar.MINUTE)) }

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "选择时间",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.Start)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WheelPicker(
                        label = "时",
                        items = (0..23).toList(),
                        selectedItem = selectedHour,
                        onItemSelected = { selectedHour = it },
                        formatter = { "%02d".format(it) }
                    )
                    WheelPicker(
                        label = "分",
                        items = (0..59).toList(),
                        selectedItem = selectedMinute,
                        onItemSelected = { selectedMinute = it },
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
                                set(Calendar.HOUR_OF_DAY, selectedHour)
                                set(Calendar.MINUTE, selectedMinute)
                                set(Calendar.SECOND, 0)
                                set(Calendar.MILLISECOND, 0)
                            }
                            onTimeSelected(result.timeInMillis)
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
