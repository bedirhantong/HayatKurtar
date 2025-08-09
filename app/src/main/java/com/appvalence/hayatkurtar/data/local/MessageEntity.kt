package com.appvalence.hayatkurtar.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val sender: String,
    val content: String,
    val timestamp: Long,
    val peerAddress: String,
)


