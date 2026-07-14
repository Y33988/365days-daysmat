package com.remcalendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.remcalendar.ui.theme.GlassBackground
import com.remcalendar.ui.theme.GlassBorder
import com.remcalendar.ui.theme.GlassBorderHighlight
import com.remcalendar.ui.theme.GlassTintBlue
import com.remcalendar.ui.theme.GlassWhite
import com.remcalendar.ui.theme.TextPrimary
import com.remcalendar.util.adaptivePadding

@Composable
fun GlassBottomNav(
    items: List<Pair<String, ImageVector>>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val padding = adaptivePadding()
    val shape = RoundedCornerShape(
        topStart = padding.cornerRadius,
        topEnd = padding.cornerRadius,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = padding.screenPadding)
            .padding(bottom = padding.screenPadding)
            .clip(shape)
            .shadow(
                elevation = 16.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.28f),
                spotColor = Color.Black.copy(alpha = 0.38f)
            )
            .border(
                width = 0.5.dp,
                color = GlassBorder.copy(alpha = 0.32f),
                shape = shape
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            GlassWhite.copy(alpha = 0.14f),
                            GlassBackground.copy(alpha = 0.45f),
                            GlassTintBlue.copy(alpha = 0.08f),
                            Color.Black.copy(alpha = 0.08f)
                        )
                    )
                )
                .drawWithContent {
                    drawContent()
                    drawDockSheen()
                }
                .padding(vertical = if (padding.isCompact) 8.dp else 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, (label, icon) ->
                    val selected = index == selectedIndex
                    BottomNavItem(
                        icon = icon,
                        label = label,
                        selected = selected,
                        onClick = { onItemSelected(index) },
                        compact = padding.isCompact
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    compact: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = if (compact) 8.dp else 12.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(if (compact) 32.dp else 36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (selected) GlassWhite.copy(alpha = 0.18f)
                    else Color.Transparent
                )
                .border(
                    width = if (selected) 0.5.dp else 0.dp,
                    color = if (selected) GlassBorderHighlight.copy(alpha = 0.3f)
                    else Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) TextPrimary else TextPrimary.copy(alpha = 0.62f),
                modifier = Modifier.size(if (compact) 20.dp else 22.dp)
            )
        }

        if (!compact) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                color = if (selected) TextPrimary else TextPrimary.copy(alpha = 0.62f),
                fontSize = 11.sp,
                fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
            )
        }
    }
}

private fun ContentDrawScope.drawDockSheen() {
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.14f),
                Color.White.copy(alpha = 0.04f),
                Color.Transparent
            ),
            startY = 0f,
            endY = size.height * 0.4f
        ),
        topLeft = Offset.Zero,
        size = androidx.compose.ui.geometry.Size(size.width, size.height * 0.45f)
    )
}
