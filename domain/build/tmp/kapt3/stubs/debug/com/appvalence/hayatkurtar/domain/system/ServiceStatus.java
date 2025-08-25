package com.appvalence.hayatkurtar.domain.system;

import kotlinx.coroutines.flow.Flow;

/**
 * Service status (Bluetooth, WiFi)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00c6\u0003J1\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u00072\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u000fH\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\f\u00a8\u0006\u001e"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/system/ServiceStatus;", "", "bluetooth", "Lcom/appvalence/hayatkurtar/domain/system/ServiceState;", "wifi", "location", "allEnabled", "", "(Lcom/appvalence/hayatkurtar/domain/system/ServiceState;Lcom/appvalence/hayatkurtar/domain/system/ServiceState;Lcom/appvalence/hayatkurtar/domain/system/ServiceState;Z)V", "getAllEnabled", "()Z", "getBluetooth", "()Lcom/appvalence/hayatkurtar/domain/system/ServiceState;", "disabledServices", "", "", "getDisabledServices", "()Ljava/util/List;", "getLocation", "getWifi", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "domain_debug"})
public final class ServiceStatus {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.system.ServiceState bluetooth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.system.ServiceState wifi = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.system.ServiceState location = null;
    private final boolean allEnabled = false;
    
    public ServiceStatus(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ServiceState bluetooth, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ServiceState wifi, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ServiceState location, boolean allEnabled) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ServiceState getBluetooth() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ServiceState getWifi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ServiceState getLocation() {
        return null;
    }
    
    public final boolean getAllEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getDisabledServices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ServiceState component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ServiceState component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ServiceState component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ServiceStatus copy(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ServiceState bluetooth, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ServiceState wifi, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ServiceState location, boolean allEnabled) {
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