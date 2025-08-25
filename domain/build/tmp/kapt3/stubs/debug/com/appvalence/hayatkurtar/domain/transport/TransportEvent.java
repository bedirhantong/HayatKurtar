package com.appvalence.hayatkurtar.domain.transport;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import kotlinx.coroutines.flow.Flow;

/**
 * Events from transport layer
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0007\u0003\u0004\u0005\u0006\u0007\b\tB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0007\n\u000b\f\r\u000e\u000f\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "", "()V", "Connected", "Disconnected", "Error", "FrameReceived", "LinkQualityChanged", "PeerDiscovered", "PeerLost", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$Connected;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$Disconnected;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$Error;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$FrameReceived;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$LinkQualityChanged;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$PeerDiscovered;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$PeerLost;", "domain_debug"})
public abstract class TransportEvent {
    
    private TransportEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$Connected;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "link", "Lcom/appvalence/hayatkurtar/domain/transport/Link;", "(Lcom/appvalence/hayatkurtar/domain/transport/Link;)V", "getLink", "()Lcom/appvalence/hayatkurtar/domain/transport/Link;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class Connected extends com.appvalence.hayatkurtar.domain.transport.TransportEvent {
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.transport.Link link = null;
        
        public Connected(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.Link link) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Link getLink() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Link component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.TransportEvent.Connected copy(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.Link link) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$Disconnected;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "peerId", "", "reason", "(Ljava/lang/String;Ljava/lang/String;)V", "getPeerId", "()Ljava/lang/String;", "getReason", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "domain_debug"})
    public static final class Disconnected extends com.appvalence.hayatkurtar.domain.transport.TransportEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String peerId = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String reason = null;
        
        public Disconnected(@org.jetbrains.annotations.NotNull()
        java.lang.String peerId, @org.jetbrains.annotations.Nullable()
        java.lang.String reason) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getPeerId() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getReason() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.TransportEvent.Disconnected copy(@org.jetbrains.annotations.NotNull()
        java.lang.String peerId, @org.jetbrains.annotations.Nullable()
        java.lang.String reason) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\r\u0010\f\u001a\u00060\u0003j\u0002`\u0004H\u00c6\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u00060\u0003j\u0002`\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0006H\u00d6\u0001R\u0015\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$Error;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "error", "Ljava/lang/Exception;", "Lkotlin/Exception;", "peerId", "", "(Ljava/lang/Exception;Ljava/lang/String;)V", "getError", "()Ljava/lang/Exception;", "getPeerId", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "domain_debug"})
    public static final class Error extends com.appvalence.hayatkurtar.domain.transport.TransportEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.Exception error = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String peerId = null;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.Exception error, @org.jetbrains.annotations.Nullable()
        java.lang.String peerId) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.Exception getError() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getPeerId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.Exception component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.TransportEvent.Error copy(@org.jetbrains.annotations.NotNull()
        java.lang.Exception error, @org.jetbrains.annotations.Nullable()
        java.lang.String peerId) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0016"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$FrameReceived;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "frame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "fromPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "(Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lcom/appvalence/hayatkurtar/domain/transport/Peer;)V", "getFrame", "()Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "getFromPeer", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class FrameReceived extends com.appvalence.hayatkurtar.domain.transport.TransportEvent {
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.core.protocol.Frame frame = null;
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.transport.Peer fromPeer = null;
        
        public FrameReceived(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.Peer fromPeer) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.core.protocol.Frame getFrame() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer getFromPeer() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.core.protocol.Frame component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.TransportEvent.FrameReceived copy(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$LinkQualityChanged;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "peerId", "", "quality", "Lcom/appvalence/hayatkurtar/domain/transport/LinkQuality;", "(Ljava/lang/String;Lcom/appvalence/hayatkurtar/domain/transport/LinkQuality;)V", "getPeerId", "()Ljava/lang/String;", "getQuality", "()Lcom/appvalence/hayatkurtar/domain/transport/LinkQuality;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "domain_debug"})
    public static final class LinkQualityChanged extends com.appvalence.hayatkurtar.domain.transport.TransportEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String peerId = null;
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.transport.LinkQuality quality = null;
        
        public LinkQualityChanged(@org.jetbrains.annotations.NotNull()
        java.lang.String peerId, @org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.LinkQuality quality) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getPeerId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.LinkQuality getQuality() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.LinkQuality component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.TransportEvent.LinkQualityChanged copy(@org.jetbrains.annotations.NotNull()
        java.lang.String peerId, @org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.LinkQuality quality) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$PeerDiscovered;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "peer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;)V", "getPeer", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class PeerDiscovered extends com.appvalence.hayatkurtar.domain.transport.TransportEvent {
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.transport.Peer peer = null;
        
        public PeerDiscovered(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.Peer peer) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer getPeer() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.Peer component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.TransportEvent.PeerDiscovered copy(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.transport.Peer peer) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent$PeerLost;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "peerId", "", "(Ljava/lang/String;)V", "getPeerId", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "domain_debug"})
    public static final class PeerLost extends com.appvalence.hayatkurtar.domain.transport.TransportEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String peerId = null;
        
        public PeerLost(@org.jetbrains.annotations.NotNull()
        java.lang.String peerId) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getPeerId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.transport.TransportEvent.PeerLost copy(@org.jetbrains.annotations.NotNull()
        java.lang.String peerId) {
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