package com.appvalence.hayatkurtar.domain.permissions;

/**
 * Represents specific permissions required for mesh networking functionality
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\b\u0086\u0081\u0002\u0018\u0000 \u00182\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0018B#\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017\u00a8\u0006\u0019"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "", "description", "", "isRequired", "", "minSdkVersion", "", "(Ljava/lang/String;ILjava/lang/String;ZI)V", "getDescription", "()Ljava/lang/String;", "()Z", "getMinSdkVersion", "()I", "BLUETOOTH_CONNECT", "BLUETOOTH_SCAN", "BLUETOOTH_ADVERTISE", "BLUETOOTH_LEGACY", "BLUETOOTH_ADMIN_LEGACY", "ACCESS_WIFI_STATE", "CHANGE_WIFI_STATE", "NEARBY_WIFI_DEVICES", "ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION", "Companion", "domain_debug"})
public enum MeshPermission {
    /*public static final*/ BLUETOOTH_CONNECT /* = new BLUETOOTH_CONNECT(null, false, 0) */,
    /*public static final*/ BLUETOOTH_SCAN /* = new BLUETOOTH_SCAN(null, false, 0) */,
    /*public static final*/ BLUETOOTH_ADVERTISE /* = new BLUETOOTH_ADVERTISE(null, false, 0) */,
    /*public static final*/ BLUETOOTH_LEGACY /* = new BLUETOOTH_LEGACY(null, false, 0) */,
    /*public static final*/ BLUETOOTH_ADMIN_LEGACY /* = new BLUETOOTH_ADMIN_LEGACY(null, false, 0) */,
    /*public static final*/ ACCESS_WIFI_STATE /* = new ACCESS_WIFI_STATE(null, false, 0) */,
    /*public static final*/ CHANGE_WIFI_STATE /* = new CHANGE_WIFI_STATE(null, false, 0) */,
    /*public static final*/ NEARBY_WIFI_DEVICES /* = new NEARBY_WIFI_DEVICES(null, false, 0) */,
    /*public static final*/ ACCESS_FINE_LOCATION /* = new ACCESS_FINE_LOCATION(null, false, 0) */,
    /*public static final*/ ACCESS_COARSE_LOCATION /* = new ACCESS_COARSE_LOCATION(null, false, 0) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    private final boolean isRequired = false;
    private final int minSdkVersion = 0;
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.hayatkurtar.domain.permissions.MeshPermission.Companion Companion = null;
    
    MeshPermission(java.lang.String description, boolean isRequired, int minSdkVersion) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final boolean isRequired() {
        return false;
    }
    
    public final int getMinSdkVersion() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007\u00a8\u0006\b"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission$Companion;", "", "()V", "getRequiredPermissions", "", "Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "sdkVersion", "", "domain_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Get permissions required for current SDK version
         */
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> getRequiredPermissions(int sdkVersion) {
            return null;
        }
    }
}