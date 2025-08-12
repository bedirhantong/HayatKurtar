package com.appvalence.hayatkurtar.presentation.chatdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .height(56.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* attachments in future */ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = TelegramColors.Primary)
            }

            TextField(
                value = input,
                onValueChange = onInputChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = { Text(text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.message_placeholder), color = TelegramColors.TextSecondary) },
                textStyle = MaterialTheme.typography.bodyLarge
            )

            val canSend = input.isNotBlank() && isConnected
            val sendDesc = stringResource(id = com.appvalence.hayatkurtar.R.string.send)
            androidx.compose.animation.AnimatedVisibility(
                visible = input.isNotBlank(),
                enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.scaleIn(),
                exit = androidx.compose.animation.fadeOut() + androidx.compose.animation.scaleOut()
            ) {
                IconButton(
                    onClick = onSend,
                    enabled = canSend,
                    modifier = Modifier
                        .padding(end = 8.dp)
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
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}


