package com.remcalendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.remcalendar.ui.theme.GlassBackground
import com.remcalendar.ui.theme.GlassBorder
import com.remcalendar.ui.theme.GlassBorderHighlight
import com.remcalendar.ui.theme.GlassTintBlue
import com.remcalendar.ui.theme.GlassWhite
import com.remcalendar.ui.theme.TextPrimary

@Composable
fun GlassFloatingActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    cornerRadius: Dp = 18.dp
) {
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .shadow(
                elevation = 14.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.28f),
                spotColor = Color.Black.copy(alpha = 0.38f)
            )
            .border(
                width = 0.5.dp,
                color = GlassBorder.copy(alpha = 0.4f),
                shape = shape
            )
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            shape = shape,
            containerColor = Color.Transparent,
            contentColor = TextPrimary,
            elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                GlassWhite.copy(alpha = 0.18f),
                                GlassBackground.copy(alpha = 0.5f),
                                GlassTintBlue.copy(alpha = 0.1f),
                                Color.Black.copy(alpha = 0.08f)
                            )
                        )
                    )
                    .drawWithContent {
                        drawContent()
                        drawSoftSheen()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = TextPrimary
                )
            }
        }
    }
}

private fun ContentDrawScope.drawSoftSheen() {
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.16f),
                Color.White.copy(alpha = 0.04f),
                Color.Transparent
            ),
            startY = 0f,
            endY = size.height * 0.45f
        ),
        topLeft = Offset.Zero,
        size = androidx.compose.ui.geometry.Size(size.width, size.height * 0.5f)
    )
}
