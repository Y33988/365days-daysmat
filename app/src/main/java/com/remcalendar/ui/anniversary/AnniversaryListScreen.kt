package com.remcalendar.ui.anniversary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
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
import com.remcalendar.data.model.Anniversary
import com.remcalendar.ui.components.GlassCard
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import com.remcalendar.util.adaptivePadding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnniversaryListScreen(
    onAddAnniversary: () -> Unit,
    onEditAnniversary: (Long) -> Unit,
    anniversaryViewModel: AnniversaryViewModel = hiltViewModel()
) {
    val anniversaries by anniversaryViewModel.anniversaries.collectAsState()
    val padding = adaptivePadding()

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        floatingActionButton = {
            GlassFloatingActionButton(
                onClick = onAddAnniversary,
                icon = Icons.Default.Add,
                contentDescription = "添加纪念日",
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
            if (anniversaries.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "暂无纪念日\n点击右下角按钮添加",
                            color = TextSecondary,
                            fontSize = 16.sp
                        )
                    }
                }
            } else {
                items(anniversaries) { anniversary ->
                    AnniversaryCard(
                        anniversary = anniversary,
                        onClick = { onEditAnniversary(anniversary.id) },
                        onDelete = { anniversaryViewModel.deleteAnniversary(anniversary) },
                        onToggleTop = { anniversaryViewModel.toggleTopStatus(anniversary) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AnniversaryCard(
    anniversary: Anniversary,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onToggleTop: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val daysDiff = calculateDaysDifference(anniversary.date)

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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = anniversary.icon,
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = anniversary.title,
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = SimpleDateFormat("yyyy年M月d日", Locale.CHINESE).format(
                            Calendar.getInstance().apply { timeInMillis = anniversary.date }.time
                        ),
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = if (daysDiff >= 0) "$daysDiff 天后" else "${abs(daysDiff)} 天前",
                        color = Color(anniversary.color),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = onToggleTop) {
                        Icon(
                            if (anniversary.isTop) Icons.Default.Star else Icons.Outlined.StarOutline,
                            contentDescription = "置顶",
                            tint = if (anniversary.isTop) Color.Yellow else TextSecondary
                        )
                    }
                }
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
            title = { Text("删除纪念日") },
            text = { Text("确定要删除这个纪念日吗？") },
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

private fun calculateDaysDifference(targetDate: Long): Long {
    val today = Calendar.getInstance()
    today.set(Calendar.HOUR_OF_DAY, 0)
    today.set(Calendar.MINUTE, 0)
    today.set(Calendar.SECOND, 0)
    today.set(Calendar.MILLISECOND, 0)

    val target = Calendar.getInstance()
    target.timeInMillis = targetDate
    target.set(Calendar.HOUR_OF_DAY, 0)
    target.set(Calendar.MINUTE, 0)
    target.set(Calendar.SECOND, 0)
    target.set(Calendar.MILLISECOND, 0)

    val diff = target.timeInMillis - today.timeInMillis
    return diff / (1000 * 60 * 60 * 24)
}
