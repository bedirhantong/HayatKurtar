package com.appvalence.hayatkurtar.domain.mesh;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.protocol.Priority;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.transport.Peer;
import kotlinx.coroutines.flow.Flow;
import java.util.UUID;

/**
 * Events from the mesh router
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\t\u0003\u0004\u0005\u0006\u0007\b\t\n\u000bB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\t\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "", "()V", "MessageAcknowledged", "MessageDelivered", "MessageDropped", "MessageExpired", "MessageForwarded", "MessageReceived", "NetworkStateChanged", "RouteDiscovered", "RouteLost", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageAcknowledged;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageDelivered;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageDropped;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageExpired;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageForwarded;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageReceived;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$NetworkStateChanged;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$RouteDiscovered;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$RouteLost;", "domain_debug"})
public abstract class MeshEvent {
    
    private MeshEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\f\u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\nJ\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0019\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u001a"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageAcknowledged;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "messageId", "Lcom/appvalence/hayatkurtar/domain/mesh/MessageId;", "fromPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "(Ljava/util/UUID;Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getFromPeer", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "getMessageId-f2SR-2M", "()Ljava/util/UUID;", "Ljava/util/UUID;", "component1", "component1-f2SR-2M", "component2", "copy", "copy-meG8Gq0", "(Ljava/util/UUID;Lcom/appvalence/hayatkurtar/domain/transport/Peer;)Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageAcknowledged;", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class MessageAcknowledged extends com.appvalence.hayatkurtar.domain.mesh.MeshEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.util.UUID messageId = null;
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.transport.Peer fromPeer = null;
        
        private MessageAcknowledged(java.util.UUID messageId, com.appvalence.hayatkurtar.domain.transport.Peer fromPeer) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer getFromPeer() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer component2() {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\f\u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\bJ\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u0019\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u001a"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageDelivered;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "messageId", "Lcom/appvalence/hayatkurtar/domain/mesh/MessageId;", "toPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "(Ljava/util/UUID;Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getMessageId-f2SR-2M", "()Ljava/util/UUID;", "Ljava/util/UUID;", "getToPeer", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "component1", "component1-f2SR-2M", "component2", "copy", "copy-meG8Gq0", "(Ljava/util/UUID;Lcom/appvalence/hayatkurtar/domain/transport/Peer;)Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageDelivered;", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class MessageDelivered extends com.appvalence.hayatkurtar.domain.mesh.MeshEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.util.UUID messageId = null;
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.transport.Peer toPeer = null;
        
        private MessageDelivered(java.util.UUID messageId, com.appvalence.hayatkurtar.domain.transport.Peer toPeer) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer getToPeer() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer component2() {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\f\u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\bJ\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0005H\u00d6\u0001R\u0019\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u0019"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageDropped;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "messageId", "Lcom/appvalence/hayatkurtar/domain/mesh/MessageId;", "reason", "", "(Ljava/util/UUID;Ljava/lang/String;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getMessageId-f2SR-2M", "()Ljava/util/UUID;", "Ljava/util/UUID;", "getReason", "()Ljava/lang/String;", "component1", "component1-f2SR-2M", "component2", "copy", "copy-meG8Gq0", "(Ljava/util/UUID;Ljava/lang/String;)Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageDropped;", "equals", "", "other", "", "hashCode", "", "toString", "domain_debug"})
    public static final class MessageDropped extends com.appvalence.hayatkurtar.domain.mesh.MeshEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.util.UUID messageId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String reason = null;
        
        private MessageDropped(java.util.UUID messageId, java.lang.String reason) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getReason() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\b\u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\t\u0010\u0006J\u001d\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0019\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u0015"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageExpired;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "messageId", "Lcom/appvalence/hayatkurtar/domain/mesh/MessageId;", "(Ljava/util/UUID;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getMessageId-f2SR-2M", "()Ljava/util/UUID;", "Ljava/util/UUID;", "component1", "component1-f2SR-2M", "copy", "copy-HNkFZSI", "(Ljava/util/UUID;)Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageExpired;", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class MessageExpired extends com.appvalence.hayatkurtar.domain.mesh.MeshEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.util.UUID messageId = null;
        
        private MessageExpired(java.util.UUID messageId) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0011\u0010\fJ\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0007H\u00c6\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0019\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u001e"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageForwarded;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "messageId", "Lcom/appvalence/hayatkurtar/domain/mesh/MessageId;", "toPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "hopCount", "", "(Ljava/util/UUID;Lcom/appvalence/hayatkurtar/domain/transport/Peer;ILkotlin/jvm/internal/DefaultConstructorMarker;)V", "getHopCount", "()I", "getMessageId-f2SR-2M", "()Ljava/util/UUID;", "Ljava/util/UUID;", "getToPeer", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "component1", "component1-f2SR-2M", "component2", "component3", "copy", "copy-mNZjwBM", "(Ljava/util/UUID;Lcom/appvalence/hayatkurtar/domain/transport/Peer;I)Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageForwarded;", "equals", "", "other", "", "hashCode", "toString", "", "domain_debug"})
    public static final class MessageForwarded extends com.appvalence.hayatkurtar.domain.mesh.MeshEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.util.UUID messageId = null;
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.transport.Peer toPeer = null;
        private final int hopCount = 0;
        
        private MessageForwarded(java.util.UUID messageId, com.appvalence.hayatkurtar.domain.transport.Peer toPeer, int hopCount) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer getToPeer() {
            return null;
        }
        
        public final int getHopCount() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer component2() {
            return null;
        }
        
        public final int component3() {
            return 0;
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0016"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$MessageReceived;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "message", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshMessage;", "fromPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "(Lcom/appvalence/hayatkurtar/domain/mesh/MeshMessage;Lcom/appvalence/hayatkurtar/domain/transport/Peer;)V", "getFromPeer", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "getMessage", "()Lcom/appvalence/hayatkurtar/domain/mesh/MeshMessage;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class MessageReceived extends com.appvalence.hayatkurtar.domain.mesh.MeshEvent {
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.mesh.MeshMessage message = null;
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.transport.Peer fromPeer = null;
        
        public MessageReceived(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.mesh.MeshMessage message, @org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.Peer fromPeer) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.mesh.MeshMessage getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer getFromPeer() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.mesh.MeshMessage component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.mesh.MeshEvent.MessageReceived copy(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.mesh.MeshMessage message, @org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.Peer fromPeer) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$NetworkStateChanged;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "connectedPeers", "", "knownRoutes", "(II)V", "getConnectedPeers", "()I", "getKnownRoutes", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "toString", "", "domain_debug"})
    public static final class NetworkStateChanged extends com.appvalence.hayatkurtar.domain.mesh.MeshEvent {
        private final int connectedPeers = 0;
        private final int knownRoutes = 0;
        
        public NetworkStateChanged(int connectedPeers, int knownRoutes) {
        }
        
        public final int getConnectedPeers() {
            return 0;
        }
        
        public final int getKnownRoutes() {
            return 0;
        }
        
        public final int component1() {
            return 0;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.mesh.MeshEvent.NetworkStateChanged copy(int connectedPeers, int knownRoutes) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$RouteDiscovered;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "targetId", "", "viaPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "hopCount", "", "(JLcom/appvalence/hayatkurtar/domain/transport/Peer;I)V", "getHopCount", "()I", "getTargetId", "()J", "getViaPeer", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "toString", "", "domain_debug"})
    public static final class RouteDiscovered extends com.appvalence.hayatkurtar.domain.mesh.MeshEvent {
        private final long targetId = 0L;
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.transport.Peer viaPeer = null;
        private final int hopCount = 0;
        
        public RouteDiscovered(long targetId, @org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.Peer viaPeer, int hopCount) {
        }
        
        public final long getTargetId() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer getViaPeer() {
            return null;
        }
        
        public final int getHopCount() {
            return 0;
        }
        
        public final long component1() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer component2() {
            return null;
        }
        
        public final int component3() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.mesh.MeshEvent.RouteDiscovered copy(long targetId, @org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.Peer viaPeer, int hopCount) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0016"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent$RouteLost;", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "targetId", "", "viaPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "(JLcom/appvalence/hayatkurtar/domain/transport/Peer;)V", "getTargetId", "()J", "getViaPeer", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class RouteLost extends com.appvalence.hayatkurtar.domain.mesh.MeshEvent {
        private final long targetId = 0L;
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.transport.Peer viaPeer = null;
        
        public RouteLost(long targetId, @org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.Peer viaPeer) {
        }
        
        public final long getTargetId() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer getViaPeer() {
            return null;
        }
        
        public final long component1() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.mesh.MeshEvent.RouteLost copy(long targetId, @org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.Peer viaPeer) {
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
}