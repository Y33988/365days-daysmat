package com.remcalendar.ui.components

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.remcalendar.ui.theme.GlassBorder
import com.remcalendar.ui.theme.GlassBorderHighlight
import com.remcalendar.ui.theme.GlassTintBlue
import com.remcalendar.ui.theme.GlassWhite
import com.remcalendar.ui.theme.GlassWhiteGlow
import com.remcalendar.ui.theme.GlassWhiteStrong
import com.remcalendar.ui.theme.TextPrimary

@Composable
fun GlassButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val shape = RoundedCornerShape(14.dp)

    Box(
        modifier = modifier
            .height(50.dp)
            .clip(shape)
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .blur(6.dp)
                    .background(Color.White.copy(alpha = 0.1f), shape)
            )
        }

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = if (enabled)
                            listOf(GlassBorderHighlight, GlassBorder.copy(alpha = 0.5f), GlassBorderHighlight)
                        else listOf(GlassBorder.copy(alpha = 0.3f), GlassBorder.copy(alpha = 0.1f))
                    ),
                    shape = shape
                ),
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = TextPrimary,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.White.copy(alpha = 0.4f)
            ),
            border = BorderStroke(
                0.5.dp,
                Color.Transparent
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            enabled = enabled,
            contentPadding = PaddingValues(horizontal = 28.dp, vertical = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = if (enabled)
                                listOf(GlassWhiteStrong, GlassWhite, GlassTintBlue, GlassWhite.copy(alpha = 0.12f))
                            else listOf(GlassWhite.copy(alpha = 0.1f), GlassWhite.copy(alpha = 0.05f))
                        )
                    )
                    .drawWithContent {
                        drawContent()
                        drawTopHighlight()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

private fun ContentDrawScope.drawTopHighlight() {
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                GlassWhiteGlow.copy(alpha = 0.4f),
                GlassWhiteGlow.copy(alpha = 0.08f),
                Color.Transparent
            ),
            startY = 0f,
            endY = size.height * 0.5f
        ),
        topLeft = Offset.Zero,
        size = androidx.compose.ui.geometry.Size(size.width, size.height * 0.55f)
    )
}
