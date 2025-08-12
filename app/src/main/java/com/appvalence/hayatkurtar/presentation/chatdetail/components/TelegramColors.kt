package com.appvalence.hayatkurtar.presentation.chatdetail.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object TelegramColors {
    val Primary: Color
        @Composable get() = MaterialTheme.colorScheme.primary

    val Background: Color
        @Composable get() = MaterialTheme.colorScheme.background

    val Surface: Color
        @Composable get() = MaterialTheme.colorScheme.surface

    // Chat bubble colors tuned for clearer contrast
    val OutgoingBubble: Color
        @Composable get() = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f)

    val IncomingBubble: Color
        @Composable get() = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)

    val TextPrimary: Color
        @Composable get() = MaterialTheme.colorScheme.onBackground

    val TextSecondary: Color
        @Composable get() = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f)

    val InputBackground: Color
        @Composable get() = MaterialTheme.colorScheme.surfaceVariant

    val Divider: Color
        @Composable get() = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
}


