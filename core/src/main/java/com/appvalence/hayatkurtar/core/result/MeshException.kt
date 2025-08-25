package com.appvalence.hayatkurtar.core.result

/**
 * Base exception class for mesh networking operations
 */
sealed class MeshException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {

    /**
     * Transport layer exceptions
     */
    sealed class Transport(message: String, cause: Throwable? = null) : MeshException(message, cause) {
        class ConnectionFailed(address: String, cause: Throwable? = null) : 
            Transport("Failed to connect to $address", cause)
        
        class SendFailed(message: String, cause: Throwable? = null) : 
            Transport("Failed to send: $message", cause)
        
        class ReceiveFailed(cause: Throwable? = null) : 
            Transport("Failed to receive data", cause)
        
        class NoTransportAvailable : Transport("No transport strategy available")
        
        class TransportNotSupported(transportName: String) : 
            Transport("Transport not supported: $transportName")
    }

    /**
     * Mesh routing exceptions
     */
    sealed class Routing(message: String, cause: Throwable? = null) : MeshException(message, cause) {
        class MessageExpired(messageId: String) : Routing("Message expired: $messageId")
        
        class TTLExceeded(messageId: String) : Routing("TTL exceeded for message: $messageId")
        
        class RouteNotFound(targetId: String) : Routing("No route found to: $targetId")
        
        class MessageTooLarge(size: Int, maxSize: Int) : 
            Routing("Message too large: $size bytes (max: $maxSize)")
    }

    /**
     * Cryptography exceptions
     */
    sealed class Crypto(message: String, cause: Throwable? = null) : MeshException(message, cause) {
        class KeyExchangeFailed(cause: Throwable? = null) : 
            Crypto("Key exchange failed", cause)
        
        class EncryptionFailed(cause: Throwable? = null) : 
            Crypto("Encryption failed", cause)
        
        class DecryptionFailed(cause: Throwable? = null) : 
            Crypto("Decryption failed", cause)
        
        class InvalidKey(keyType: String) : Crypto("Invalid $keyType key")
    }

    /**
     * Frame protocol exceptions
     */
    sealed class Protocol(message: String, cause: Throwable? = null) : MeshException(message, cause) {
        class InvalidFrame(reason: String) : Protocol("Invalid frame: $reason")
        
        class UnsupportedVersion(version: Int) : Protocol("Unsupported protocol version: $version")
        
        class CrcMismatch : Protocol("Frame CRC mismatch")
        
        class PayloadTooLarge(size: Int, maxSize: Int) : 
            Protocol("Payload too large: $size bytes (max: $maxSize)")
    }

    /**
     * Permission exceptions
     */
    sealed class Permission(message: String) : MeshException(message) {
        class BluetoothNotGranted : Permission("Bluetooth permissions not granted")
        
        class LocationNotGranted : Permission("Location permission not granted")
        
        class WiFiDirectNotGranted : Permission("Wi-Fi Direct permissions not granted")
    }

    companion object {
        /**
         * Wraps any throwable into appropriate MeshException
         */
        fun wrap(throwable: Throwable): MeshException = when (throwable) {
            is MeshException -> throwable
            is SecurityException -> Permission.BluetoothNotGranted()
            is java.net.ConnectException -> Transport.ConnectionFailed("Unknown", throwable)
            is java.io.IOException -> Transport.SendFailed("IO error", throwable)
            else -> Generic(throwable.message ?: "Unknown error", throwable)
        }
    }

    /**
     * Generic exception for wrapping unknown errors
     */
    class Generic(message: String, cause: Throwable? = null) : MeshException(message, cause)
}