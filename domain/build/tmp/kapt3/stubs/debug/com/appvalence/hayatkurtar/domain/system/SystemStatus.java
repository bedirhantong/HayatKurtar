package com.appvalence.hayatkurtar.domain.system;

import kotlinx.coroutines.flow.Flow;

/**
 * Comprehensive system status
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\tH\u00c6\u0003J\t\u0010 \u001a\u00020\u000bH\u00c6\u0003J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u00c6\u0003JK\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u00c6\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020'H\u00d6\u0001J\t\u0010(\u001a\u00020)H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006*"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/system/SystemStatus;", "", "overallState", "Lcom/appvalence/hayatkurtar/domain/system/SystemState;", "permissions", "Lcom/appvalence/hayatkurtar/domain/system/PermissionStatus;", "services", "Lcom/appvalence/hayatkurtar/domain/system/ServiceStatus;", "connectivity", "Lcom/appvalence/hayatkurtar/domain/system/ConnectivityStatus;", "discovery", "Lcom/appvalence/hayatkurtar/domain/system/DiscoveryStatus;", "errors", "", "Lcom/appvalence/hayatkurtar/domain/system/SystemError;", "(Lcom/appvalence/hayatkurtar/domain/system/SystemState;Lcom/appvalence/hayatkurtar/domain/system/PermissionStatus;Lcom/appvalence/hayatkurtar/domain/system/ServiceStatus;Lcom/appvalence/hayatkurtar/domain/system/ConnectivityStatus;Lcom/appvalence/hayatkurtar/domain/system/DiscoveryStatus;Ljava/util/List;)V", "getConnectivity", "()Lcom/appvalence/hayatkurtar/domain/system/ConnectivityStatus;", "getDiscovery", "()Lcom/appvalence/hayatkurtar/domain/system/DiscoveryStatus;", "getErrors", "()Ljava/util/List;", "getOverallState", "()Lcom/appvalence/hayatkurtar/domain/system/SystemState;", "getPermissions", "()Lcom/appvalence/hayatkurtar/domain/system/PermissionStatus;", "getServices", "()Lcom/appvalence/hayatkurtar/domain/system/ServiceStatus;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "", "domain_debug"})
public final class SystemStatus {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.system.SystemState overallState = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.system.PermissionStatus permissions = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.system.ServiceStatus services = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.system.ConnectivityStatus connectivity = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.system.DiscoveryStatus discovery = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.appvalence.hayatkurtar.domain.system.SystemError> errors = null;
    
    public SystemStatus(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.SystemState overallState, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.PermissionStatus permissions, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ServiceStatus services, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ConnectivityStatus connectivity, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.DiscoveryStatus discovery, @org.jetbrains.annotations.NotNull()
    java.util.List<com.appvalence.hayatkurtar.domain.system.SystemError> errors) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.SystemState getOverallState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.PermissionStatus getPermissions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ServiceStatus getServices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ConnectivityStatus getConnectivity() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.DiscoveryStatus getDiscovery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.appvalence.hayatkurtar.domain.system.SystemError> getErrors() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.SystemState component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.PermissionStatus component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ServiceStatus component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ConnectivityStatus component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.DiscoveryStatus component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.appvalence.hayatkurtar.domain.system.SystemError> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.SystemStatus copy(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.SystemState overallState, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.PermissionStatus permissions, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ServiceStatus services, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ConnectivityStatus connectivity, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.DiscoveryStatus discovery, @org.jetbrains.annotations.NotNull()
    java.util.List<com.appvalence.hayatkurtar.domain.system.SystemError> errors) {
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