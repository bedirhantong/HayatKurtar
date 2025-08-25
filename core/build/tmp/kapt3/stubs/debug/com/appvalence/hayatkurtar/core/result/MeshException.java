package com.appvalence.hayatkurtar.core.result;

/**
 * Base exception class for mesh networking operations
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \b2\u00060\u0001j\u0002`\u0002:\u0007\b\t\n\u000b\f\r\u000eB\u001b\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007\u0082\u0001\u0006\u000f\u0010\u0011\u0012\u0013\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "message", "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "Companion", "Crypto", "Generic", "Permission", "Protocol", "Routing", "Transport", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Generic;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Permission;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport;", "core_debug"})
public abstract class MeshException extends java.lang.Exception {
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.hayatkurtar.core.result.MeshException.Companion Companion = null;
    
    private MeshException(java.lang.String message, java.lang.Throwable cause) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Companion;", "", "()V", "wrap", "Lcom/appvalence/hayatkurtar/core/result/MeshException;", "throwable", "", "core_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Wraps any throwable into appropriate MeshException
         */
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.core.result.MeshException wrap(@org.jetbrains.annotations.NotNull()
        java.lang.Throwable throwable) {
            return null;
        }
    }
    
    /**
     * Cryptography exceptions
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0007\b\t\nB\u001b\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006\u0082\u0001\u0004\u000b\f\r\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto;", "Lcom/appvalence/hayatkurtar/core/result/MeshException;", "message", "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "DecryptionFailed", "EncryptionFailed", "InvalidKey", "KeyExchangeFailed", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto$DecryptionFailed;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto$EncryptionFailed;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto$InvalidKey;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto$KeyExchangeFailed;", "core_debug"})
    public static abstract class Crypto extends com.appvalence.hayatkurtar.core.result.MeshException {
        
        private Crypto(java.lang.String message, java.lang.Throwable cause) {
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto$DecryptionFailed;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto;", "cause", "", "(Ljava/lang/Throwable;)V", "core_debug"})
        public static final class DecryptionFailed extends com.appvalence.hayatkurtar.core.result.MeshException.Crypto {
            
            public DecryptionFailed(@org.jetbrains.annotations.Nullable()
            java.lang.Throwable cause) {
            }
            
            public DecryptionFailed() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto$EncryptionFailed;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto;", "cause", "", "(Ljava/lang/Throwable;)V", "core_debug"})
        public static final class EncryptionFailed extends com.appvalence.hayatkurtar.core.result.MeshException.Crypto {
            
            public EncryptionFailed(@org.jetbrains.annotations.Nullable()
            java.lang.Throwable cause) {
            }
            
            public EncryptionFailed() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto$InvalidKey;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto;", "keyType", "", "(Ljava/lang/String;)V", "core_debug"})
        public static final class InvalidKey extends com.appvalence.hayatkurtar.core.result.MeshException.Crypto {
            
            public InvalidKey(@org.jetbrains.annotations.NotNull()
            java.lang.String keyType) {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto$KeyExchangeFailed;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Crypto;", "cause", "", "(Ljava/lang/Throwable;)V", "core_debug"})
        public static final class KeyExchangeFailed extends com.appvalence.hayatkurtar.core.result.MeshException.Crypto {
            
            public KeyExchangeFailed(@org.jetbrains.annotations.Nullable()
            java.lang.Throwable cause) {
            }
            
            public KeyExchangeFailed() {
            }
        }
    }
    
    /**
     * Generic exception for wrapping unknown errors
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Generic;", "Lcom/appvalence/hayatkurtar/core/result/MeshException;", "message", "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "core_debug"})
    public static final class Generic extends com.appvalence.hayatkurtar.core.result.MeshException {
        
        public Generic(@org.jetbrains.annotations.NotNull()
        java.lang.String message, @org.jetbrains.annotations.Nullable()
        java.lang.Throwable cause) {
        }
    }
    
    /**
     * Permission exceptions
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0005\u0006\u0007B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u0082\u0001\u0003\b\t\n\u00a8\u0006\u000b"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Permission;", "Lcom/appvalence/hayatkurtar/core/result/MeshException;", "message", "", "(Ljava/lang/String;)V", "BluetoothNotGranted", "LocationNotGranted", "WiFiDirectNotGranted", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Permission$BluetoothNotGranted;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Permission$LocationNotGranted;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Permission$WiFiDirectNotGranted;", "core_debug"})
    public static abstract class Permission extends com.appvalence.hayatkurtar.core.result.MeshException {
        
        private Permission(java.lang.String message) {
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Permission$BluetoothNotGranted;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Permission;", "()V", "core_debug"})
        public static final class BluetoothNotGranted extends com.appvalence.hayatkurtar.core.result.MeshException.Permission {
            
            public BluetoothNotGranted() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Permission$LocationNotGranted;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Permission;", "()V", "core_debug"})
        public static final class LocationNotGranted extends com.appvalence.hayatkurtar.core.result.MeshException.Permission {
            
            public LocationNotGranted() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Permission$WiFiDirectNotGranted;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Permission;", "()V", "core_debug"})
        public static final class WiFiDirectNotGranted extends com.appvalence.hayatkurtar.core.result.MeshException.Permission {
            
            public WiFiDirectNotGranted() {
            }
        }
    }
    
    /**
     * Frame protocol exceptions
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0007\b\t\nB\u001b\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006\u0082\u0001\u0004\u000b\f\r\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol;", "Lcom/appvalence/hayatkurtar/core/result/MeshException;", "message", "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "CrcMismatch", "InvalidFrame", "PayloadTooLarge", "UnsupportedVersion", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol$CrcMismatch;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol$InvalidFrame;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol$PayloadTooLarge;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol$UnsupportedVersion;", "core_debug"})
    public static abstract class Protocol extends com.appvalence.hayatkurtar.core.result.MeshException {
        
        private Protocol(java.lang.String message, java.lang.Throwable cause) {
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol$CrcMismatch;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol;", "()V", "core_debug"})
        public static final class CrcMismatch extends com.appvalence.hayatkurtar.core.result.MeshException.Protocol {
            
            public CrcMismatch() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol$InvalidFrame;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol;", "reason", "", "(Ljava/lang/String;)V", "core_debug"})
        public static final class InvalidFrame extends com.appvalence.hayatkurtar.core.result.MeshException.Protocol {
            
            public InvalidFrame(@org.jetbrains.annotations.NotNull()
            java.lang.String reason) {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol$PayloadTooLarge;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol;", "size", "", "maxSize", "(II)V", "core_debug"})
        public static final class PayloadTooLarge extends com.appvalence.hayatkurtar.core.result.MeshException.Protocol {
            
            public PayloadTooLarge(int size, int maxSize) {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol$UnsupportedVersion;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Protocol;", "version", "", "(I)V", "core_debug"})
        public static final class UnsupportedVersion extends com.appvalence.hayatkurtar.core.result.MeshException.Protocol {
            
            public UnsupportedVersion(int version) {
            }
        }
    }
    
    /**
     * Mesh routing exceptions
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0007\b\t\nB\u001b\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006\u0082\u0001\u0004\u000b\f\r\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing;", "Lcom/appvalence/hayatkurtar/core/result/MeshException;", "message", "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "MessageExpired", "MessageTooLarge", "RouteNotFound", "TTLExceeded", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing$MessageExpired;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing$MessageTooLarge;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing$RouteNotFound;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing$TTLExceeded;", "core_debug"})
    public static abstract class Routing extends com.appvalence.hayatkurtar.core.result.MeshException {
        
        private Routing(java.lang.String message, java.lang.Throwable cause) {
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing$MessageExpired;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing;", "messageId", "", "(Ljava/lang/String;)V", "core_debug"})
        public static final class MessageExpired extends com.appvalence.hayatkurtar.core.result.MeshException.Routing {
            
            public MessageExpired(@org.jetbrains.annotations.NotNull()
            java.lang.String messageId) {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing$MessageTooLarge;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing;", "size", "", "maxSize", "(II)V", "core_debug"})
        public static final class MessageTooLarge extends com.appvalence.hayatkurtar.core.result.MeshException.Routing {
            
            public MessageTooLarge(int size, int maxSize) {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing$RouteNotFound;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing;", "targetId", "", "(Ljava/lang/String;)V", "core_debug"})
        public static final class RouteNotFound extends com.appvalence.hayatkurtar.core.result.MeshException.Routing {
            
            public RouteNotFound(@org.jetbrains.annotations.NotNull()
            java.lang.String targetId) {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing$TTLExceeded;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Routing;", "messageId", "", "(Ljava/lang/String;)V", "core_debug"})
        public static final class TTLExceeded extends com.appvalence.hayatkurtar.core.result.MeshException.Routing {
            
            public TTLExceeded(@org.jetbrains.annotations.NotNull()
            java.lang.String messageId) {
            }
        }
    }
    
    /**
     * Transport layer exceptions
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0007\b\t\n\u000bB\u001b\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006\u0082\u0001\u0005\f\r\u000e\u000f\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport;", "Lcom/appvalence/hayatkurtar/core/result/MeshException;", "message", "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "ConnectionFailed", "NoTransportAvailable", "ReceiveFailed", "SendFailed", "TransportNotSupported", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport$ConnectionFailed;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport$NoTransportAvailable;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport$ReceiveFailed;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport$SendFailed;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport$TransportNotSupported;", "core_debug"})
    public static abstract class Transport extends com.appvalence.hayatkurtar.core.result.MeshException {
        
        private Transport(java.lang.String message, java.lang.Throwable cause) {
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport$ConnectionFailed;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport;", "address", "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "core_debug"})
        public static final class ConnectionFailed extends com.appvalence.hayatkurtar.core.result.MeshException.Transport {
            
            public ConnectionFailed(@org.jetbrains.annotations.NotNull()
            java.lang.String address, @org.jetbrains.annotations.Nullable()
            java.lang.Throwable cause) {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport$NoTransportAvailable;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport;", "()V", "core_debug"})
        public static final class NoTransportAvailable extends com.appvalence.hayatkurtar.core.result.MeshException.Transport {
            
            public NoTransportAvailable() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport$ReceiveFailed;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport;", "cause", "", "(Ljava/lang/Throwable;)V", "core_debug"})
        public static final class ReceiveFailed extends com.appvalence.hayatkurtar.core.result.MeshException.Transport {
            
            public ReceiveFailed(@org.jetbrains.annotations.Nullable()
            java.lang.Throwable cause) {
            }
            
            public ReceiveFailed() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport$SendFailed;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport;", "message", "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "core_debug"})
        public static final class SendFailed extends com.appvalence.hayatkurtar.core.result.MeshException.Transport {
            
            public SendFailed(@org.jetbrains.annotations.NotNull()
            java.lang.String message, @org.jetbrains.annotations.Nullable()
            java.lang.Throwable cause) {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport$TransportNotSupported;", "Lcom/appvalence/hayatkurtar/core/result/MeshException$Transport;", "transportName", "", "(Ljava/lang/String;)V", "core_debug"})
        public static final class TransportNotSupported extends com.appvalence.hayatkurtar.core.result.MeshException.Transport {
            
            public TransportNotSupported(@org.jetbrains.annotations.NotNull()
            java.lang.String transportName) {
            }
        }
    }
}