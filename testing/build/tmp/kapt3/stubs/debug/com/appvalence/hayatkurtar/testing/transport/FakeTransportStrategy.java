package com.appvalence.hayatkurtar.testing.transport;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.transport.*;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Fake transport strategy for testing mesh networking scenarios
 * Simulates network behavior including latency, packet loss, and disconnections
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0012\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u00102\u001a\b\u0012\u0004\u0012\u000204032\u0006\u00105\u001a\u000206H\u0096@\u00a2\u0006\u0002\u00107J\u001c\u00108\u001a\b\u0012\u0004\u0012\u000209032\u0006\u0010:\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010;J\u000e\u0010<\u001a\b\u0012\u0004\u0012\u00020\u000b0=H\u0016J\u000e\u0010>\u001a\b\u0012\u0004\u0012\u00020\u000b0?H\u0016J\u0012\u0010@\u001a\u0004\u0018\u0001092\u0006\u0010A\u001a\u00020\u0003H\u0016J\u001e\u0010B\u001a\u0002042\u0006\u0010C\u001a\u00020\u00032\u0006\u0010D\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010EJ\u001e\u0010F\u001a\u0002042\u0006\u00105\u001a\u0002062\u0006\u0010C\u001a\u00020\u0003H\u0086@\u00a2\u0006\u0002\u0010GJ$\u0010H\u001a\b\u0012\u0004\u0012\u000204032\u0006\u0010:\u001a\u00020\u000b2\u0006\u00105\u001a\u000206H\u0096@\u00a2\u0006\u0002\u0010IJ\u000e\u0010J\u001a\u0002042\u0006\u0010K\u001a\u00020\u0010J\u0016\u0010L\u001a\u0002042\u0006\u0010A\u001a\u00020\u0003H\u0086@\u00a2\u0006\u0002\u0010MJ\u0016\u0010N\u001a\b\u0012\u0004\u0012\u00020\u000e0=2\u0006\u0010.\u001a\u00020/H\u0016J\u000e\u0010O\u001a\u000204H\u0096@\u00a2\u0006\u0002\u0010PR\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u0013X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010'\u001a\u00020(X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020\u000b0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010/X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101\u00a8\u0006Q"}, d2 = {"Lcom/appvalence/hayatkurtar/testing/transport/FakeTransportStrategy;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportStrategy;", "nodeId", "", "transportType", "Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "(Ljava/lang/String;Lcom/appvalence/hayatkurtar/domain/transport/TransportType;)V", "connections", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/appvalence/hayatkurtar/testing/transport/FakeLink;", "discoveredPeers", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "eventChannel", "Lkotlinx/coroutines/channels/Channel;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "isAvailable", "", "()Z", "isEnabled", "Ljava/util/concurrent/atomic/AtomicBoolean;", "isEnabled$testing_debug", "()Ljava/util/concurrent/atomic/AtomicBoolean;", "latencyMs", "", "getLatencyMs", "()J", "setLatencyMs", "(J)V", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "name", "getName", "()Ljava/lang/String;", "networkCoordinator", "Lcom/appvalence/hayatkurtar/testing/transport/FakeNetworkCoordinator;", "getNetworkCoordinator", "()Lcom/appvalence/hayatkurtar/testing/transport/FakeNetworkCoordinator;", "setNetworkCoordinator", "(Lcom/appvalence/hayatkurtar/testing/transport/FakeNetworkCoordinator;)V", "packetLossRate", "", "getPacketLossRate", "()D", "setPacketLossRate", "(D)V", "peerChannel", "scope", "Lkotlinx/coroutines/CoroutineScope;", "getTransportType", "()Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "broadcast", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "", "frame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "(Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connectTo", "Lcom/appvalence/hayatkurtar/domain/transport/Link;", "peer", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "discoverPeers", "Lkotlinx/coroutines/flow/Flow;", "getConnectedPeers", "", "getLink", "peerId", "handleIncomingConnection", "fromNodeId", "remoteLink", "(Ljava/lang/String;Lcom/appvalence/hayatkurtar/testing/transport/FakeLink;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveFrame", "(Lcom/appvalence/hayatkurtar/core/protocol/Frame;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendTo", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setEnabled", "enabled", "simulateDisconnection", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "start", "stop", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "testing_debug"})
public final class FakeTransportStrategy implements com.appvalence.hayatkurtar.domain.transport.TransportStrategy {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nodeId = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.transport.TransportType transportType = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    private long latencyMs = 100L;
    private double packetLossRate = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicBoolean isEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.appvalence.hayatkurtar.testing.transport.FakeLink> connections = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.appvalence.hayatkurtar.domain.transport.Peer> discoveredPeers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.sync.Mutex mutex = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.channels.Channel<com.appvalence.hayatkurtar.domain.transport.TransportEvent> eventChannel = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.channels.Channel<com.appvalence.hayatkurtar.domain.transport.Peer> peerChannel = null;
    @org.jetbrains.annotations.Nullable()
    private com.appvalence.hayatkurtar.testing.transport.FakeNetworkCoordinator networkCoordinator;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.CoroutineScope scope;
    
    public FakeTransportStrategy(@org.jetbrains.annotations.NotNull()
    java.lang.String nodeId, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.TransportType transportType) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.appvalence.hayatkurtar.domain.transport.TransportType getTransportType() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getName() {
        return null;
    }
    
    public final long getLatencyMs() {
        return 0L;
    }
    
    public final void setLatencyMs(long p0) {
    }
    
    public final double getPacketLossRate() {
        return 0.0;
    }
    
    public final void setPacketLossRate(double p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.atomic.AtomicBoolean isEnabled$testing_debug() {
        return null;
    }
    
    @java.lang.Override()
    public boolean isAvailable() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.appvalence.hayatkurtar.testing.transport.FakeNetworkCoordinator getNetworkCoordinator() {
        return null;
    }
    
    public final void setNetworkCoordinator(@org.jetbrains.annotations.Nullable()
    com.appvalence.hayatkurtar.testing.transport.FakeNetworkCoordinator p0) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.transport.TransportEvent> start(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.transport.Peer> discoverPeers() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object connectTo(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer peer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<? extends com.appvalence.hayatkurtar.domain.transport.Link>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object broadcast(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object sendTo(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer peer, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.appvalence.hayatkurtar.domain.transport.Peer> getConnectedPeers() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.appvalence.hayatkurtar.domain.transport.Link getLink(@org.jetbrains.annotations.NotNull()
    java.lang.String peerId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object stop(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Simulate receiving a frame from another node
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object receiveFrame(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    java.lang.String fromNodeId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Handle incoming connection from another node
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object handleIncomingConnection(@org.jetbrains.annotations.NotNull()
    java.lang.String fromNodeId, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.testing.transport.FakeLink remoteLink, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Simulate network disconnection
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object simulateDisconnection(@org.jetbrains.annotations.NotNull()
    java.lang.String peerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Enable/disable the transport
     */
    public final void setEnabled(boolean enabled) {
    }
}