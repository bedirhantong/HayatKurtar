package com.appvalence.hayatkurtar.presentation.chatlist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.appvalence.hayatkurtar.domain.model.ChatMessage
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.appvalence.hayatkurtar.presentation.chatdetail.components.TelegramColors
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatListItem(
    message: ChatMessage,
    onClick: (peer: String) -> Unit,
    onDelete: (peer: String) -> Unit = {}
) {
    val menuExpanded = remember { mutableStateOf(false) }
    // Resolve peer device name (WhatsApp-like: show the other party, not last sender)
    val peerName = remember(message.peerAddress) { resolveDeviceName(message.peerAddress) }

    Surface(
        color = TelegramColors.Background,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { onClick(message.peerAddress) },
                    onLongClick = { menuExpanded.value = true }
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            ChatAvatar(
                name = peerName,
                modifier = Modifier.padding(end = 12.dp)
            )

            // Chat content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Peer name
                    Text(
                        text = peerName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = TelegramColors.TextPrimary,
                        modifier = Modifier.weight(1f)
                    )

                    // Time
                    Text(
                        text = formatChatTime(message.timestamp),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 13.sp
                        ),
                        color = TelegramColors.TextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Last message
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = TelegramColors.TextSecondary
                )
            }

            // Dropdown menu
            DropdownMenu(
                expanded = menuExpanded.value,
                onDismissRequest = { menuExpanded.value = false }
            ) {
                DropdownMenuItem(
                    text = { Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.chat_delete)) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color(0xFFE53E3E)
                        )
                    },
                    onClick = {
                        menuExpanded.value = false
                        onDelete(message.peerAddress)
                    }
                )
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun resolveDeviceName(address: String): String {
    if (address.isBlank()) return ""
    val adapter = BluetoothAdapter.getDefaultAdapter() ?: return address
    return runCatching { adapter.getRemoteDevice(address).name }
        .getOrNull()
        ?.takeIf { !it.isNullOrBlank() }
        ?: address
}

@Composable
private fun ChatAvatar(
    name: String,
    modifier: Modifier = Modifier
) {
    val initials = getInitials(name)
    val backgroundColor = getAvatarColor(name)

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            ),
            color = Color.White
        )
    }
}

private fun getInitials(name: String): String {
    return name.split(" ")
        .take(2)
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .joinToString("")
        .ifEmpty { name.take(2).uppercase() }
}

private fun getAvatarColor(name: String): Color {
    val colors = listOf(
        Color(0xFFE17076), // Red
        Color(0xFF7BC862), // Green
        Color(0xFF65AADD), // Blue
        Color(0xFFFFA85C), // Orange
        Color(0xFF9B59B6), // Purple
        Color(0xFF3498DB), // Light Blue
        Color(0xFFE74C3C), // Dark Red
        Color(0xFF2ECC71)  // Dark Green
    )

    val hash = name.hashCode()
    return colors[Math.abs(hash) % colors.size]
}

private fun formatChatTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60000 -> "şimdi" // Less than 1 minute
        diff < 3600000 -> "${diff / 60000}dk" // Less than 1 hour
        diff < 86400000 -> SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp)) // Same day
        diff < 604800000 -> { // Less than 1 week
            val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            dayFormat.format(Date(timestamp))
        }
        else -> SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(timestamp))
    }
}
