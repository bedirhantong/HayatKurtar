package com.appvalence.hayatkurtar.domain.transport;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import kotlinx.coroutines.flow.Flow;

/**
 * Strategy interface for different transport mechanisms (Bluetooth, Wi-Fi Direct)
 * Implements Strategy pattern for pluggable transport implementations
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u00a6@\u00a2\u0006\u0002\u0010\u0012J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u00a6@\u00a2\u0006\u0002\u0010\u0017J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00160\u0019H&J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00160\u001bH&J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u001d\u001a\u00020\u0006H&J$\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0011H\u00a6@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u00192\u0006\u0010\"\u001a\u00020#H&J\u000e\u0010$\u001a\u00020\u000fH\u00a6@\u00a2\u0006\u0002\u0010%R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006&"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/TransportStrategy;", "", "isAvailable", "", "()Z", "name", "", "getName", "()Ljava/lang/String;", "transportType", "Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "getTransportType", "()Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "broadcast", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "", "frame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "(Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connectTo", "Lcom/appvalence/hayatkurtar/domain/transport/Link;", "peer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "discoverPeers", "Lkotlinx/coroutines/flow/Flow;", "getConnectedPeers", "", "getLink", "peerId", "sendTo", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "start", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "stop", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public abstract interface TransportStrategy {
    
    /**
     * Name of this transport strategy
     */
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String getName();
    
    /**
     * Transport type this strategy handles
     */
    @org.jetbrains.annotations.NotNull()
    public abstract com.appvalence.hayatkurtar.domain.transport.TransportType getTransportType();
    
    /**
     * Whether this transport is currently available/enabled
     */
    public abstract boolean isAvailable();
    
    /**
     * Start the transport strategy and return events flow
     * @param scope Coroutine scope for background operations
     * @return Flow of transport events
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.transport.TransportEvent> start(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope);
    
    /**
     * Discover peers using this transport
     * @return Flow of discovered peers
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.transport.Peer> discoverPeers();
    
    /**
     * Connect to a specific peer
     * @param peer Target peer to connect to
     * @return Link if successful
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object connectTo(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer peer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<? extends com.appvalence.hayatkurtar.domain.transport.Link>> $completion);
    
    /**
     * Broadcast frame to all connected peers
     * @param frame Frame to broadcast
     * @return Success or error result
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object broadcast(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion);
    
    /**
     * Send frame to specific peer
     * @param peer Target peer
     * @param frame Frame to send
     * @return Success or error result
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendTo(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer peer, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion);
    
    /**
     * Get all currently connected peers
     */
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.appvalence.hayatkurtar.domain.transport.Peer> getConnectedPeers();
    
    /**
     * Get specific link by peer ID
     */
    @org.jetbrains.annotations.Nullable()
    public abstract com.appvalence.hayatkurtar.domain.transport.Link getLink(@org.jetbrains.annotations.NotNull()
    java.lang.String peerId);
    
    /**
     * Stop the transport strategy and cleanup resources
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stop(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}