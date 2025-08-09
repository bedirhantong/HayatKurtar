package com.appvalence.hayatkurtar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: MessageEntity)

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun observeAll(): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE peerAddress = :peer ORDER BY timestamp ASC")
    fun observeByPeer(peer: String): Flow<List<MessageEntity>>

    @Query("DELETE FROM messages WHERE peerAddress = :peer")
    suspend fun deleteByPeer(peer: String)
}


