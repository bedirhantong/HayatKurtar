package com.appvalence.hayatkurtar.data.mesh.router

import com.appvalence.hayatkurtar.core.protocol.Frame
import com.appvalence.hayatkurtar.core.protocol.FrameType
import com.appvalence.hayatkurtar.core.protocol.Priority
import com.appvalence.hayatkurtar.data.mesh.store.RoomMessageStore
import com.appvalence.hayatkurtar.domain.mesh.MessageId
import com.appvalence.hayatkurtar.domain.mesh.MeshEvent
import com.appvalence.hayatkurtar.domain.transport.*
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import java.util.UUID

@ExperimentalCoroutinesApi
class DefaultMeshRouterTest {

    private lateinit var meshRouter: DefaultMeshRouter
    private lateinit var mockTransportMultiplexer: TransportMultiplexer
    private lateinit var mockMessageStore: RoomMessageStore
    private lateinit var mockDeviceIdProvider: DeviceIdProvider

    private val testDeviceId = 12345L
    private val testPeer = Peer(
        id = "test-peer-001",
        name = "Test Peer",
        transport = TransportType.BLUETOOTH_CLASSIC,
        linkQuality = LinkQuality.GOOD
    )

    @Before
    fun setup() {
        mockTransportMultiplexer = mockk(relaxed = true)
        mockMessageStore = mockk(relaxed = true)
        mockDeviceIdProvider = mockk()
        
        every { mockDeviceIdProvider.getDeviceId() } returns testDeviceId
        
        // Mock transport events flow
        every { mockTransportMultiplexer.transportEvents } returns flowOf()
        
        meshRouter = DefaultMeshRouter(
            transportMultiplexer = mockTransportMultiplexer,
            messageStore = mockMessageStore,
            deviceIdProvider = mockDeviceIdProvider
        )
    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `send message creates and broadcasts frame`() = runTest {
        // Given
        val messageContent = "Hello, mesh network!"
        coEvery { mockMessageStore.storeOutgoingMessage(any(), any()) } just Runs
        coEvery { mockTransportMultiplexer.broadcast(any()) } returns com.appvalence.hayatkurtar.core.result.MeshResult.Success(Unit)
        
        // When
        meshRouter.start()
        val result = meshRouter.sendMessage(
            content = messageContent.toByteArray(),
            priority = Priority.NORMAL,
            ttl = 5
        )

        // Then
        assertTrue("Send message should succeed", result.isSuccess())
        
        coVerify(exactly = 1) { 
            mockMessageStore.storeOutgoingMessage(any(), null) 
        }
        coVerify(exactly = 1) { 
            mockTransportMultiplexer.broadcast(any()) 
        }
        
        val messageId = result.getOrThrow()
        assertNotNull("Message ID should not be null", messageId)
    }

    @Test
    fun `handle incoming chat frame processes correctly`() = runTest {
        // Given
        val messageId = UUID.randomUUID()
        val payload = "Incoming message"
        val frame = Frame(
            type = FrameType.CHAT,
            messageId = messageId,
            originId = 67890L,
            ttl = 3,
            hopCount = 1,
            priority = Priority.NORMAL,
            timestamp = System.currentTimeMillis(),
            payload = payload.toByteArray()
        )
        
        coEvery { mockMessageStore.hasSeenMessage(any()) } returns false
        coEvery { mockMessageStore.markMessageSeen(any()) } just Runs
        coEvery { mockTransportMultiplexer.sendToPeer(any(), any()) } returns com.appvalence.hayatkurtar.core.result.MeshResult.Success(Unit)
        coEvery { mockTransportMultiplexer.getConnectedPeers() } returns listOf()

        // When
        meshRouter.start()
        meshRouter.handleIncomingFrame(frame, testPeer)

        // Then
        coVerify(exactly = 1) { 
            mockMessageStore.hasSeenMessage(MessageId(messageId)) 
        }
        coVerify(exactly = 1) { 
            mockMessageStore.markMessageSeen(MessageId(messageId)) 
        }
        coVerify(exactly = 1) { 
            mockTransportMultiplexer.sendToPeer(testPeer, any()) // ACK
        }
    }

    @Test
    fun `handle duplicate message blocks forwarding`() = runTest {
        // Given
        val messageId = UUID.randomUUID()
        val frame = Frame(
            type = FrameType.CHAT,
            messageId = messageId,
            originId = 67890L,
            ttl = 3,
            timestamp = System.currentTimeMillis(),
            payload = "duplicate message".toByteArray()
        )
        
        coEvery { mockMessageStore.hasSeenMessage(any()) } returns true // Already seen

        // When
        meshRouter.start()
        meshRouter.handleIncomingFrame(frame, testPeer)

        // Then
        coVerify(exactly = 1) { 
            mockMessageStore.hasSeenMessage(MessageId(messageId)) 
        }
        coVerify(exactly = 0) { 
            mockMessageStore.markMessageSeen(any()) 
        }
        coVerify(exactly = 0) { 
            mockTransportMultiplexer.sendToPeer(any(), any()) 
        }
    }

    @Test
    fun `handle ACK frame marks message acknowledged`() = runTest {
        // Given
        val messageId = UUID.randomUUID()
        val ackFrame = Frame(
            type = FrameType.ACK,
            messageId = messageId,
            originId = testPeer.id.hashCode().toLong(),
            ttl = 1,
            timestamp = System.currentTimeMillis(),
            payload = ByteArray(0)
        )
        
        coEvery { mockMessageStore.markMessageAcknowledged(any()) } just Runs

        // When
        meshRouter.start()
        meshRouter.handleIncomingFrame(ackFrame, testPeer)

        // Then
        coVerify(exactly = 1) { 
            mockMessageStore.markMessageAcknowledged(MessageId(messageId)) 
        }
    }

    @Test
    fun `acknowledge message sends ACK frame`() = runTest {
        // Given
        val messageId = MessageId.generate()
        coEvery { mockTransportMultiplexer.sendToPeer(any(), any()) } returns com.appvalence.hayatkurtar.core.result.MeshResult.Success(Unit)

        // When
        meshRouter.start()
        val result = meshRouter.acknowledgeMessage(messageId, testPeer)

        // Then
        assertTrue("Acknowledge should succeed", result.isSuccess())
        coVerify(exactly = 1) { 
            mockTransportMultiplexer.sendToPeer(eq(testPeer), any()) 
        }
    }

    @Test
    fun `get stats returns current statistics`() = runTest {
        // When
        meshRouter.start()
        val stats = meshRouter.getStats()

        // Then
        assertNotNull("Stats should not be null", stats)
        assertEquals("Initial messages received should be 0", 0L, stats.messagesReceived)
        assertEquals("Initial messages sent should be 0", 0L, stats.messagesSent)
        assertEquals("Initial messages forwarded should be 0", 0L, stats.messagesForwarded)
    }

    @Test
    fun `mesh events are emitted correctly`() = runTest {
        // Given
        val events = mutableListOf<MeshEvent>()
        val messageContent = "Test message"
        
        coEvery { mockMessageStore.storeOutgoingMessage(any(), any()) } just Runs
        coEvery { mockTransportMultiplexer.broadcast(any()) } returns com.appvalence.hayatkurtar.core.result.MeshResult.Success(Unit)

        // When
        meshRouter.start()
        
        // Collect events in the background
        val eventsJob = meshRouter.events.onEach { events.add(it) }.launchIn(this)
        
        // Send a message
        meshRouter.sendMessage(messageContent.toByteArray(), Priority.NORMAL)
        
        // Allow some time for events to be processed
        kotlinx.coroutines.delay(100)
        eventsJob.cancel()

        // Then
        assertTrue("Should have emitted events", events.isNotEmpty())
        assertTrue("Should have network state change event", 
                  events.any { it is MeshEvent.NetworkStateChanged })
    }

    @Test
    fun `router stops gracefully`() = runTest {
        // Given
        coEvery { mockTransportMultiplexer.stop() } just Runs

        // When
        meshRouter.start()
        meshRouter.stop()

        // Then
        coVerify(exactly = 1) { mockTransportMultiplexer.stop() }
    }

    @Test
    fun `message forwarding skips originating peer`() = runTest {
        // Given
        val messageId = UUID.randomUUID()
        val frame = Frame(
            type = FrameType.CHAT,
            messageId = messageId,
            originId = 99999L,
            ttl = 5, // High TTL to ensure forwarding
            hopCount = 1,
            timestamp = System.currentTimeMillis(),
            payload = "forward test".toByteArray()
        )
        
        val otherPeer = Peer(
            id = "other-peer-002",
            name = "Other Peer",
            transport = TransportType.WIFI_DIRECT
        )
        
        coEvery { mockMessageStore.hasSeenMessage(any()) } returns false
        coEvery { mockMessageStore.markMessageSeen(any()) } just Runs
        coEvery { mockTransportMultiplexer.sendToPeer(any(), any()) } returns com.appvalence.hayatkurtar.core.result.MeshResult.Success(Unit)
        coEvery { mockTransportMultiplexer.getConnectedPeers() } returns listOf(testPeer, otherPeer)

        // When
        meshRouter.start()
        meshRouter.handleIncomingFrame(frame, testPeer)

        // Then
        // Should send ACK to originating peer
        coVerify(exactly = 1) { 
            mockTransportMultiplexer.sendToPeer(eq(testPeer), match { it.type == FrameType.ACK }) 
        }
        
        // Should forward to other peer but not back to originating peer
        coVerify(exactly = 1) { 
            mockTransportMultiplexer.sendToPeer(eq(otherPeer), match { it.type == FrameType.CHAT }) 
        }
        
        // Should not forward back to the originating peer
        coVerify(exactly = 0) { 
            mockTransportMultiplexer.sendToPeer(eq(testPeer), match { it.type == FrameType.CHAT }) 
        }
    }

    @Test
    fun `message with TTL 0 does not get forwarded`() = runTest {
        // Given
        val messageId = UUID.randomUUID()
        val frame = Frame(
            type = FrameType.CHAT,
            messageId = messageId,
            originId = 99999L,
            ttl = 0, // TTL expired
            timestamp = System.currentTimeMillis(),
            payload = "expired message".toByteArray()
        )
        
        coEvery { mockMessageStore.hasSeenMessage(any()) } returns false
        coEvery { mockMessageStore.markMessageSeen(any()) } just Runs
        coEvery { mockTransportMultiplexer.sendToPeer(any(), any()) } returns com.appvalence.hayatkurtar.core.result.MeshResult.Success(Unit)

        // When
        meshRouter.start()
        meshRouter.handleIncomingFrame(frame, testPeer)

        // Then
        // Should still send ACK
        coVerify(exactly = 1) { 
            mockTransportMultiplexer.sendToPeer(eq(testPeer), match { it.type == FrameType.ACK }) 
        }
        
        // Should not forward message
        coVerify(exactly = 0) { 
            mockTransportMultiplexer.sendToPeer(any(), match { it.type == FrameType.CHAT }) 
        }
    }
}