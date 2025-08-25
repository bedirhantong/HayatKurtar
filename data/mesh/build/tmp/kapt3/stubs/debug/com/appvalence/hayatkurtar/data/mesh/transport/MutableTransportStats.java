package com.appvalence.hayatkurtar.data.mesh.transport;

import com.appvalence.hayatkurtar.core.logging.MeshLog;
import com.appvalence.hayatkurtar.core.logging.MeshLogTags;
import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.result.MeshException;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.transport.*;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mutable statistics for tracking transport performance
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001BS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0005H\u00c6\u0003JY\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020$H\u00d6\u0001J\t\u0010%\u001a\u00020&H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006'"}, d2 = {"Lcom/appvalence/hayatkurtar/data/mesh/transport/MutableTransportStats;", "", "transportType", "Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "messagesSent", "Ljava/util/concurrent/atomic/AtomicLong;", "messagesReceived", "connectionAttempts", "successfulConnections", "discoveredPeers", "averageLatencyMs", "lastActivityTime", "(Lcom/appvalence/hayatkurtar/domain/transport/TransportType;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;)V", "getAverageLatencyMs", "()Ljava/util/concurrent/atomic/AtomicLong;", "getConnectionAttempts", "getDiscoveredPeers", "getLastActivityTime", "getMessagesReceived", "getMessagesSent", "getSuccessfulConnections", "getTransportType", "()Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "", "toString", "", "mesh_debug"})
final class MutableTransportStats {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.transport.TransportType transportType = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicLong messagesSent = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicLong messagesReceived = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicLong connectionAttempts = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicLong successfulConnections = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicLong discoveredPeers = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicLong averageLatencyMs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicLong lastActivityTime = null;
    
    public MutableTransportStats(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.TransportType transportType, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong messagesSent, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong messagesReceived, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong connectionAttempts, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong successfulConnections, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong discoveredPeers, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong averageLatencyMs, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong lastActivityTime) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.transport.TransportType getTransportType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong getMessagesSent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong getMessagesReceived() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong getConnectionAttempts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong getSuccessfulConnections() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong getDiscoveredPeers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong getAverageLatencyMs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong getLastActivityTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.transport.TransportType component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicLong component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.data.mesh.transport.MutableTransportStats copy(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.TransportType transportType, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong messagesSent, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong messagesReceived, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong connectionAttempts, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong successfulConnections, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong discoveredPeers, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong averageLatencyMs, @org.jetbrains.annotations.NotNull()
    java.util.concurrent.atomic.AtomicLong lastActivityTime) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}