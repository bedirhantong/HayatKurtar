package com.appvalence.bluetooth.api;

import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0014\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\t"}, d2 = {"Lcom/appvalence/bluetooth/api/BleAdvertiser;", "", "isAdvertising", "", "start", "serviceUuid", "Ljava/util/UUID;", "stop", "", "bluetooth_debug"})
public abstract interface BleAdvertiser {
    
    public abstract boolean start(@org.jetbrains.annotations.Nullable()
    java.util.UUID serviceUuid);
    
    public abstract void stop();
    
    public abstract boolean isAdvertising();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}