package com.appvalence.hayatkurtar.presentation.chatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appvalence.hayatkurtar.domain.model.ChatMessage
import com.appvalence.hayatkurtar.domain.usecase.ObserveMessagesUseCase
import com.appvalence.hayatkurtar.domain.usecase.DeleteChatByPeerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.appvalence.hayatkurtar.domain.usecase.IsBluetoothEnabledUseCase
import com.appvalence.hayatkurtar.data.bluetooth.BleAdvertiser
import android.bluetooth.BluetoothAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val deleteChatByPeerUseCase: DeleteChatByPeerUseCase,
    private val isBluetoothEnabledUseCase: IsBluetoothEnabledUseCase,
    private val bleAdvertiser: BleAdvertiser,
) : ViewModel() {

    private val _chats = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chats: StateFlow<List<ChatMessage>> = _chats.asStateFlow()

    private val _localDeviceName = MutableStateFlow("")
    val localDeviceName: StateFlow<String> = _localDeviceName.asStateFlow()

    private val _isAdvertising = MutableStateFlow(false)
    val isAdvertising: StateFlow<Boolean> = _isAdvertising.asStateFlow()

    init {
        viewModelScope.launch {
            observeMessagesUseCase().collectLatest { list ->
                // WhatsApp-like: group by peer and show last message per chat
                val grouped = list
                    .groupBy { it.peerAddress }
                    .values
                    .mapNotNull { messages -> messages.maxByOrNull { it.timestamp } }
                    .sortedByDescending { it.timestamp }
                _chats.value = grouped
            }
        }

        // Try to determine this device's Bluetooth name for header
        val adapter = BluetoothAdapter.getDefaultAdapter()
        _localDeviceName.value = adapter?.name ?: android.os.Build.MODEL
    }
    fun enableVisibility() {
        if (!_isAdvertising.value) {
            _isAdvertising.value = bleAdvertiser.start()
        }
    }

    fun disableVisibility() {
        if (_isAdvertising.value) {
            bleAdvertiser.stop()
            _isAdvertising.value = false
        }
    }

    fun delete(peer: String) {
        viewModelScope.launch { deleteChatByPeerUseCase(peer) }
    }
}


