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

/**
 * LRU Cache for frequently accessed seen messages
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u0011J \u0010\u0012\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00072\b\b\u0002\u0010\u0013\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\u00020\u0003H\u0086@\u00a2\u0006\u0002\u0010\rR\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/appvalence/hayatkurtar/data/mesh/store/SeenMessageCache;", "", "maxSize", "", "(I)V", "cache", "Ljava/util/LinkedHashMap;", "", "", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "clear", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "contains", "", "messageId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "put", "timestamp", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "size", "mesh_debug"})
public final class SeenMessageCache {
    private final int maxSize = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.LinkedHashMap<java.lang.String, java.lang.Long> cache = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.sync.Mutex mutex = null;
    
    public SeenMessageCache(int maxSize) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object contains(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object put(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object size(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clear(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public SeenMessageCache() {
        super();
    }
}