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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon

@Composable
fun MessageBubble(
    message: com.appvalence.hayatkurtar.domain.model.ChatMessage,
    isMine: Boolean,
    peerTitle: String? = null,
    delivered: Boolean = false,
    read: Boolean = false,
    modifier: Modifier = Modifier
) {
    val bubbleShape = if (isMine) {
        // Outgoing: slightly more rounded and aligned right
        RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 6.dp)
    } else {
        // Incoming: keep a subtle tail on the left
        RoundedCornerShape(topStart = 6.dp, topEnd = 18.dp, bottomStart = 6.dp, bottomEnd = 18.dp)
    }

    val bubbleColor = if (isMine) TelegramColors.OutgoingBubble else TelegramColors.IncomingBubble
    val textColor = if (isMine) MaterialTheme.colorScheme.onPrimary else TelegramColors.TextPrimary

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
    ) {
//        // Incoming avatar and optional sender name above bubble
//        Row(
//            horizontalArrangement = Arrangement.Start,
//            modifier = Modifier.padding(horizontal = if (isMine) 0.dp else 8.dp)
//        ) {
//            if ( !peerTitle.isNullOrBlank()) {
//                Text(
//                    text = peerTitle,
//                    style = MaterialTheme.typography.labelSmall,
//                    color = TelegramColors.Primary,
//                    modifier = Modifier.padding(start = 44.dp, bottom = 2.dp)
//                )
//            }
//
//            PeerAvatar(title = peerTitle ?: "?", modifier = Modifier.padding(end = 8.dp), size = 28)
//        }


        Box(
            modifier = Modifier
                .wrapContentWidth()
                .widthIn(min = 60.dp, max = 300.dp)
                .clip(bubbleShape)
                .background(bubbleColor)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Column {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp, lineHeight = 22.sp),
                    color = textColor
                )
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formatTime(message.timestamp),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = if (isMine) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f) else TelegramColors.TextSecondary
                    )
                    if (isMine) {
                        Spacer(Modifier.width(6.dp))
                        Icon(
                            imageVector = if (read) Icons.Filled.RemoveRedEye else if (delivered) Icons.Filled.DoneAll else Icons.Filled.Done,
                            contentDescription = null,
                            tint = if (isMine) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f) else TelegramColors.TextSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
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


