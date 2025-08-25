package com.appvalence.hayatkurtar.domain.system;

import kotlinx.coroutines.flow.Flow;

/**
 * Discovery status
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0011\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0006H\u00c6\u0003J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ8\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u0015J\u0013\u0010\u0016\u001a\u00020\u00032\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\fR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001b"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/system/DiscoveryStatus;", "", "isDiscovering", "", "isAdvertising", "discoveredDeviceCount", "", "lastDiscoveryTime", "", "(ZZILjava/lang/Long;)V", "getDiscoveredDeviceCount", "()I", "()Z", "getLastDiscoveryTime", "()Ljava/lang/Long;", "Ljava/lang/Long;", "component1", "component2", "component3", "component4", "copy", "(ZZILjava/lang/Long;)Lcom/appvalence/hayatkurtar/domain/system/DiscoveryStatus;", "equals", "other", "hashCode", "toString", "", "domain_debug"})
public final class DiscoveryStatus {
    private final boolean isDiscovering = false;
    private final boolean isAdvertising = false;
    private final int discoveredDeviceCount = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastDiscoveryTime = null;
    
    public DiscoveryStatus(boolean isDiscovering, boolean isAdvertising, int discoveredDeviceCount, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastDiscoveryTime) {
        super();
    }
    
    public final boolean isDiscovering() {
        return false;
    }
    
    public final boolean isAdvertising() {
        return false;
    }
    
    public final int getDiscoveredDeviceCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastDiscoveryTime() {
        return null;
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.DiscoveryStatus copy(boolean isDiscovering, boolean isAdvertising, int discoveredDeviceCount, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastDiscoveryTime) {
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