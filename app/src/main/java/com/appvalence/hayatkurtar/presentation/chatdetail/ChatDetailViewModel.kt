package com.appvalence.hayatkurtar.presentation.chatdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import com.appvalence.hayatkurtar.domain.model.ChatMessage
import com.appvalence.hayatkurtar.domain.usecase.ConnectDeviceUseCase
import com.appvalence.hayatkurtar.domain.usecase.DisconnectUseCase
import com.appvalence.hayatkurtar.domain.usecase.ObserveMessagesByPeerUseCase
import com.appvalence.hayatkurtar.domain.usecase.SendMessageUseCase
import com.appvalence.hayatkurtar.domain.usecase.ObserveConnectionStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val connectDeviceUseCase: ConnectDeviceUseCase,
    private val disconnectUseCase: DisconnectUseCase,
    private val observeMessagesByPeerUseCase: ObserveMessagesByPeerUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val observeConnectionStateUseCase: ObserveConnectionStateUseCase,
    private val ensureBondedUseCase: com.appvalence.hayatkurtar.domain.usecase.EnsureBondedUseCase,
) : ViewModel() {

    enum class PairingState { Idle, Requesting, Success, Failed }

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _peerTitle = MutableStateFlow("")
    val peerTitle: StateFlow<String> = _peerTitle.asStateFlow()

    private val _pairingState = MutableStateFlow(PairingState.Idle)
    val pairingState: StateFlow<PairingState> = _pairingState.asStateFlow()

    @Volatile private var lastAddress: String? = null
    private var messagesCollectorJob: Job? = null

    fun start(address: String) {
        // Always start observing local messages immediately, regardless of connection state
        messagesCollectorJob?.cancel()
        messagesCollectorJob = viewModelScope.launch {
            observeMessagesByPeerUseCase(address).collectLatest { list ->
                val sorted = list.sortedByDescending { it.timestamp }
                _messages.value = sorted
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            lastAddress = address
            var connected = false
            repeat(3) { attempt ->
                if (!connected) {
                    // Try to pair first if needed to avoid manual confirmation both sides
                    _pairingState.value = PairingState.Requesting
                    val bonded = ensureBondedUseCase(address)
                    _pairingState.value = if (bonded) PairingState.Success else PairingState.Failed
                    connected = if (bonded) connectDeviceUseCase(address) else false
                    _isConnected.value = connected
                    if (!connected) kotlinx.coroutines.delay(1500)
                }
            }
        }
        // Observe connection state to keep UI in sync even if socket drops
        viewModelScope.launch {
            observeConnectionStateUseCase().collectLatest { state ->
                _isConnected.value = state
            }
        }

        // Resolve a human-friendly peer name for the top bar
        viewModelScope.launch(Dispatchers.IO) {
            _peerTitle.value = resolveDeviceName(address)
        }
    }

    fun send(text: String) {
        viewModelScope.launch(Dispatchers.IO) { sendMessageUseCase(text) }
    }

    fun disconnect() {
        viewModelScope.launch(Dispatchers.IO) { disconnectUseCase() }
    }

    fun retryPair(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _pairingState.value = PairingState.Requesting
            val bonded = ensureBondedUseCase(address)
            _pairingState.value = if (bonded) PairingState.Success else PairingState.Failed
        }
    }

    fun reconnect() {
        val addr = lastAddress ?: return
        viewModelScope.launch(Dispatchers.IO) {
            // Avoid pairing again here; just try connecting
            val ok = connectDeviceUseCase(addr)
            _isConnected.value = ok
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
}


