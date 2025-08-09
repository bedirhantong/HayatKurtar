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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatDetailScreen(
    address: String,
    onBack: () -> Unit,
    viewModel: ChatDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(address) {
        if (address.isNotBlank()) viewModel.start(address)
    }

    val messages = viewModel.messages.collectAsState().value
    val isConnected = viewModel.isConnected.collectAsState().value
    val peerTitle = address.ifBlank { "Bilinmeyen" }

    var input by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Auto scroll to bottom when new messages arrive
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
                // Empty state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Henüz mesaj yok",
                        color = TelegramColors.TextSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Telegram-style Input Bar
        ChatInputBar(
            input = input,
            onInputChange = { input = it },
            onSend = {
                if (input.isNotBlank()) {
                    viewModel.send(input)
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
