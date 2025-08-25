package com.appvalence.hayatkurtar.data.mesh.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.appvalence.hayatkurtar.data.mesh.store.MessageStoreDao
import com.appvalence.hayatkurtar.data.mesh.store.PendingMessageEntity
import com.appvalence.hayatkurtar.data.mesh.store.SeenMessageEntity

/**
 * Room database for mesh networking data
 */
@Database(
    entities = [
        SeenMessageEntity::class,
        PendingMessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MeshDatabase : RoomDatabase() {
    abstract fun messageStoreDao(): MessageStoreDao
    
    companion object {
        const val DATABASE_NAME = "mesh_network_db"
        
        fun create(context: Context): MeshDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MeshDatabase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
        }
    }
}