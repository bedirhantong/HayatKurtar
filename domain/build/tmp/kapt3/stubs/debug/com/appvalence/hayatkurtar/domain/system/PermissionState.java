package com.appvalence.hayatkurtar.domain.system;

import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/system/PermissionState;", "", "(Ljava/lang/String;I)V", "GRANTED", "DENIED", "NOT_DETERMINED", "PERMANENTLY_DENIED", "domain_debug"})
public enum PermissionState {
    /*public static final*/ GRANTED /* = new GRANTED() */,
    /*public static final*/ DENIED /* = new DENIED() */,
    /*public static final*/ NOT_DETERMINED /* = new NOT_DETERMINED() */,
    /*public static final*/ PERMANENTLY_DENIED /* = new PERMANENTLY_DENIED() */;
    
    PermissionState() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.appvalence.hayatkurtar.domain.system.PermissionState> getEntries() {
        return null;
    }
}