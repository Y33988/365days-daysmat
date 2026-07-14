package com.remcalendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.remcalendar.ui.theme.GlassBorderHighlight
import com.remcalendar.ui.theme.GlassWhite
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.ui.theme.TextSecondary

@Composable
fun <T> WheelPicker(
    label: String,
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    formatter: (T) -> String = { it.toString() },
    itemHeight: Dp = 40.dp,
    visibleItems: Int = 5
) {
    val startIndex = items.indexOf(selectedItem).coerceIn(0, items.size - 1)
    val listState = rememberLazyListState(startIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    LaunchedEffect(items) {
        val target = items.indexOf(selectedItem).coerceIn(0, (items.size - 1).coerceAtLeast(0))
        listState.scrollToItem(target)
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val centerItem = listState.firstVisibleItemIndex + visibleItems / 2
            val target = centerItem.coerceIn(0, items.size - 1)
            if (items.getOrNull(target) != selectedItem) {
                onItemSelected(items[target])
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = TextSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .width(70.dp)
                .height(itemHeight * visibleItems),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeight)
                    .clip(RoundedCornerShape(10.dp))
                    .background(GlassWhite.copy(alpha = 0.12f))
                    .border(
                        width = 0.5.dp,
                        color = GlassBorderHighlight.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(10.dp)
                    )
            )

            LazyColumn(
                state = listState,
                flingBehavior = flingBehavior,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                items(items.size) { index ->
                    val item = items[index]
                    val isSelected = item == selectedItem
                    Box(
                        modifier = Modifier
                            .height(itemHeight)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = formatter(item),
                            color = if (isSelected) TextPrimary else TextPrimary.copy(alpha = 0.45f),
                            fontSize = if (isSelected) 17.sp else 15.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                        drawWheelFade(itemHeight.toPx(), visibleItems)
                    }
            )
        }
    }
}

private fun ContentDrawScope.drawWheelFade(itemHeightPx: Float, visibleItems: Int) {
    val fadeHeight = itemHeightPx * 1.8f
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF162536).copy(alpha = 0.78f),
                Color.Transparent
            ),
            startY = 0f,
            endY = fadeHeight
        ),
        topLeft = Offset.Zero,
        size = androidx.compose.ui.geometry.Size(size.width, fadeHeight)
    )
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                Color(0xFF162536).copy(alpha = 0.78f)
            ),
            startY = size.height - fadeHeight,
            endY = size.height
        ),
        topLeft = Offset(0f, size.height - fadeHeight),
        size = androidx.compose.ui.geometry.Size(size.width, fadeHeight)
    )
}
