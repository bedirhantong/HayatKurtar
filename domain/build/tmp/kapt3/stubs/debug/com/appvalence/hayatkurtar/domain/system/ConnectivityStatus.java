package com.appvalence.hayatkurtar.domain.system;

import kotlinx.coroutines.flow.Flow;

/**
 * Connectivity and mesh status
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\bH\u00c6\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0015\u001a\u00020\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000b\u00a8\u0006\u001a"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/system/ConnectivityStatus;", "", "connectedPeers", "", "connectionQuality", "Lcom/appvalence/hayatkurtar/domain/system/ConnectionQuality;", "meshNetworkSize", "isConnectedToMesh", "", "(ILcom/appvalence/hayatkurtar/domain/system/ConnectionQuality;IZ)V", "getConnectedPeers", "()I", "getConnectionQuality", "()Lcom/appvalence/hayatkurtar/domain/system/ConnectionQuality;", "()Z", "getMeshNetworkSize", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "toString", "", "domain_debug"})
public final class ConnectivityStatus {
    private final int connectedPeers = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.system.ConnectionQuality connectionQuality = null;
    private final int meshNetworkSize = 0;
    private final boolean isConnectedToMesh = false;
    
    public ConnectivityStatus(int connectedPeers, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ConnectionQuality connectionQuality, int meshNetworkSize, boolean isConnectedToMesh) {
        super();
    }
    
    public final int getConnectedPeers() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ConnectionQuality getConnectionQuality() {
        return null;
    }
    
    public final int getMeshNetworkSize() {
        return 0;
    }
    
    public final boolean isConnectedToMesh() {
        return false;
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ConnectionQuality component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ConnectivityStatus copy(int connectedPeers, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ConnectionQuality connectionQuality, int meshNetworkSize, boolean isConnectedToMesh) {
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