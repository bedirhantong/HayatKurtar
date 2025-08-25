package com.appvalence.hayatkurtar.domain.system;

import kotlinx.coroutines.flow.Flow;

/**
 * Overall system state enum
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/system/SystemState;", "", "(Ljava/lang/String;I)V", "READY", "NEEDS_PERMISSIONS", "NEEDS_SERVICES", "DISCOVERING", "ERROR", "domain_debug"})
public enum SystemState {
    /*public static final*/ READY /* = new READY() */,
    /*public static final*/ NEEDS_PERMISSIONS /* = new NEEDS_PERMISSIONS() */,
    /*public static final*/ NEEDS_SERVICES /* = new NEEDS_SERVICES() */,
    /*public static final*/ DISCOVERING /* = new DISCOVERING() */,
    /*public static final*/ ERROR /* = new ERROR() */;
    
    SystemState() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.appvalence.hayatkurtar.domain.system.SystemState> getEntries() {
        return null;
    }
}