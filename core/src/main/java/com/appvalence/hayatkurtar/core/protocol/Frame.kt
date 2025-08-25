package com.appvalence.hayatkurtar.core.protocol

import com.appvalence.hayatkurtar.core.result.MeshException
import com.appvalence.hayatkurtar.core.result.MeshResult
import com.appvalence.hayatkurtar.core.result.meshTry
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.UUID
import java.util.zip.CRC32

/**
 * Frame types for mesh protocol
 */
enum class FrameType(val value: Byte) {
    HELLO(0),           // Initial handshake
    CHAT(1),            // Chat message
    ACK(2),             // Acknowledgment
    PING(3),            // Keep-alive ping
    PONG(4),            // Ping response
    KEY_EXCHANGE(5);    // Cryptographic key exchange

    companion object {
        fun fromByte(value: Byte): FrameType? = values().find { it.value == value }
    }
}

/**
 * Message priority levels
 */
enum class Priority(val value: Byte) {
    LOW(0),
    NORMAL(1),
    HIGH(2);  // SOS/Emergency

    companion object {
        fun fromByte(value: Byte): Priority? = values().find { it.value == value }
    }
}

/**
 * Binary frame structure for mesh communication
 * 
 * Frame Format (binary):
 * 1 byte  : version (0x01)
 * 1 byte  : type (0=HELLO,1=CHAT,2=ACK,3=PING,4=PONG,5=KEY_EXCHANGE)
 * 16 bytes: messageId (UUID v4)
 * 8 bytes : originId (hash(devicePublicKey) first 8 bytes)
 * 1 byte  : ttl (recommended: 8)
 * 1 byte  : hopCount
 * 1 byte  : priority (0=LOW,1=NORMAL,2=HIGH)
 * 8 bytes : timestamp (epoch millis)
 * 2 bytes : payloadLen (<= 512 recommended)
 * N bytes : payload (UTF-8 chat text etc.)
 * 4 bytes : crc32 (frame integrity check)
 */
data class Frame(
    val version: Byte = PROTOCOL_VERSION,
    val type: FrameType,
    val messageId: UUID,
    val originId: Long,           // 8-byte device identifier
    val ttl: Byte,
    val hopCount: Byte = 0,
    val priority: Priority = Priority.NORMAL,
    val timestamp: Long,
    val payload: ByteArray,
    val crc32: Int = 0
) {
    companion object {
        const val PROTOCOL_VERSION: Byte = 0x01
        const val HEADER_SIZE = 1 + 1 + 16 + 8 + 1 + 1 + 1 + 8 + 2  // 39 bytes
        const val CRC_SIZE = 4
        const val MAX_PAYLOAD_SIZE = 512
        const val MAX_FRAME_SIZE = HEADER_SIZE + MAX_PAYLOAD_SIZE + CRC_SIZE
        const val DEFAULT_TTL: Byte = 8
    }

    /**
     * Calculate and set CRC32 for this frame
     */
    fun withCrc(): Frame {
        val frameBytes = toByteArrayWithoutCrc()
        val crc = CRC32()
        crc.update(frameBytes)
        return copy(crc32 = crc.value.toInt())
    }

    /**
     * Convert frame to byte array without CRC
     */
    private fun toByteArrayWithoutCrc(): ByteArray {
        val buffer = ByteBuffer.allocate(HEADER_SIZE + payload.size)
            .order(ByteOrder.BIG_ENDIAN)
        
        // Header
        buffer.put(version)
        buffer.put(type.value)
        
        // UUID as 16 bytes
        buffer.putLong(messageId.mostSignificantBits)
        buffer.putLong(messageId.leastSignificantBits)
        
        buffer.putLong(originId)
        buffer.put(ttl)
        buffer.put(hopCount)
        buffer.put(priority.value)
        buffer.putLong(timestamp)
        buffer.putShort(payload.size.toShort())
        
        // Payload
        buffer.put(payload)
        
        return buffer.array()
    }

    /**
     * Convert frame to complete byte array with CRC
     */
    fun toByteArray(): ByteArray {
        val frameWithCrc = withCrc()
        val buffer = ByteBuffer.allocate(HEADER_SIZE + payload.size + CRC_SIZE)
            .order(ByteOrder.BIG_ENDIAN)
        
        buffer.put(frameWithCrc.toByteArrayWithoutCrc())
        buffer.putInt(frameWithCrc.crc32)
        
        return buffer.array()
    }

    /**
     * Create frame with incremented hop count and decremented TTL
     */
    fun forward(): Frame? {
        return if (ttl > 0) {
            copy(hopCount = (hopCount + 1).toByte(), ttl = (ttl - 1).toByte())
        } else {
            null  // TTL expired
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Frame

        return messageId == other.messageId
    }

    override fun hashCode(): Int {
        return messageId.hashCode()
    }
}

/**
 * Frame codec for serialization/deserialization
 */
object FrameCodec {

    /**
     * Parse frame from byte array
     */
    fun decode(bytes: ByteArray): MeshResult<Frame> = meshTry {
        if (bytes.size < Frame.HEADER_SIZE + Frame.CRC_SIZE) {
            throw MeshException.Protocol.InvalidFrame("Frame too small: ${bytes.size} bytes")
        }

        val buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN)
        
        // Parse header
        val version = buffer.get()
        if (version != Frame.PROTOCOL_VERSION) {
            throw MeshException.Protocol.UnsupportedVersion(version.toInt())
        }

        val type = FrameType.fromByte(buffer.get()) 
            ?: throw MeshException.Protocol.InvalidFrame("Unknown frame type")

        val messageId = UUID(buffer.long, buffer.long)
        val originId = buffer.long
        val ttl = buffer.get()
        val hopCount = buffer.get()
        val priority = Priority.fromByte(buffer.get()) 
            ?: throw MeshException.Protocol.InvalidFrame("Unknown priority")
        val timestamp = buffer.long
        val payloadLen = buffer.short.toInt() and 0xFFFF

        if (payloadLen > Frame.MAX_PAYLOAD_SIZE) {
            throw MeshException.Protocol.PayloadTooLarge(payloadLen, Frame.MAX_PAYLOAD_SIZE)
        }

        // Validate total frame size
        val expectedSize = Frame.HEADER_SIZE + payloadLen + Frame.CRC_SIZE
        if (bytes.size != expectedSize) {
            throw MeshException.Protocol.InvalidFrame(
                "Size mismatch: expected $expectedSize, got ${bytes.size}"
            )
        }

        // Parse payload
        val payload = ByteArray(payloadLen)
        buffer.get(payload)

        // Parse and verify CRC
        val crc32 = buffer.int
        
        val frame = Frame(
            version = version,
            type = type,
            messageId = messageId,
            originId = originId,
            ttl = ttl,
            hopCount = hopCount,
            priority = priority,
            timestamp = timestamp,
            payload = payload,
            crc32 = crc32
        )

        // Verify CRC
        val expectedCrc = frame.copy(crc32 = 0).withCrc().crc32
        if (crc32 != expectedCrc) {
            throw MeshException.Protocol.CrcMismatch()
        }

        frame
    }

    /**
     * Encode frame to byte array
     */
    fun encode(frame: Frame): MeshResult<ByteArray> = meshTry {
        if (frame.payload.size > Frame.MAX_PAYLOAD_SIZE) {
            throw MeshException.Protocol.PayloadTooLarge(
                frame.payload.size, 
                Frame.MAX_PAYLOAD_SIZE
            )
        }
        
        frame.toByteArray()
    }
}

/**
 * Builder for creating frames
 */
class FrameBuilder {
    private var type: FrameType = FrameType.CHAT
    private var messageId: UUID = UUID.randomUUID()
    private var originId: Long = 0L
    private var ttl: Byte = Frame.DEFAULT_TTL
    private var priority: Priority = Priority.NORMAL
    private var timestamp: Long = System.currentTimeMillis()
    private var payload: ByteArray = ByteArray(0)

    fun type(type: FrameType) = apply { this.type = type }
    fun messageId(messageId: UUID) = apply { this.messageId = messageId }
    fun originId(originId: Long) = apply { this.originId = originId }
    fun ttl(ttl: Byte) = apply { this.ttl = ttl }
    fun priority(priority: Priority) = apply { this.priority = priority }
    fun timestamp(timestamp: Long) = apply { this.timestamp = timestamp }
    fun payload(payload: ByteArray) = apply { this.payload = payload }
    fun payload(text: String) = apply { this.payload = text.toByteArray(Charsets.UTF_8) }

    fun build(): Frame = Frame(
        type = type,
        messageId = messageId,
        originId = originId,
        ttl = ttl,
        priority = priority,
        timestamp = timestamp,
        payload = payload
    )
}