package com.appvalence.hayatkurtar.testing.integration

import com.appvalence.hayatkurtar.core.logging.MeshLogger
import com.appvalence.hayatkurtar.core.logging.MeshLogLevel
import com.appvalence.hayatkurtar.core.protocol.Priority
import com.appvalence.hayatkurtar.data.mesh.router.DefaultMeshRouter
import com.appvalence.hayatkurtar.data.mesh.router.DeviceIdProvider
import com.appvalence.hayatkurtar.data.mesh.store.RoomMessageStore
import com.appvalence.hayatkurtar.data.mesh.transport.DefaultTransportMultiplexer
import com.appvalence.hayatkurtar.domain.mesh.MeshEvent
import com.appvalence.hayatkurtar.domain.mesh.MeshRouter
import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer
import com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy
import com.appvalence.hayatkurtar.testing.transport.MeshTestUtils
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class MeshNetworkIntegrationTest {

    private lateinit var nodeA: TestMeshNode
    private lateinit var nodeB: TestMeshNode
    private lateinit var nodeC: TestMeshNode
    
    @Before
    fun setup() {
        // Initialize mock logger for testing
        val mockContext = mockk<android.content.Context>(relaxed = true)
        try {
            MeshLogger.initialize(mockContext, MeshLogLevel.DEBUG)
        } catch (e: Exception) {
            // Logger might already be initialized
        }
        
        // Create three-node mesh network
        val (transportA, transportB, transportC) = MeshTestUtils.createThreeNodeMesh()
        
        nodeA = createTestNode("A", 1001L, transportA)
        nodeB = createTestNode("B", 1002L, transportB)
        nodeC = createTestNode("C", 1003L, transportC)
    }
    
    @After
    fun teardown() {
        runTest {
            nodeA.stop()
            nodeB.stop()
            nodeC.stop()
        }
    }

    @Test
    fun `direct message transmission between two nodes`() = runTest {
        // Given
        nodeA.start()
        nodeB.start()
        
        // Wait for discovery
        MeshTestUtils.waitForPropagation()
        
        // Connect A to B
        val peersA = nodeA.discoverPeers()
        val peerB = peersA.first { it.id == "B" }
        nodeA.connectToPeer(peerB)
        
        MeshTestUtils.waitForPropagation()

        // When
        val messageContent = "Hello from A to B"
        val messageId = nodeA.sendMessage(messageContent)

        // Wait for message propagation
        MeshTestUtils.waitForPropagation()

        // Then
        assertTrue("Message should be sent successfully", messageId != null)
        
        val receivedEvents = nodeB.getReceivedEvents()
        assertTrue("Node B should receive the message", 
                  receivedEvents.any { it is MeshEvent.MessageReceived })
        
        val messageEvent = receivedEvents.filterIsInstance<MeshEvent.MessageReceived>().first()
        assertEquals("Message content should match", 
                    messageContent, String(messageEvent.message.content, Charsets.UTF_8))
    }

    @Test
    fun `multi-hop message routing A to B to C`() = runTest {
        // Given - Setup linear topology: A <-> B <-> C
        nodeA.start()
        nodeB.start()
        nodeC.start()
        
        MeshTestUtils.waitForPropagation()
        
        // Connect A to B
        val peersA = nodeA.discoverPeers()
        val peerB = peersA.first { it.id == "B" }
        nodeA.connectToPeer(peerB)
        
        // Connect B to C  
        val peersB = nodeB.discoverPeers()
        val peerC = peersB.first { it.id == "C" }
        nodeB.connectToPeer(peerC)
        
        MeshTestUtils.waitForPropagation(1000) // Allow for connection establishment

        // When - A sends message to the network
        val messageContent = "Multi-hop message from A"
        val messageId = nodeA.sendMessage(messageContent, Priority.NORMAL, ttl = 5)

        // Wait for multi-hop propagation
        MeshTestUtils.waitForPropagation(2000)

        // Then - C should receive the message via B
        assertNotNull("Message should be sent", messageId)
        
        val eventsB = nodeB.getReceivedEvents()
        val eventsC = nodeC.getReceivedEvents()
        
        assertTrue("Node B should receive the message", 
                  eventsB.any { it is MeshEvent.MessageReceived })
        assertTrue("Node B should forward the message", 
                  eventsB.any { it is MeshEvent.MessageForwarded })
        assertTrue("Node C should receive the forwarded message", 
                  eventsC.any { it is MeshEvent.MessageReceived })
        
        val messageEventC = eventsC.filterIsInstance<MeshEvent.MessageReceived>().first()
        assertEquals("Message content should match at destination", 
                    messageContent, String(messageEventC.message.content, Charsets.UTF_8))
    }

    @Test
    fun `message deduplication prevents loops`() = runTest {
        // Given - All nodes connected in a triangle: A <-> B <-> C <-> A
        nodeA.start()
        nodeB.start()
        nodeC.start()
        
        MeshTestUtils.waitForPropagation()
        
        // Create full mesh connections
        val peersA = nodeA.discoverPeers()
        val peersB = nodeB.discoverPeers()
        val peersC = nodeC.discoverPeers()
        
        nodeA.connectToPeer(peersA.first { it.id == "B" })
        nodeA.connectToPeer(peersA.first { it.id == "C" })
        nodeB.connectToPeer(peersB.first { it.id == "C" })
        
        MeshTestUtils.waitForPropagation(1000)

        // When - A sends a message
        val messageContent = "Dedup test message"
        nodeA.sendMessage(messageContent, Priority.NORMAL, ttl = 5)

        MeshTestUtils.waitForPropagation(2000)

        // Then - Each node should receive the message only once
        val eventsB = nodeB.getReceivedEvents()
        val eventsC = nodeC.getReceivedEvents()
        
        val receivedMessagesB = eventsB.filterIsInstance<MeshEvent.MessageReceived>()
        val receivedMessagesC = eventsC.filterIsInstance<MeshEvent.MessageReceived>()
        
        assertEquals("Node B should receive message exactly once", 1, receivedMessagesB.size)
        assertEquals("Node C should receive message exactly once", 1, receivedMessagesC.size)
        
        // Check for duplicate blocking events
        assertTrue("Should have duplicate blocking", 
                  nodeB.getMeshStats().duplicatesBlocked > 0 || nodeC.getMeshStats().duplicatesBlocked > 0)
    }

    @Test
    fun `TTL expiration stops message propagation`() = runTest {
        // Given - Linear chain: A <-> B <-> C
        nodeA.start()
        nodeB.start()
        nodeC.start()
        
        MeshTestUtils.waitForPropagation()
        
        val peersA = nodeA.discoverPeers()
        val peersB = nodeB.discoverPeers()
        
        nodeA.connectToPeer(peersA.first { it.id == "B" })
        nodeB.connectToPeer(peersB.first { it.id == "C" })
        
        MeshTestUtils.waitForPropagation(1000)

        // When - A sends message with TTL=1 (should reach B but not C)
        val messageContent = "TTL=1 message"
        nodeA.sendMessage(messageContent, Priority.NORMAL, ttl = 1)

        MeshTestUtils.waitForPropagation(2000)

        // Then
        val eventsB = nodeB.getReceivedEvents()
        val eventsC = nodeC.getReceivedEvents()
        
        assertTrue("Node B should receive the message", 
                  eventsB.any { it is MeshEvent.MessageReceived })
        assertFalse("Node C should NOT receive the message due to TTL expiration", 
                   eventsC.any { it is MeshEvent.MessageReceived })
        
        // Check for TTL expiration events
        assertTrue("Should have message expiration events", 
                  eventsB.any { it is MeshEvent.MessageExpired } || 
                  eventsC.any { it is MeshEvent.MessageExpired })
    }

    @Test
    fun `network partition and recovery`() = runTest {
        // Given - Connected network
        nodeA.start()
        nodeB.start()
        nodeC.start()
        
        MeshTestUtils.waitForPropagation()
        
        val peersA = nodeA.discoverPeers()
        val peersB = nodeB.discoverPeers()
        
        nodeA.connectToPeer(peersA.first { it.id == "B" })
        nodeB.connectToPeer(peersB.first { it.id == "C" })
        
        MeshTestUtils.waitForPropagation(1000)

        // When - Simulate B going offline (network partition)
        nodeB.simulateDisconnection()
        MeshTestUtils.waitForPropagation()

        // Send message from A (should not reach C)
        val messageBeforeRecovery = "Message during partition"
        nodeA.sendMessage(messageBeforeRecovery)
        MeshTestUtils.waitForPropagation(1000)

        // Then - Message should not reach C
        val eventsCDuringPartition = nodeC.getReceivedEvents()
        assertFalse("Message should not reach C during partition", 
                   eventsCDuringPartition.any { 
                       it is MeshEvent.MessageReceived && 
                       String((it as MeshEvent.MessageReceived).message.content, Charsets.UTF_8) == messageBeforeRecovery 
                   })

        // When - B comes back online
        nodeB.start()
        MeshTestUtils.waitForPropagation()
        
        // Reconnect
        val peersAAfterRecovery = nodeA.discoverPeers()
        val peersBAfterRecovery = nodeB.discoverPeers()
        
        nodeA.connectToPeer(peersAAfterRecovery.first { it.id == "B" })
        nodeB.connectToPeer(peersBAfterRecovery.first { it.id == "C" })
        
        MeshTestUtils.waitForPropagation(1000)

        // Send message after recovery
        val messageAfterRecovery = "Message after recovery"
        nodeA.sendMessage(messageAfterRecovery)
        MeshTestUtils.waitForPropagation(2000)

        // Then - Message should reach C after recovery
        val eventsCAfterRecovery = nodeC.getReceivedEvents()
        assertTrue("Message should reach C after recovery", 
                  eventsCAfterRecovery.any { 
                      it is MeshEvent.MessageReceived && 
                      String((it as MeshEvent.MessageReceived).message.content, Charsets.UTF_8) == messageAfterRecovery 
                  })
    }

    @Test
    fun `high priority messages get retry behavior`() = runTest {
        // Given
        nodeA.start()
        nodeB.start()
        
        MeshTestUtils.waitForPropagation()
        
        val peersA = nodeA.discoverPeers()
        val peerB = peersA.first { it.id == "B" }
        nodeA.connectToPeer(peerB)
        
        MeshTestUtils.waitForPropagation()

        // When - Send high priority message
        val emergencyMessage = "🆘 EMERGENCY: Help needed!"
        val messageId = nodeA.sendMessage(emergencyMessage, Priority.HIGH)

        MeshTestUtils.waitForPropagation(3000) // Allow time for retries

        // Then
        assertNotNull("Emergency message should be sent", messageId)
        
        val eventsB = nodeB.getReceivedEvents()
        assertTrue("Node B should receive emergency message", 
                  eventsB.any { it is MeshEvent.MessageReceived })
        
        val stats = nodeA.getMeshStats()
        assertTrue("Should have attempted to send messages", stats.messagesSent > 0)
    }

    private fun createTestNode(
        nodeId: String, 
        deviceId: Long, 
        transport: FakeTransportStrategy
    ): TestMeshNode {
        val mockMessageStore = mockk<RoomMessageStore>(relaxed = true)
        val mockDeviceIdProvider = mockk<DeviceIdProvider>()
        
        every { mockDeviceIdProvider.getDeviceId() } returns deviceId
        
        val transportMultiplexer = DefaultTransportMultiplexer().apply {
            addTransport(transport)
        }
        
        val meshRouter = DefaultMeshRouter(
            transportMultiplexer = transportMultiplexer,
            messageStore = mockMessageStore,
            deviceIdProvider = mockDeviceIdProvider
        )
        
        return TestMeshNode(nodeId, meshRouter, transportMultiplexer, transport)
    }

    private data class TestMeshNode(
        val nodeId: String,
        val meshRouter: MeshRouter,
        val transportMultiplexer: TransportMultiplexer,
        val fakeTransport: FakeTransportStrategy
    ) {
        private val receivedEvents = mutableListOf<MeshEvent>()
        
        suspend fun start() {
            meshRouter.start()
            transportMultiplexer.start()
            
            // Collect events in background
            kotlinx.coroutines.GlobalScope.launch {
                meshRouter.events.collect { event ->
                    receivedEvents.add(event)
                }
            }
        }
        
        suspend fun stop() {
            meshRouter.stop()
            transportMultiplexer.stop()
        }
        
        suspend fun sendMessage(
            content: String, 
            priority: Priority = Priority.NORMAL, 
            ttl: Byte = 8
        ) = meshRouter.sendMessage(content.toByteArray(), priority, ttl).getOrNull()
        
        suspend fun discoverPeers() = withTimeoutOrNull(2000) {
            transportMultiplexer.discoverPeers().toList()
        } ?: emptyList()
        
        suspend fun connectToPeer(peer: com.appvalence.hayatkurtar.domain.transport.Peer) = 
            transportMultiplexer.connectToPeer(peer)
        
        fun getReceivedEvents() = receivedEvents.toList()
        
        fun getMeshStats() = meshRouter.getStats()
        
        fun simulateDisconnection() {
            fakeTransport.setEnabled(false)
        }
    }
}