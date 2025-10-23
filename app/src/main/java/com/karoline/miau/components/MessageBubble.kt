package com.karoline.miau.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karoline.miau.model.Message

@Composable
fun MessageBubble(
    message: Message,
    userColor: Color,
    modifier: Modifier = Modifier
) {
    val bgColor = if (message.isUser) userColor else Color.White
    val textColor = if (message.isUser) Color.White else Color.DarkGray

    val horizontalPadding = if (message.isUser) 48.dp else 8.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = 4.dp),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .background(bgColor, shape = RoundedCornerShape(12.dp))
                    .padding(12.dp)
                    .widthIn(max = 280.dp)
                    .wrapContentWidth()
            ) {
                Text(
                    text = message.text,
                    color = textColor,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
