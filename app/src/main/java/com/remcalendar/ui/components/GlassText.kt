package com.remcalendar.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.remcalendar.ui.theme.TextPrimary

@Composable
fun GlassText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = TextPrimary,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        style = style.copy(
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.22f),
                offset = androidx.compose.ui.geometry.Offset(0f, 1f),
                blurRadius = 2f
            )
        )
    )
}
