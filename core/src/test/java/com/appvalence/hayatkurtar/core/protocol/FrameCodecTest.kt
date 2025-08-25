package com.appvalence.hayatkurtar.core.protocol

import org.junit.Test
import org.junit.Assert.*
import java.util.UUID

class FrameCodecTest {

    @Test
    fun `encode and decode frame successfully`() {
        // Given
        val originalFrame = FrameBuilder()
            .type(FrameType.CHAT)
            .messageId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
            .originId(12345L)
            .ttl(8)
            .priority(Priority.HIGH)
            .payload("Hello, World!")
            .build()

        // When
        val encodedResult = FrameCodec.encode(originalFrame)
        assertTrue("Encoding should succeed", encodedResult.isSuccess())
        
        val encodedBytes = encodedResult.getOrThrow()
        val decodedResult = FrameCodec.decode(encodedBytes)
        assertTrue("Decoding should succeed", decodedResult.isSuccess())
        
        val decodedFrame = decodedResult.getOrThrow()

        // Then
        assertEquals(originalFrame.version, decodedFrame.version)
        assertEquals(originalFrame.type, decodedFrame.type)
        assertEquals(originalFrame.messageId, decodedFrame.messageId)
        assertEquals(originalFrame.originId, decodedFrame.originId)
        assertEquals(originalFrame.ttl, decodedFrame.ttl)
        assertEquals(originalFrame.priority, decodedFrame.priority)
        assertEquals("Hello, World!", String(decodedFrame.payload, Charsets.UTF_8))
    }

    @Test
    fun `decode invalid frame with wrong version`() {
        // Given
        val frame = FrameBuilder()
            .type(FrameType.PING)
            .payload("test")
            .build()
            .copy(version = 0x99.toByte()) // Invalid version

        val frameBytes = FrameCodec.encode(frame).getOrThrow()
        
        // Manually modify version byte
        frameBytes[0] = 0x99.toByte()
        
        // Recalculate CRC
        val frameWithoutCrc = frameBytes.copyOfRange(0, frameBytes.size - 4)
        val crc = java.util.zip.CRC32()
        crc.update(frameWithoutCrc)
        val crcBytes = ByteArray(4)
        crcBytes[0] = (crc.value shr 24).toByte()
        crcBytes[1] = (crc.value shr 16).toByte() 
        crcBytes[2] = (crc.value shr 8).toByte()
        crcBytes[3] = crc.value.toByte()
        System.arraycopy(crcBytes, 0, frameBytes, frameBytes.size - 4, 4)

        // When
        val result = FrameCodec.decode(frameBytes)

        // Then
        assertTrue("Decoding should fail", result.isError())
        assertTrue("Should be unsupported version error", 
                   result.errorOrNull() is com.appvalence.hayatkurtar.core.result.MeshException.Protocol.UnsupportedVersion)
    }

    @Test
    fun `decode frame with CRC mismatch`() {
        // Given
        val frame = FrameBuilder()
            .type(FrameType.ACK)
            .payload("test")
            .build()

        val frameBytes = FrameCodec.encode(frame).getOrThrow()
        
        // Corrupt the last CRC byte
        frameBytes[frameBytes.size - 1] = (frameBytes[frameBytes.size - 1] + 1).toByte()

        // When
        val result = FrameCodec.decode(frameBytes)

        // Then
        assertTrue("Decoding should fail", result.isError())
        assertTrue("Should be CRC mismatch error", 
                   result.errorOrNull() is com.appvalence.hayatkurtar.core.result.MeshException.Protocol.CrcMismatch)
    }

    @Test
    fun `encode frame with oversized payload fails`() {
        // Given
        val largePayload = ByteArray(Frame.MAX_PAYLOAD_SIZE + 1) { 0x41 } // 'A' repeated
        val frame = FrameBuilder()
            .type(FrameType.CHAT)
            .payload(largePayload)
            .build()

        // When
        val result = FrameCodec.encode(frame)

        // Then
        assertTrue("Encoding should fail", result.isError())
        assertTrue("Should be payload too large error", 
                   result.errorOrNull() is com.appvalence.hayatkurtar.core.result.MeshException.Protocol.PayloadTooLarge)
    }

    @Test
    fun `decode frame with invalid frame size`() {
        // Given
        val tooSmallFrame = ByteArray(10) // Much smaller than minimum frame size

        // When
        val result = FrameCodec.decode(tooSmallFrame)

        // Then
        assertTrue("Decoding should fail", result.isError())
        assertTrue("Should be invalid frame error", 
                   result.errorOrNull() is com.appvalence.hayatkurtar.core.result.MeshException.Protocol.InvalidFrame)
    }

    @Test
    fun `frame forward decrements TTL and increments hop count`() {
        // Given
        val frame = FrameBuilder()
            .type(FrameType.CHAT)
            .ttl(5)
            .payload("test")
            .build()

        // When
        val forwardedFrame = frame.forward()

        // Then
        assertNotNull("Forwarded frame should not be null", forwardedFrame)
        assertEquals("TTL should be decremented", 4, forwardedFrame!!.ttl.toInt())
        assertEquals("Hop count should be incremented", 1, forwardedFrame.hopCount.toInt())
    }

    @Test
    fun `frame forward with TTL 0 returns null`() {
        // Given
        val frame = FrameBuilder()
            .type(FrameType.CHAT)
            .ttl(0)
            .payload("test")
            .build()

        // When
        val forwardedFrame = frame.forward()

        // Then
        assertNull("Forwarded frame should be null when TTL is 0", forwardedFrame)
    }

    @Test
    fun `frame with CRC verification succeeds`() {
        // Given
        val frame = FrameBuilder()
            .type(FrameType.HELLO)
            .payload("Hello")
            .build()

        // When
        val frameWithCrc = frame.withCrc()
        val encodedBytes = frameWithCrc.toByteArray()
        val decodedResult = FrameCodec.decode(encodedBytes)

        // Then
        assertTrue("Frame with CRC should decode successfully", decodedResult.isSuccess())
        val decodedFrame = decodedResult.getOrThrow()
        assertEquals("Payload should match", "Hello", String(decodedFrame.payload, Charsets.UTF_8))
    }

    @Test
    fun `all frame types can be encoded and decoded`() {
        FrameType.values().forEach { frameType ->
            // Given
            val frame = FrameBuilder()
                .type(frameType)
                .payload("test_${frameType.name}")
                .build()

            // When
            val encodedResult = FrameCodec.encode(frame)
            assertTrue("Encoding $frameType should succeed", encodedResult.isSuccess())
            
            val decodedResult = FrameCodec.decode(encodedResult.getOrThrow())
            assertTrue("Decoding $frameType should succeed", decodedResult.isSuccess())
            
            // Then
            val decodedFrame = decodedResult.getOrThrow()
            assertEquals("Frame type should match", frameType, decodedFrame.type)
            assertEquals("Payload should match", "test_${frameType.name}", 
                        String(decodedFrame.payload, Charsets.UTF_8))
        }
    }

    @Test
    fun `all priority levels can be encoded and decoded`() {
        Priority.values().forEach { priority ->
            // Given
            val frame = FrameBuilder()
                .type(FrameType.CHAT)
                .priority(priority)
                .payload("test_${priority.name}")
                .build()

            // When
            val encodedResult = FrameCodec.encode(frame)
            assertTrue("Encoding priority $priority should succeed", encodedResult.isSuccess())
            
            val decodedResult = FrameCodec.decode(encodedResult.getOrThrow())
            assertTrue("Decoding priority $priority should succeed", decodedResult.isSuccess())
            
            // Then
            val decodedFrame = decodedResult.getOrThrow()
            assertEquals("Priority should match", priority, decodedFrame.priority)
        }
    }

    @Test
    fun `frame builder creates valid frames`() {
        // Given & When
        val frame = FrameBuilder()
            .type(FrameType.CHAT)
            .originId(999L)
            .ttl(10)
            .priority(Priority.HIGH)
            .payload("Builder test")
            .build()

        // Then
        assertEquals(Frame.PROTOCOL_VERSION, frame.version)
        assertEquals(FrameType.CHAT, frame.type)
        assertEquals(999L, frame.originId)
        assertEquals(10, frame.ttl.toInt())
        assertEquals(Priority.HIGH, frame.priority)
        assertEquals("Builder test", String(frame.payload, Charsets.UTF_8))
        assertTrue("Message ID should be valid UUID", frame.messageId.toString().isNotEmpty())
        assertTrue("Timestamp should be recent", 
                  Math.abs(System.currentTimeMillis() - frame.timestamp) < 1000)
    }
}