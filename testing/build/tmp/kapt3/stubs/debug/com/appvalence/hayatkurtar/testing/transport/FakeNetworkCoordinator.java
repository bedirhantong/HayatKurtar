package com.appvalence.hayatkurtar.testing.transport;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.transport.*;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Coordinates communication between fake transport nodes for testing
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\n2\u0006\u0010\u000b\u001a\u00020\fJ\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\nJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000f\u001a\u00020\u0005J\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\n2\u0006\u0010\u0011\u001a\u00020\u0005J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0006J\u001e\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0019J\u0006\u0010\u001a\u001a\u00020\u0013J\"\u0010\u001b\u001a\u00020\u00132\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00050\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u001dJ\u000e\u0010\u001f\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/appvalence/hayatkurtar/testing/transport/FakeNetworkCoordinator;", "", "()V", "nodes", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lcom/appvalence/hayatkurtar/testing/transport/FakeTransportStrategy;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "createMeshTopology", "", "nodeCount", "", "getAllNodes", "getNode", "nodeId", "getOtherNodes", "excludeNodeId", "registerNode", "", "node", "sendFrame", "fromNodeId", "toNodeId", "frame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "shutdown", "simulateNetworkPartition", "group1", "", "group2", "unregisterNode", "testing_debug"})
public final class FakeNetworkCoordinator {
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy> nodes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    
    public FakeNetworkCoordinator() {
        super();
    }
    
    public final void registerNode(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy node) {
    }
    
    public final void unregisterNode(@org.jetbrains.annotations.NotNull()
    java.lang.String nodeId) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy getNode(@org.jetbrains.annotations.NotNull()
    java.lang.String nodeId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy> getOtherNodes(@org.jetbrains.annotations.NotNull()
    java.lang.String excludeNodeId) {
        return null;
    }
    
    public final void sendFrame(@org.jetbrains.annotations.NotNull()
    java.lang.String fromNodeId, @org.jetbrains.annotations.NotNull()
    java.lang.String toNodeId, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy> getAllNodes() {
        return null;
    }
    
    public final void shutdown() {
    }
    
    /**
     * Simulate network partition between two groups of nodes
     */
    public final void simulateNetworkPartition(@org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> group1, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> group2) {
    }
    
    /**
     * Create a mesh network topology for testing
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy> createMeshTopology(int nodeCount) {
        return null;
    }
}