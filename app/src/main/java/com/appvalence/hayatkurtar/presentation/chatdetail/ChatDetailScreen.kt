package com.appvalence.hayatkurtar.presentation.chatdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appvalence.hayatkurtar.presentation.chatdetail.components.ChatInputBar
import com.appvalence.hayatkurtar.presentation.chatdetail.components.ChatTopBar
import com.appvalence.hayatkurtar.presentation.chatdetail.components.MessageBubble
import com.appvalence.hayatkurtar.presentation.chatdetail.components.TelegramColors
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatDetailScreen(
    address: String,
    onBack: () -> Unit,
    viewModel: ChatDetailViewModel = hiltViewModel()
) {
    val haptics = LocalHapticFeedback.current
    LaunchedEffect(address) {
        if (address.isNotBlank()) viewModel.start(address)
    }

    val messages = viewModel.messages.collectAsState().value
    val isConnected = viewModel.isConnected.collectAsState().value
    val peerTitle = address.ifBlank { androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.unknown_peer) }

    var input by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TelegramColors.Background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .imePadding()
    ) {
        // Telegram-style Top Bar
        ChatTopBar(
            title = peerTitle,
            isOnline = isConnected,
            onBack = onBack
        )

        // Messages List
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (messages.isNotEmpty()) {
                val sortedMessages = remember(messages) {
                    messages.sortedByDescending { it.timestamp }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    reverseLayout = true,
                    contentPadding = PaddingValues(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(
                        items = sortedMessages,
                        key = { it.id }
                    ) { message ->
                        MessageBubble(
                            message = message,
                            isMine = message.sender == "Me"
                        )
                    }
                }
            } else {
                // Empty state with CTA to discover devices
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.chat_empty),
                        color = TelegramColors.TextSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(onClick = { /* navigate back to chats to start discovery */ onBack() }) {
                        Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.discover_devices_cta))
                    }
                }
            }
        }

        ChatInputBar(
            input = input,
            onInputChange = { input = it },
            onSend = {
                if (input.isNotBlank()) {
                    viewModel.send(input)
                    // Light confirmation haptic on successful send action
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    input = ""
                }
            },
            isConnected = isConnected,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        )
    }
}
