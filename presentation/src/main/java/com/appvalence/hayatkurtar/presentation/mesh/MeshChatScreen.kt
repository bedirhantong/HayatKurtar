package com.appvalence.hayatkurtar.presentation.mesh

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appvalence.hayatkurtar.core.protocol.Priority
import com.appvalence.hayatkurtar.domain.mesh.*
import com.appvalence.hayatkurtar.domain.transport.Peer
import com.appvalence.hayatkurtar.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalComposeUiApi::class)

@Composable
fun MeshChatScreen(
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MeshChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    
    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Network status bar
        MeshNetworkStatusBar(
            meshStats = uiState.meshStats,
            transportStats = uiState.transportStats,
            isServiceRunning = uiState.isServiceRunning,
            onStatusClick = onNavigateToSettings
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Emergency actions
        if (uiState.meshStats.connectedPeers > 0) {
            EmergencyActionsCard(
                onSendEmergency = viewModel::sendEmergencyMessage,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Messages list
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (uiState.messages.isEmpty()) {
                item {
                    EmptyStateCard(
                        isConnected = uiState.meshStats.connectedPeers > 0,
                        isServiceRunning = uiState.isServiceRunning
                    )
                }
            } else {
                items(uiState.messages) { message ->
                    MessageCard(
                        message = message,
                        isFromSelf = message.isFromCurrentDevice
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Message input
        MessageInput(
            message = uiState.currentMessage,
            onMessageChange = viewModel::updateCurrentMessage,
            onSendMessage = {
                viewModel.sendMessage()
                keyboardController?.hide()
            },
            isEnabled = uiState.meshStats.connectedPeers > 0 || uiState.isServiceRunning,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun EmergencyActionsCard(
    onSendEmergency: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showEmergencyDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Emergency,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = "Emergency Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.weight(1f)
            )
            
            Button(
                onClick = { showEmergencyDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Campaign,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("SOS")
            }
        }
    }
    
    if (showEmergencyDialog) {
        EmergencyMessageDialog(
            onDismiss = { showEmergencyDialog = false },
            onSendEmergency = { message ->
                onSendEmergency(message)
                showEmergencyDialog = false
            }
        )
    }
}

@Composable
private fun EmergencyMessageDialog(
    onDismiss: () -> Unit,
    onSendEmergency: (String) -> Unit
) {
    var emergencyMessage by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Emergency,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Emergency Message")
            }
        },
        text = {
            Column {
                Text(
                    text = "This will broadcast an emergency message to all connected devices in the mesh network.",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = emergencyMessage,
                    onValueChange = { emergencyMessage = it },
                    label = { Text("Emergency details") },
                    placeholder = { Text("Help needed at...") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (emergencyMessage.isNotBlank()) {
                        onSendEmergency(emergencyMessage)
                    }
                },
                enabled = emergencyMessage.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Send SOS")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun MessageCard(
    message: UiMessage,
    isFromSelf: Boolean,
    modifier: Modifier = Modifier
) {
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromSelf) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isFromSelf) 16.dp else 4.dp,
                bottomEnd = if (isFromSelf) 4.dp else 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = when {
                    message.isEmergency -> MaterialTheme.colorScheme.errorContainer
                    isFromSelf -> MaterialTheme.colorScheme.primaryContainer
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                if (!isFromSelf) {
                    Text(
                        text = message.senderName,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                
                if (message.isEmergency) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Emergency,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "EMERGENCY",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
                
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = timeFormat.format(Date(message.timestamp)),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    if (message.hopCount > 0) {
                        Text(
                            text = "• ${message.hopCount} hop${if (message.hopCount > 1) "s" else ""}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    if (isFromSelf && message.isDelivered) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Delivered",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyStateCard(
    isConnected: Boolean,
    isServiceRunning: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = when {
                    !isServiceRunning -> Icons.Default.PowerSettingsNew
                    isConnected -> Icons.Default.Forum
                    else -> Icons.Default.BluetoothSearching
                },
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = when {
                    !isServiceRunning -> "Mesh Network Offline"
                    isConnected -> "Start Messaging"
                    else -> "Searching for Peers"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = when {
                    !isServiceRunning -> "The mesh network service is not running. Messages cannot be sent or received."
                    isConnected -> "You're connected to the mesh network. Send a message to start communicating."
                    else -> "Looking for nearby devices to connect to the mesh network..."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MessageInput(
    message: String,
    onMessageChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = onMessageChange,
                placeholder = { 
                    Text(
                        if (isEnabled) "Type a message..." else "Connect to mesh network to send messages"
                    ) 
                },
                enabled = isEnabled,
                modifier = Modifier.weight(1f),
                maxLines = 4,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = { if (message.isNotBlank()) onSendMessage() }
                )
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FilledIconButton(
                onClick = onSendMessage,
                enabled = isEnabled && message.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send message"
                )
            }
        }
    }
}

// UI State and ViewModel
@HiltViewModel
class MeshChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val sendEmergencyMessageUseCase: SendEmergencyMessageUseCase,
    private val getMeshStatsUseCase: GetMeshStatsUseCase,
    private val meshRouter: MeshRouter
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MeshChatUiState())
    val uiState: StateFlow<MeshChatUiState> = _uiState.asStateFlow()
    
    init {
        // Collect mesh events
        viewModelScope.launch {
            meshRouter.events.collect { event ->
                handleMeshEvent(event)
            }
        }
        
        // Update stats periodically
        viewModelScope.launch {
            while (true) {
                updateStats()
                kotlinx.coroutines.delay(2000) // Every 2 seconds
            }
        }
    }
    
    fun updateCurrentMessage(message: String) {
        _uiState.value = _uiState.value.copy(currentMessage = message)
    }
    
    fun sendMessage() {
        val message = _uiState.value.currentMessage.trim()
        if (message.isBlank()) return
        
        viewModelScope.launch {
            sendMessageUseCase(message, Priority.NORMAL)
            _uiState.value = _uiState.value.copy(currentMessage = "")
        }
    }
    
    fun sendEmergencyMessage(message: String) {
        viewModelScope.launch {
            sendEmergencyMessageUseCase(message)
        }
    }
    
    private fun handleMeshEvent(event: MeshEvent) {
        when (event) {
            is MeshEvent.MessageReceived -> {
                val uiMessage = UiMessage(
                    id = event.message.id.toString(),
                    content = String(event.message.content, Charsets.UTF_8),
                    senderName = event.fromPeer.name ?: event.fromPeer.id,
                    timestamp = event.message.timestamp,
                    isFromCurrentDevice = false,
                    hopCount = event.message.hopCount.toInt(),
                    isEmergency = String(event.message.content, Charsets.UTF_8).contains("🆘 EMERGENCY")
                )
                
                _uiState.value = _uiState.value.copy(
                    messages = _uiState.value.messages + uiMessage
                )
            }
            is MeshEvent.MessageDelivered -> {
                // Update message as delivered
                val updatedMessages = _uiState.value.messages.map { message ->
                    if (message.id == event.messageId.toString()) {
                        message.copy(isDelivered = true)
                    } else {
                        message
                    }
                }
                _uiState.value = _uiState.value.copy(messages = updatedMessages)
            }
            // Handle other events...
            else -> {}
        }
    }
    
    private fun updateStats() {
        val stats = getMeshStatsUseCase()
        _uiState.value = _uiState.value.copy(
            meshStats = stats,
            isServiceRunning = true // This should come from service state
        )
    }
}

data class MeshChatUiState(
    val messages: List<UiMessage> = emptyList(),
    val currentMessage: String = "",
    val meshStats: MeshStats = MeshStats(),
    val transportStats: Map<com.appvalence.hayatkurtar.domain.transport.TransportType, com.appvalence.hayatkurtar.domain.transport.TransportStats> = emptyMap(),
    val isServiceRunning: Boolean = false
)

data class UiMessage(
    val id: String,
    val content: String,
    val senderName: String,
    val timestamp: Long,
    val isFromCurrentDevice: Boolean,
    val hopCount: Int = 0,
    val isDelivered: Boolean = false,
    val isEmergency: Boolean = false
)