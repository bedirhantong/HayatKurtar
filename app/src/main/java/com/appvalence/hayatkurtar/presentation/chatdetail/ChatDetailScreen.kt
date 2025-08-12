package com.appvalence.hayatkurtar.presentation.chatdetail

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.cancel

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
    val resolvedTitle = viewModel.peerTitle.collectAsState().value
    val peerTitle = (resolvedTitle.ifBlank { address }).ifBlank { androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.unknown_peer) }
    val pairingState = viewModel.pairingState.collectAsState().value

    var input by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val sentMessageText = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.message_sent)
    val showScrollButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 2 || listState.firstVisibleItemScrollOffset > 200
        }
    }

    // Daha kararlı ilk yükleme: içerik ölçümü sonrası liste başına kaydır
    val hasAutoScrolled = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(messages) {
        if (messages.isNotEmpty() && !hasAutoScrolled.value) {
            snapshotFlow { listState.layoutInfo.totalItemsCount }.collect { _ ->
                listState.scrollToItem(0)
                hasAutoScrolled.value = true
                this.cancel()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TelegramColors.Background)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        // Telegram-style Top Bar
        ChatTopBar(
            title = peerTitle,
            isOnline = isConnected,
            onBack = onBack
        )

        // Pairing banner feedback
        AnimatedVisibility(visible = pairingState != ChatDetailViewModel.PairingState.Idle && !isConnected) {
            val (bg, fg, text) = when (pairingState) {
                ChatDetailViewModel.PairingState.Requesting -> Triple(
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.pairing_requesting)
                )
                ChatDetailViewModel.PairingState.Success -> Triple(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    MaterialTheme.colorScheme.onTertiaryContainer,
                    androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.pairing_success)
                )
                ChatDetailViewModel.PairingState.Failed -> Triple(
                    MaterialTheme.colorScheme.errorContainer,
                    MaterialTheme.colorScheme.onErrorContainer,
                    androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.pairing_failed)
                )
                else -> Triple(Color.Unspecified, Color.Unspecified, "")
            }
            if (text.isNotBlank()) {
                Surface(color = bg) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (pairingState == ChatDetailViewModel.PairingState.Requesting) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(end = 8.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                            Text(text = text, color = fg, style = MaterialTheme.typography.bodyMedium)
                        }
                        Row {
                            if (pairingState == ChatDetailViewModel.PairingState.Failed) {
                                TextButton(onClick = { viewModel.retryPair(address) }) {
                                    Text(text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.retry), color = fg)
                                }
                            }
                            if (pairingState == ChatDetailViewModel.PairingState.Success) {
                                TextButton(onClick = { viewModel.reconnect() }) {
                                    Text(text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.connect), color = fg)
                                }
                            }
                        }
                    }
                }
            }
        }

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
                            isMine = message.sender == "Me",
                            peerTitle = resolvedTitle,
                            delivered = message.delivered,
                            read = message.read
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

            // Scroll-to-bottom FAB similar to sample
            if (showScrollButton) {
                FloatingActionButton(
                    onClick = { scope.launch { listState.animateScrollToItem(0) } },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 8.dp, end = 16.dp)
                        .size(46.dp),
                    containerColor = TelegramColors.Primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null)
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
                    scope.launch { snackbarHostState.showSnackbar(sentMessageText, withDismissAction = true, duration = SnackbarDuration.Short) }
                    input = ""
                    // Scroll to bottom smoothly
                    scope.launch { listState.animateScrollToItem(0) }
                }
            },
            isConnected = isConnected,
            modifier = Modifier
        )
    }

    // Snackbar host layered above
    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 80.dp))
    }
}
