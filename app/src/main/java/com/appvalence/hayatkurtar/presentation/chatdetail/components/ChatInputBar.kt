package com.appvalence.hayatkurtar.presentation.chatdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun ChatInputBar(
    input: String,
    onInputChange: (String) -> Unit,
    onSend: () -> Unit,
    isConnected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = TelegramColors.Background,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = onInputChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text(text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.message_placeholder), color = TelegramColors.TextSecondary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = TelegramColors.InputBackground,
                    unfocusedContainerColor = TelegramColors.InputBackground
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
                maxLines = 5,
                textStyle = MaterialTheme.typography.bodyMedium
            )

            val canSend = input.isNotBlank() && isConnected
            val sendDesc = stringResource(id = com.appvalence.hayatkurtar.R.string.send)
            IconButton(
                onClick = onSend,
                enabled = canSend,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if (canSend) TelegramColors.Primary else TelegramColors.Primary.copy(alpha = 0.4f),
                        shape = CircleShape
                    )
                    .semantics { contentDescription = sendDesc }
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = if (canSend) 1f else 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}


