package com.appvalence.hayatkurtar.domain.usecase

import com.appvalence.hayatkurtar.core.protocol.Priority
import com.appvalence.hayatkurtar.core.result.MeshResult
import com.appvalence.hayatkurtar.domain.mesh.MessageId
import com.appvalence.hayatkurtar.domain.mesh.MeshRouter
import javax.inject.Inject

/**
 * Use case for sending messages through the mesh network
 */
class SendMessageUseCase @Inject constructor(
    private val meshRouter: MeshRouter
) {
    suspend operator fun invoke(
        message: String,
        priority: Priority = Priority.NORMAL
    ): MeshResult<MessageId> {
        val content = message.toByteArray(Charsets.UTF_8)
        return meshRouter.sendMessage(content, priority)
    }
}

/**
 * Use case for sending emergency/SOS messages with high priority
 */
class SendEmergencyMessageUseCase @Inject constructor(
    private val meshRouter: MeshRouter
) {
    suspend operator fun invoke(
        emergencyMessage: String,
        location: String? = null
    ): MeshResult<MessageId> {
        val fullMessage = buildString {
            append("🆘 EMERGENCY: ")
            append(emergencyMessage)
            if (location != null) {
                append(" | Location: $location")
            }
            append(" | Time: ${System.currentTimeMillis()}")
        }
        
        return meshRouter.sendMessage(
            content = fullMessage.toByteArray(Charsets.UTF_8),
            priority = Priority.HIGH,
            ttl = 10  // Higher TTL for emergency messages
        )
    }
}

/**
 * Use case for acknowledging received messages
 */
class AcknowledgeMessageUseCase @Inject constructor(
    private val meshRouter: MeshRouter
) {
    suspend operator fun invoke(
        messageId: MessageId,
        toPeer: com.appvalence.hayatkurtar.domain.transport.Peer
    ): MeshResult<Unit> {
        return meshRouter.acknowledgeMessage(messageId, toPeer)
    }
}

/**
 * Use case for getting mesh network statistics
 */
class GetMeshStatsUseCase @Inject constructor(
    private val meshRouter: MeshRouter
) {
    operator fun invoke() = meshRouter.getStats()
}

/**
 * Use case for discovering and managing network peers
 */
class DiscoverPeersUseCase @Inject constructor(
    private val transportMultiplexer: com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer
) {
    fun execute() = transportMultiplexer.discoverPeers()
}

/**
 * Use case for connecting to a specific peer
 */
class ConnectToPeerUseCase @Inject constructor(
    private val transportMultiplexer: com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer
) {
    suspend operator fun invoke(
        peer: com.appvalence.hayatkurtar.domain.transport.Peer
    ): MeshResult<com.appvalence.hayatkurtar.domain.transport.Link> {
        return transportMultiplexer.connectToPeer(peer)
    }
}

/**
 * Use case for getting connected peers
 */
class GetConnectedPeersUseCase @Inject constructor(
    private val transportMultiplexer: com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer
) {
    operator fun invoke() = transportMultiplexer.getConnectedPeers()
}