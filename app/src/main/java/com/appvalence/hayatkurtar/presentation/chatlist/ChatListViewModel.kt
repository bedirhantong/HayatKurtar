package com.appvalence.hayatkurtar.presentation.chatlist

import android.annotation.SuppressLint
import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appvalence.hayatkurtar.domain.model.ChatMessage
import com.appvalence.hayatkurtar.domain.usecase.ObserveMessagesUseCase
import com.appvalence.hayatkurtar.domain.usecase.DeleteChatByPeerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.appvalence.hayatkurtar.domain.usecase.IsBluetoothEnabledUseCase
import com.appvalence.bluetooth.api.BleAdvertiser
import android.bluetooth.BluetoothAdapter
import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.appvalence.hayatkurtar.data.local.UserPrefsStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val deleteChatByPeerUseCase: DeleteChatByPeerUseCase,
    private val isBluetoothEnabledUseCase: IsBluetoothEnabledUseCase,
    private val bleAdvertiser: BleAdvertiser,
    private val userPrefs: UserPrefsStore,
) : ViewModel() {

    private val _chats = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chats: StateFlow<List<ChatMessage>> = _chats.asStateFlow()

    private val _localDeviceName = MutableStateFlow("")
    val localDeviceName: StateFlow<String> = _localDeviceName.asStateFlow()

    private val _isAdvertising = MutableStateFlow(false)
    val isAdvertising: StateFlow<Boolean> = _isAdvertising.asStateFlow()

    private val _isBluetoothEnabled = MutableStateFlow(true)
    val isBluetoothEnabled: StateFlow<Boolean> = _isBluetoothEnabled.asStateFlow()

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
        @SuppressLint("MissingPermission")
        val adapter = BluetoothAdapter.getDefaultAdapter()
        _localDeviceName.value = adapter?.name ?: Build.MODEL
        _isBluetoothEnabled.value = isBluetoothEnabledUseCase()

        // Observe preferences
        viewModelScope.launch(Dispatchers.IO) {
            userPrefs.autoVisibilityEnabled.collect { auto ->
                if (auto && !_isAdvertising.value) enableVisibility()
                _autoVisibility.value = auto
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            userPrefs.discoverablePromptShown.collect { shown ->
                _promptShown.value = shown
            }
        }
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

    fun refreshBluetoothEnabled() {
        _isBluetoothEnabled.value = isBluetoothEnabledUseCase()
    }

    fun markDiscoverablePromptShown() {
        viewModelScope.launch(Dispatchers.IO) { userPrefs.setDiscoverablePromptShown(true) }
    }

    fun saveAutoVisibilityPreference(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) { userPrefs.setAutoVisibilityEnabled(enabled) }
    }

    private val _promptShown = MutableStateFlow(false)
    val promptShown: StateFlow<Boolean> = _promptShown.asStateFlow()

    private val _autoVisibility = MutableStateFlow(false)
    val autoVisibility: StateFlow<Boolean> = _autoVisibility.asStateFlow()

    fun delete(peer: String) {
        viewModelScope.launch { deleteChatByPeerUseCase(peer) }
    }
}


