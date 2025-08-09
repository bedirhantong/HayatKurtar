package com.appvalence.hayatkurtar.presentation.chatdetail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChatTopBar(
    title: String,
    isOnline: Boolean,
    onBack: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = TelegramColors.Background,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(48.dp)) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Geri", tint = TelegramColors.Primary)
            }

            PeerAvatar(title = title, modifier = Modifier.padding(end = 12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, color = TelegramColors.TextPrimary, maxLines = 1)
                Text(
                    text = if (isOnline) "çevrimiçi" else "son görülme bilinmiyor",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isOnline) TelegramColors.Primary else TelegramColors.TextSecondary
                )
            }

            // Opsiyonel menü ikonu: Gerekirse tekrar eklenir
            // IconButton(onClick = { /*more*/ }, modifier = Modifier.size(48.dp)) {
            //     Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Daha fazla", tint = TelegramColors.TextSecondary)
            // }
        }
    }
}


