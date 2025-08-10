package com.appvalence.bluetooth.impl;

import com.appvalence.bluetooth.api.DistanceEstimator;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J+\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\u00072\b\u0010\r\u001a\u0004\u0018\u00010\t2\b\u0010\u000e\u001a\u0004\u0018\u00010\tH\u0016\u00a2\u0006\u0002\u0010\u000fJ+\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u00042\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0016\u00a2\u0006\u0002\u0010\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/appvalence/bluetooth/impl/RssiDistanceEstimator;", "Lcom/appvalence/bluetooth/api/DistanceEstimator;", "()V", "alpha", "", "lastDistances", "", "", "measuredPower", "", "pathLossExponent", "estimateDistanceMeters", "address", "rssi", "txPower", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Double;", "updateCalibration", "", "measuredPowerDefault", "smoothingAlpha", "(Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/Double;)V", "bluetooth_debug"})
public final class RssiDistanceEstimator implements com.appvalence.bluetooth.api.DistanceEstimator {
    @kotlin.jvm.Volatile()
    private volatile int measuredPower = -59;
    @kotlin.jvm.Volatile()
    private volatile double pathLossExponent = 2.0;
    @kotlin.jvm.Volatile()
    private volatile double alpha = 0.5;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Double> lastDistances = null;
    
    public RssiDistanceEstimator() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Double estimateDistanceMeters(@org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.Nullable()
    java.lang.Integer rssi, @org.jetbrains.annotations.Nullable()
    java.lang.Integer txPower) {
        return null;
    }
    
    @java.lang.Override()
    public void updateCalibration(@org.jetbrains.annotations.Nullable()
    java.lang.Integer measuredPowerDefault, @org.jetbrains.annotations.Nullable()
    java.lang.Double pathLossExponent, @org.jetbrains.annotations.Nullable()
    java.lang.Double smoothingAlpha) {
    }
}