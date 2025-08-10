package com.appvalence.bluetooth.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J+\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0007H&\u00a2\u0006\u0002\u0010\tJ1\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003H&\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/appvalence/bluetooth/api/DistanceEstimator;", "", "estimateDistanceMeters", "", "address", "", "rssi", "", "txPower", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Double;", "updateCalibration", "", "measuredPowerDefault", "pathLossExponent", "smoothingAlpha", "(Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/Double;)V", "bluetooth_debug"})
public abstract interface DistanceEstimator {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Double estimateDistanceMeters(@org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.Nullable()
    java.lang.Integer rssi, @org.jetbrains.annotations.Nullable()
    java.lang.Integer txPower);
    
    public abstract void updateCalibration(@org.jetbrains.annotations.Nullable()
    java.lang.Integer measuredPowerDefault, @org.jetbrains.annotations.Nullable()
    java.lang.Double pathLossExponent, @org.jetbrains.annotations.Nullable()
    java.lang.Double smoothingAlpha);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}