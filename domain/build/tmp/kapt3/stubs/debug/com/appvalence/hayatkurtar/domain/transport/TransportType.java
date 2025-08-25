package com.appvalence.hayatkurtar.domain.transport;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import kotlinx.coroutines.flow.Flow;

/**
 * Types of transport mechanisms
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "", "(Ljava/lang/String;I)V", "BLUETOOTH_CLASSIC", "WIFI_DIRECT", "UNKNOWN", "domain_debug"})
public enum TransportType {
    /*public static final*/ BLUETOOTH_CLASSIC /* = new BLUETOOTH_CLASSIC() */,
    /*public static final*/ WIFI_DIRECT /* = new WIFI_DIRECT() */,
    /*public static final*/ UNKNOWN /* = new UNKNOWN() */;
    
    TransportType() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.appvalence.hayatkurtar.domain.transport.TransportType> getEntries() {
        return null;
    }
}