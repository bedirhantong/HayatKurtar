package com.appvalence.hayatkurtar.presentation.chatdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appvalence.hayatkurtar.domain.model.ChatMessage
import com.appvalence.hayatkurtar.domain.usecase.ConnectDeviceUseCase
import com.appvalence.hayatkurtar.domain.usecase.DisconnectUseCase
import com.appvalence.hayatkurtar.domain.usecase.ObserveMessagesByPeerUseCase
import com.appvalence.hayatkurtar.domain.usecase.SendMessageUseCase
import com.appvalence.hayatkurtar.domain.usecase.ObserveConnectionStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    fun start(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var connected = false
            repeat(3) { attempt ->
                if (!connected) {
                    connected = connectDeviceUseCase(address)
                    _isConnected.value = connected
                    if (!connected) kotlinx.coroutines.delay(1500)
                }
            }
            if (connected) {
                viewModelScope.launch {
                    observeMessagesByPeerUseCase(address).collectLatest { list ->
                        _messages.value = list
                    }
                }
            }
        }
        // Observe connection state to keep UI in sync even if socket drops
        viewModelScope.launch {
            observeConnectionStateUseCase().collectLatest { state ->
                _isConnected.value = state
            }
        }
    }

    fun send(text: String) {
        viewModelScope.launch(Dispatchers.IO) { sendMessageUseCase(text) }
    }

    fun disconnect() {
        viewModelScope.launch(Dispatchers.IO) { disconnectUseCase() }
    }
}


