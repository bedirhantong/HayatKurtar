package com.appvalence.hayatkurtar.domain.mesh;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.protocol.Priority;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.transport.Peer;
import kotlinx.coroutines.flow.Flow;
import java.util.UUID;

/**
 * Core mesh router interface for message routing and handling
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J)\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0014\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011H&J\b\u0010\u0014\u001a\u00020\u0015H&J\u001e\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\rH\u00a6@\u00a2\u0006\u0002\u0010\u001aJ0\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010 \u001a\u00020!H\u00a6@\u00a2\u0006\u0002\u0010\"J\u000e\u0010#\u001a\u00020\tH\u00a6@\u00a2\u0006\u0002\u0010$J\u000e\u0010%\u001a\u00020\tH\u00a6@\u00a2\u0006\u0002\u0010$R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006&"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshRouter;", "", "events", "Lkotlinx/coroutines/flow/Flow;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "getEvents", "()Lkotlinx/coroutines/flow/Flow;", "acknowledgeMessage", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "", "messageId", "Lcom/appvalence/hayatkurtar/domain/mesh/MessageId;", "toPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "acknowledgeMessage-mNZjwBM", "(Ljava/util/UUID;Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getKnownRoutes", "", "", "Lcom/appvalence/hayatkurtar/domain/mesh/RouteInfo;", "getStats", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshStats;", "handleIncomingFrame", "frame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "fromPeer", "(Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendMessage", "content", "", "priority", "Lcom/appvalence/hayatkurtar/core/protocol/Priority;", "ttl", "", "([BLcom/appvalence/hayatkurtar/core/protocol/Priority;BLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "start", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stop", "domain_debug"})
public abstract interface MeshRouter {
    
    /**
     * Send a message through the mesh network
     * @param content Message content (text, binary data)
     * @param priority Message priority level
     * @param ttl Time-to-live (hop count limit)
     * @return MessageId for tracking
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendMessage(@org.jetbrains.annotations.NotNull()
    byte[] content, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Priority priority, byte ttl, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<com.appvalence.hayatkurtar.domain.mesh.MessageId>> $completion);
    
    /**
     * Handle incoming frame from transport layer
     * @param frame Received frame
     * @param fromPeer Peer that sent the frame
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object handleIncomingFrame(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer fromPeer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Get mesh events flow for UI updates
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.mesh.MeshEvent> getEvents();
    
    /**
     * Get current mesh statistics
     */
    @org.jetbrains.annotations.NotNull()
    public abstract com.appvalence.hayatkurtar.domain.mesh.MeshStats getStats();
    
    /**
     * Get all known routes in the network
     */
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.Map<java.lang.Long, com.appvalence.hayatkurtar.domain.mesh.RouteInfo> getKnownRoutes();
    
    /**
     * Start the mesh router
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object start(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Stop the mesh router and cleanup
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stop(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Core mesh router interface for message routing and handling
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}