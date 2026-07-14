package com.remcalendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.remcalendar.ui.theme.GlassBackground
import com.remcalendar.ui.theme.GlassBorder
import com.remcalendar.ui.theme.GlassBorderHighlight
import com.remcalendar.ui.theme.GlassTintBlue
import com.remcalendar.ui.theme.GlassTintPurple
import com.remcalendar.ui.theme.GlassWhite

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .clip(shape)
            .shadow(
                elevation = 12.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.25f),
                spotColor = Color.Black.copy(alpha = 0.32f)
            )
            .border(
                width = 0.5.dp,
                color = GlassBorder.copy(alpha = 0.35f),
                shape = shape
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 0.5.dp,
                    color = GlassBorderHighlight.copy(alpha = 0.18f),
                    shape = shape
                ),
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    GlassWhite.copy(alpha = 0.14f),
                                    GlassBackground.copy(alpha = 0.45f),
                                    GlassTintBlue.copy(alpha = 0.08f),
                                    GlassTintPurple.copy(alpha = 0.06f),
                                    Color.Black.copy(alpha = 0.08f)
                                )
                            )
                        )
                        .drawWithContent {
                            drawContent()
                            drawSoftTopSheen()
                            drawInnerDepth()
                        }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        content()
                    }
                }
            }
        )
    }
}

private fun ContentDrawScope.drawSoftTopSheen() {
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.18f),
                Color.White.copy(alpha = 0.05f),
                Color.Transparent
            ),
            startY = 0f,
            endY = size.height * 0.35f
        ),
        topLeft = Offset.Zero,
        size = androidx.compose.ui.geometry.Size(size.width, size.height * 0.4f)
    )
}

private fun ContentDrawScope.drawInnerDepth() {
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                Color.Black.copy(alpha = 0.06f)
            ),
            startY = size.height * 0.6f,
            endY = size.height
        ),
        topLeft = Offset(0f, size.height * 0.6f),
        size = androidx.compose.ui.geometry.Size(size.width, size.height * 0.4f)
    )
}
