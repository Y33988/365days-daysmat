package com.remcalendar.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remcalendar.data.model.Anniversary
import com.remcalendar.data.model.Event
import com.remcalendar.ui.components.GlassCard
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary
import com.remcalendar.ui.theme.TextTertiary
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    onEventClick: (Long) -> Unit,
    onAnniversaryClick: (Long) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val events by viewModel.events.collectAsState()
    val anniversaries by viewModel.anniversaries.collectAsState()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color.Transparent),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = TextPrimary,
                    navigationIconContentColor = TextPrimary,
                    actionIconContentColor = TextPrimary
                ),
                title = {
                    TextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        placeholder = { Text("搜索事件或纪念日") },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White.copy(alpha = 0.12f),
                            unfocusedContainerColor = Color.White.copy(alpha = 0.08f),
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedPlaceholderColor = TextSecondary,
                            unfocusedPlaceholderColor = TextTertiary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = TextPrimary
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (searchQuery.isEmpty()) {
                item {
                    Text(
                        text = "输入关键词搜索",
                        color = TextSecondary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                if (events.isNotEmpty()) {
                    item {
                        Text(
                            text = "事件 (${events.size})",
                            color = TextPrimary,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    items(events) { event ->
                        EventSearchResult(event = event, onClick = { onEventClick(event.id) })
                    }
                }

                if (anniversaries.isNotEmpty()) {
                    item {
                        Text(
                            text = "纪念日 (${anniversaries.size})",
                            color = TextPrimary,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    items(anniversaries) { anniversary ->
                        AnniversarySearchResult(
                            anniversary = anniversary,
                            onClick = { onAnniversaryClick(anniversary.id) }
                        )
                    }
                }

                if (events.isEmpty() && anniversaries.isEmpty()) {
                    item {
                        Text(
                            text = "未找到相关结果",
                            color = TextSecondary,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EventSearchResult(
    event: Event,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = event.title,
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = SimpleDateFormat("yyyy年M月d日", Locale.CHINESE).format(Date(event.date)),
                color = TextSecondary,
                style = MaterialTheme.typography.bodySmall
            )
            if (!event.description.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.description,
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
private fun AnniversarySearchResult(
    anniversary: Anniversary,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${anniversary.icon} ${anniversary.title}",
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = SimpleDateFormat("yyyy年M月d日", Locale.CHINESE).format(Date(anniversary.date)),
                color = TextSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
