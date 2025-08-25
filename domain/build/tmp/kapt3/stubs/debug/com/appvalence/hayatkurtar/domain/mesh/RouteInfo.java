package com.appvalence.hayatkurtar.domain.mesh;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.protocol.Priority;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.transport.Peer;
import kotlinx.coroutines.flow.Flow;
import java.util.UUID;

/**
 * Information about a route to a specific node
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\nH\u00c6\u0003J;\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00d6\u0001J\u0010\u0010\u001f\u001a\u00020\u001c2\b\b\u0002\u0010 \u001a\u00020\u0003J\t\u0010!\u001a\u00020\"H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f\u00a8\u0006#"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/RouteInfo;", "", "targetId", "", "nextHop", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "hopCount", "", "lastUpdated", "reliability", "", "(JLcom/appvalence/hayatkurtar/domain/transport/Peer;IJD)V", "getHopCount", "()I", "getLastUpdated", "()J", "getNextHop", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "getReliability", "()D", "getTargetId", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "isFresh", "maxAgeMs", "toString", "", "domain_debug"})
public final class RouteInfo {
    private final long targetId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.transport.Peer nextHop = null;
    private final int hopCount = 0;
    private final long lastUpdated = 0L;
    private final double reliability = 0.0;
    
    public RouteInfo(long targetId, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer nextHop, int hopCount, long lastUpdated, double reliability) {
        super();
    }
    
    public final long getTargetId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.transport.Peer getNextHop() {
        return null;
    }
    
    public final int getHopCount() {
        return 0;
    }
    
    public final long getLastUpdated() {
        return 0L;
    }
    
    public final double getReliability() {
        return 0.0;
    }
    
    /**
     * Check if route is still fresh
     */
    public final boolean isFresh(long maxAgeMs) {
        return false;
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
    
    public final long component4() {
        return 0L;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.mesh.RouteInfo copy(long targetId, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer nextHop, int hopCount, long lastUpdated, double reliability) {
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