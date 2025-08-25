package com.appvalence.hayatkurtar.data.mesh.store;

import androidx.room.*;
import com.appvalence.hayatkurtar.core.logging.MeshLog;
import com.appvalence.hayatkurtar.core.logging.MeshLogTags;
import com.appvalence.hayatkurtar.domain.mesh.MessageId;
import com.appvalence.hayatkurtar.domain.mesh.MessageStore;
import com.appvalence.hayatkurtar.domain.mesh.MeshMessage;
import com.appvalence.hayatkurtar.domain.mesh.StorageStats;
import com.appvalence.hayatkurtar.domain.transport.Peer;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u00a8\u0006\u0003"}, d2 = {"toMeshMessage", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshMessage;", "Lcom/appvalence/hayatkurtar/data/mesh/store/PendingMessageEntity;", "mesh_debug"})
public final class RoomMessageStoreKt {
    
    /**
     * Extension function to convert entity to domain model
     */
    private static final com.appvalence.hayatkurtar.domain.mesh.MeshMessage toMeshMessage(com.appvalence.hayatkurtar.data.mesh.store.PendingMessageEntity $this$toMeshMessage) {
        return null;
    }
}