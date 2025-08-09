package com.appvalence.hayatkurtar.presentation.chatdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageBubble(
    message: com.appvalence.hayatkurtar.domain.model.ChatMessage,
    isMine: Boolean,
    modifier: Modifier = Modifier
) {
    val bubbleShape = if (isMine) {
        RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 4.dp)
    } else {
        RoundedCornerShape(topStart = 4.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 18.dp)
    }

    val bubbleColor = if (isMine) TelegramColors.OutgoingBubble else TelegramColors.IncomingBubble
    val textColor = if (isMine) Color.White else TelegramColors.TextPrimary

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        if (!isMine) Spacer(modifier = Modifier.width(48.dp))

        Box(
            modifier = Modifier
                .wrapContentWidth()
                .widthIn(max = 280.dp)
                .clip(bubbleShape)
                .background(bubbleColor)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Column {
                Text(text = message.content, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp, lineHeight = 22.sp), color = textColor)
                Row(modifier = Modifier.wrapContentWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = formatTime(message.timestamp),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = if (isMine) Color.White.copy(alpha = 0.8f) else TelegramColors.TextSecondary
                    )
                }
            }
        }

        if (isMine) Spacer(modifier = Modifier.width(10.dp))
    }
}

private fun formatTime(timestamp: Long): String {
    val sdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(timestamp))
}


