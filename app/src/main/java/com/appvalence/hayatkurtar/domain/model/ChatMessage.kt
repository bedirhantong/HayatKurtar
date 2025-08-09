package com.appvalence.hayatkurtar.domain.model

data class ChatMessage(
    val id: Long = 0L,
    val sender: String,
    val content: String,
    val timestamp: Long,
    val peerAddress: String,
)


