package com.appvalence.hayatkurtar.domain.system;

import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/system/ErrorType;", "", "(Ljava/lang/String;I)V", "PERMISSION_ERROR", "SERVICE_ERROR", "HARDWARE_ERROR", "NETWORK_ERROR", "UNKNOWN_ERROR", "domain_debug"})
public enum ErrorType {
    /*public static final*/ PERMISSION_ERROR /* = new PERMISSION_ERROR() */,
    /*public static final*/ SERVICE_ERROR /* = new SERVICE_ERROR() */,
    /*public static final*/ HARDWARE_ERROR /* = new HARDWARE_ERROR() */,
    /*public static final*/ NETWORK_ERROR /* = new NETWORK_ERROR() */,
    /*public static final*/ UNKNOWN_ERROR /* = new UNKNOWN_ERROR() */;
    
    ErrorType() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.appvalence.hayatkurtar.domain.system.ErrorType> getEntries() {
        return null;
    }
}