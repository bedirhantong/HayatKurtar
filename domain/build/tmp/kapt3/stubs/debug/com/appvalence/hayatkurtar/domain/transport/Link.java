package com.appvalence.hayatkurtar.domain.transport;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import kotlinx.coroutines.flow.Flow;

/**
 * Represents an active connection/link to a peer
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00a6@\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012H&J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0014\u001a\u00020\u0015H\u00a6@\u00a2\u0006\u0002\u0010\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0017"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/Link;", "", "isConnected", "", "()Z", "linkQuality", "Lcom/appvalence/hayatkurtar/domain/transport/LinkQuality;", "getLinkQuality", "()Lcom/appvalence/hayatkurtar/domain/transport/LinkQuality;", "peer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "getPeer", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "disconnect", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeConnection", "Lkotlinx/coroutines/flow/Flow;", "send", "frame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "(Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public abstract interface Link {
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.appvalence.hayatkurtar.domain.transport.Peer getPeer();
    
    public abstract boolean isConnected();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.appvalence.hayatkurtar.domain.transport.LinkQuality getLinkQuality();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object send(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object disconnect(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Boolean> observeConnection();
}