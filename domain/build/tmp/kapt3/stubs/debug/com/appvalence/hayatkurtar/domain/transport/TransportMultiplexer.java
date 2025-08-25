package com.appvalence.hayatkurtar.domain.transport;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import kotlinx.coroutines.flow.Flow;

/**
 * Manages multiple transport strategies and orchestrates connections
 * Implements connection orchestrator pattern for handling multiple transport types
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\r\u001a\u00020\u000eH\u00a6@\u00a2\u0006\u0002\u0010\u000fJ\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u00a6@\u00a2\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u0003H&J\b\u0010\u0016\u001a\u00020\u0017H&J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0019H&J\u0014\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d0\u001bH&J\u0010\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\u001cH&J$\u0010 \u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u000eH\u00a6@\u00a2\u0006\u0002\u0010!J\u000e\u0010\"\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010#J\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u00a6@\u00a2\u0006\u0002\u0010#J\u000e\u0010%\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010#J\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u00a6@\u00a2\u0006\u0002\u0010#J\u0016\u0010'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\u0017H\u00a6@\u00a2\u0006\u0002\u0010)R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006*"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/TransportMultiplexer;", "", "transportEvents", "Lkotlinx/coroutines/flow/Flow;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "getTransportEvents", "()Lkotlinx/coroutines/flow/Flow;", "addTransport", "", "strategy", "Lcom/appvalence/hayatkurtar/domain/transport/TransportStrategy;", "broadcast", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "frame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "(Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connectToPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Link;", "peer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "discoverPeers", "getConfig", "Lcom/appvalence/hayatkurtar/domain/transport/TransportConfig;", "getConnectedPeers", "", "getTransportStats", "", "Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportStats;", "removeTransport", "transportType", "sendToPeer", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "start", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startDiscovery", "stop", "stopDiscovery", "updateConfig", "config", "(Lcom/appvalence/hayatkurtar/domain/transport/TransportConfig;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public abstract interface TransportMultiplexer {
    
    /**
     * Add a transport strategy to the multiplexer
     */
    public abstract void addTransport(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.TransportStrategy strategy);
    
    /**
     * Remove a transport strategy
     */
    public abstract void removeTransport(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.TransportType transportType);
    
    /**
     * Start all available transport strategies
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object start(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Stop all transport strategies
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stop(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Discover peers across all transport strategies
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.transport.Peer> discoverPeers();
    
    /**
     * Start discovery and advertising across all transports
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object startDiscovery(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion);
    
    /**
     * Stop discovery and advertising across all transports
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stopDiscovery(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion);
    
    /**
     * Connect to a peer using the best available transport
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object connectToPeer(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer peer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<? extends com.appvalence.hayatkurtar.domain.transport.Link>> $completion);
    
    /**
     * Broadcast frame to all connected peers across all transports
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object broadcast(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion);
    
    /**
     * Send frame to specific peer using the best available connection
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendToPeer(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer peer, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion);
    
    /**
     * Get all connected peers across all transports
     */
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.appvalence.hayatkurtar.domain.transport.Peer> getConnectedPeers();
    
    /**
     * Get transport events from all strategies
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.transport.TransportEvent> getTransportEvents();
    
    /**
     * Get current transport configuration
     */
    @org.jetbrains.annotations.NotNull()
    public abstract com.appvalence.hayatkurtar.domain.transport.TransportConfig getConfig();
    
    /**
     * Update transport configuration
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateConfig(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.TransportConfig config, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Get statistics for all transports
     */
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.Map<com.appvalence.hayatkurtar.domain.transport.TransportType, com.appvalence.hayatkurtar.domain.transport.TransportStats> getTransportStats();
}