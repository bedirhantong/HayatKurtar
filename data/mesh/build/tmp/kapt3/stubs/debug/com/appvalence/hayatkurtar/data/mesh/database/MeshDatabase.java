package com.appvalence.hayatkurtar.data.mesh.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.appvalence.hayatkurtar.data.mesh.store.MessageStoreDao;
import com.appvalence.hayatkurtar.data.mesh.store.PendingMessageEntity;
import com.appvalence.hayatkurtar.data.mesh.store.SeenMessageEntity;

/**
 * Room database for mesh networking data
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lcom/appvalence/hayatkurtar/data/mesh/database/MeshDatabase;", "Landroidx/room/RoomDatabase;", "()V", "messageStoreDao", "Lcom/appvalence/hayatkurtar/data/mesh/store/MessageStoreDao;", "Companion", "mesh_debug"})
@androidx.room.Database(entities = {com.appvalence.hayatkurtar.data.mesh.store.SeenMessageEntity.class, com.appvalence.hayatkurtar.data.mesh.store.PendingMessageEntity.class}, version = 1, exportSchema = false)
public abstract class MeshDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATABASE_NAME = "mesh_network_db";
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.hayatkurtar.data.mesh.database.MeshDatabase.Companion Companion = null;
    
    public MeshDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.appvalence.hayatkurtar.data.mesh.store.MessageStoreDao messageStoreDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/appvalence/hayatkurtar/data/mesh/database/MeshDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "create", "Lcom/appvalence/hayatkurtar/data/mesh/database/MeshDatabase;", "context", "Landroid/content/Context;", "mesh_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.data.mesh.database.MeshDatabase create(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}