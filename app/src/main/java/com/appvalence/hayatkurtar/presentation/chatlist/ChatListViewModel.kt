package com.appvalence.hayatkurtar.presentation.chatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appvalence.hayatkurtar.domain.model.ChatMessage
import com.appvalence.hayatkurtar.domain.usecase.ObserveMessagesUseCase
import com.appvalence.hayatkurtar.domain.usecase.DeleteChatByPeerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : ViewModel() {

    private val _chats = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chats: StateFlow<List<ChatMessage>> = _chats.asStateFlow()

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
    }

    fun delete(peer: String) {
        viewModelScope.launch { deleteChatByPeerUseCase(peer) }
    }
}


