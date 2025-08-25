package com.appvalence.hayatkurtar.data.transport.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import com.appvalence.bluetooth.api.BluetoothController
import com.appvalence.bluetooth.api.DiscoveredDevice
import com.appvalence.bluetooth.api.HighPerformanceScanner
import com.appvalence.hayatkurtar.core.logging.MeshLog
import com.appvalence.hayatkurtar.core.logging.MeshLogTags
import com.appvalence.hayatkurtar.core.protocol.Frame
import com.appvalence.hayatkurtar.core.protocol.FrameCodec
import com.appvalence.hayatkurtar.core.result.MeshException
import com.appvalence.hayatkurtar.core.result.MeshResult
import com.appvalence.hayatkurtar.core.result.toError
import com.appvalence.hayatkurtar.core.result.toSuccess
import com.appvalence.hayatkurtar.domain.transport.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Bluetooth Classic transport strategy using RFCOMM/SPP for mesh networking
 */
@Singleton
class BluetoothClassicTransportStrategy @Inject constructor(
    @ApplicationContext private val context: Context,
    private val bluetoothController: BluetoothController,
    private val scanner: HighPerformanceScanner
) : TransportStrategy {

    override val name: String = "Bluetooth-Classic"
    override val transportType: TransportType = TransportType.BLUETOOTH_CLASSIC
    
    // SPP UUID for mesh communication
    private val MESH_SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    
    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        BluetoothAdapter.getDefaultAdapter()
    }
    
    override val isAvailable: Boolean
        get() = bluetoothAdapter?.isEnabled == true

    // Connection management
    private val connections = ConcurrentHashMap<String, BluetoothMeshLink>()
    private val serverSocket = ConcurrentHashMap<String, BluetoothServerSocket>()
    private val mutex = Mutex()
    
    // Event channels
    private val eventChannel = Channel<TransportEvent>(Channel.UNLIMITED)
    private val peerChannel = Channel<Peer>(Channel.UNLIMITED)
    
    // Background operations
    private var scope: CoroutineScope? = null
    private var serverJob: Job? = null
    private var discoveryJob: Job? = null
    
    // Configuration
    private var config = TransportConfig()

    override fun start(scope: CoroutineScope): Flow<TransportEvent> {
        this.scope = scope
        
        if (!isAvailable) {
            MeshLog.e(MeshLogTags.BLUETOOTH, "Bluetooth not available")
            return flowOf(TransportEvent.Error(MeshException.Transport.TransportNotSupported("Bluetooth")))
        }
        
        startServerSocket()
        startConnectionMonitoring()
        
        MeshLog.i(MeshLogTags.BLUETOOTH, "Bluetooth Classic transport started")
        
        return eventChannel.receiveAsFlow()
    }

    override fun discoverPeers(): Flow<Peer> {
        if (!isAvailable) {
            return flowOf()
        }
        
        discoveryJob = scope?.launch {
            try {
                scanner.startScan().collect { device ->
                    val peer = device.toPeer()
                    peerChannel.trySend(peer)
                    eventChannel.trySend(TransportEvent.PeerDiscovered(peer))
                }
            } catch (e: Exception) {
                MeshLog.e(MeshLogTags.BLUETOOTH, "Error in peer discovery", e)
                eventChannel.trySend(TransportEvent.Error(e))
            }
        }
        
        return peerChannel.receiveAsFlow()
    }

    override suspend fun connectTo(peer: Peer): MeshResult<Link> {
        if (!isAvailable) {
            return MeshException.Transport.TransportNotSupported("Bluetooth not available").toError()
        }
        
        return mutex.withLock {
            // Check if already connected
            connections[peer.id]?.let { existingLink ->
                if (existingLink.isConnected) {
                    return@withLock existingLink.toSuccess()
                }
            }
            
            connectToDevice(peer)
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun connectToDevice(peer: Peer): MeshResult<Link> = withContext(Dispatchers.IO) {
        try {
            val device = bluetoothAdapter?.getRemoteDevice(peer.id)
                ?: return@withContext MeshException.Transport.ConnectionFailed(peer.id, Exception("Device not found")).toError()
            
            MeshLog.d(MeshLogTags.BLUETOOTH, "Connecting to ${device.address}")
            
            // Create RFCOMM socket
            val socket = device.createRfcommSocketToServiceRecord(MESH_SPP_UUID)
            
            // Cancel discovery to improve connection performance
            bluetoothAdapter?.cancelDiscovery()
            
            socket.connect()
            
            val link = BluetoothMeshLink(peer, socket)
            connections[peer.id] = link
            
            // Start reading frames
            link.startReading { frame ->
                eventChannel.trySend(TransportEvent.FrameReceived(frame, peer))
            }
            
            eventChannel.trySend(TransportEvent.Connected(link))
            MeshLog.i(MeshLogTags.BLUETOOTH, "Connected to ${device.address}")
            
            link.toSuccess()
            
        } catch (e: Exception) {
            MeshLog.e(MeshLogTags.BLUETOOTH, "Failed to connect to ${peer.id}", e)
            MeshException.Transport.ConnectionFailed(peer.id, e).toError()
        }
    }

    override suspend fun broadcast(frame: Frame): MeshResult<Unit> {
        val connectedLinks = connections.values.filter { it.isConnected }
        
        if (connectedLinks.isEmpty()) {
            return MeshException.Transport.NoTransportAvailable().toError()
        }
        
        var successCount = 0
        var lastError: Exception? = null
        
        for (link in connectedLinks) {
            val result = link.send(frame)
            if (result.isSuccess()) {
                successCount++
            } else {
                lastError = result.errorOrNull()
            }
        }
        
        return if (successCount > 0) {
            Unit.toSuccess()
        } else {
            MeshException.Transport.SendFailed("Broadcast failed", lastError).toError()
        }
    }

    override suspend fun sendTo(peer: Peer, frame: Frame): MeshResult<Unit> {
        val link = connections[peer.id]
            ?: return MeshException.Transport.ConnectionFailed(peer.id, Exception("Not connected")).toError()
        
        return link.send(frame)
    }

    override fun getConnectedPeers(): List<Peer> {
        return connections.values.filter { it.isConnected }.map { it.peer }
    }

    override fun getLink(peerId: String): Link? {
        return connections[peerId]?.takeIf { it.isConnected }
    }

    override suspend fun stop() {
        MeshLog.i(MeshLogTags.BLUETOOTH, "Stopping Bluetooth Classic transport")
        
        // Stop discovery
        discoveryJob?.cancel()
        scanner.stopScan()
        
        // Close all connections
        connections.values.forEach { it.disconnect() }
        connections.clear()
        
        // Close server socket
        serverJob?.cancel()
        serverSocket.values.forEach { 
            try {
                it.close()
            } catch (e: Exception) {
                MeshLog.w(MeshLogTags.BLUETOOTH, "Error closing server socket", e)
            }
        }
        serverSocket.clear()
        
        // Close channels
        eventChannel.close()
        peerChannel.close()
        
        scope = null
    }

    @SuppressLint("MissingPermission")
    private fun startServerSocket() {
        serverJob = scope?.launch(Dispatchers.IO) {
            try {
                val server = bluetoothAdapter?.listenUsingRfcommWithServiceRecord("MeshNetwork", MESH_SPP_UUID)
                if (server != null) {
                    serverSocket["main"] = server
                    MeshLog.i(MeshLogTags.BLUETOOTH, "Bluetooth server socket started")
                    
                    while (isActive) {
                        try {
                            val clientSocket = server.accept()
                            launch {
                                handleIncomingConnection(clientSocket)
                            }
                        } catch (e: IOException) {
                            if (isActive) {
                                MeshLog.w(MeshLogTags.BLUETOOTH, "Server socket error", e)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                MeshLog.e(MeshLogTags.BLUETOOTH, "Failed to start server socket", e)
                eventChannel.trySend(TransportEvent.Error(e))
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun handleIncomingConnection(socket: BluetoothSocket) {
        try {
            val remoteDevice = socket.remoteDevice
            val peer = Peer(
                id = remoteDevice.address,
                name = remoteDevice.name ?: "Unknown",
                transport = TransportType.BLUETOOTH_CLASSIC,
                linkQuality = LinkQuality.GOOD
            )
            
            MeshLog.d(MeshLogTags.BLUETOOTH, "Incoming connection from ${remoteDevice.address}")
            
            val link = BluetoothMeshLink(peer, socket)
            connections[peer.id] = link
            
            eventChannel.trySend(TransportEvent.Connected(link))
            
            // Start reading frames
            link.startReading { frame ->
                eventChannel.trySend(TransportEvent.FrameReceived(frame, peer))
            }
            
        } catch (e: Exception) {
            MeshLog.e(MeshLogTags.BLUETOOTH, "Error handling incoming connection", e)
            socket.close()
        }
    }

    private fun startConnectionMonitoring() {
        scope?.launch {
            while (isActive) {
                delay(30_000) // Check every 30 seconds
                
                val disconnectedPeers = connections.filter { !it.value.isConnected }
                disconnectedPeers.forEach { (peerId, link) ->
                    connections.remove(peerId)
                    eventChannel.trySend(TransportEvent.Disconnected(peerId, "Connection lost"))
                }
                
                if (disconnectedPeers.isNotEmpty()) {
                    MeshLog.d(MeshLogTags.BLUETOOTH, "Cleaned up ${disconnectedPeers.size} disconnected peers")
                }
            }
        }
    }
}

/**
 * Bluetooth mesh link implementation using RFCOMM
 */
class BluetoothMeshLink(
    override val peer: Peer,
    private val socket: BluetoothSocket
) : Link {
    
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    private val connectionState = MutableStateFlow(socket.isConnected)
    private var readingJob: Job? = null
    
    override val isConnected: Boolean
        get() = socket.isConnected
    
    override val linkQuality: LinkQuality
        get() = if (isConnected) LinkQuality.GOOD else LinkQuality.POOR

    init {
        try {
            inputStream = socket.inputStream
            outputStream = socket.outputStream
        } catch (e: Exception) {
            MeshLog.e(MeshLogTags.BLUETOOTH, "Failed to get socket streams", e)
        }
    }

    override suspend fun send(frame: Frame): MeshResult<Unit> = withContext(Dispatchers.IO) {
        try {
            val frameBytes = FrameCodec.encode(frame).getOrThrow()
            val output = outputStream ?: throw IOException("Output stream not available")
            
            // Send frame length first (4 bytes)
            val lengthBytes = ByteArray(4)
            lengthBytes[0] = (frameBytes.size shr 24).toByte()
            lengthBytes[1] = (frameBytes.size shr 16).toByte()
            lengthBytes[2] = (frameBytes.size shr 8).toByte()
            lengthBytes[3] = frameBytes.size.toByte()
            
            output.write(lengthBytes)
            output.write(frameBytes)
            output.flush()
            
            Unit.toSuccess()
        } catch (e: Exception) {
            MeshException.Transport.SendFailed("Bluetooth send failed", e).toError()
        }
    }

    override suspend fun disconnect(): MeshResult<Unit> = withContext(Dispatchers.IO) {
        try {
            readingJob?.cancel()
            inputStream?.close()
            outputStream?.close()
            socket.close()
            connectionState.value = false
            Unit.toSuccess()
        } catch (e: Exception) {
            MeshException.Transport.ConnectionFailed(peer.id, e).toError()
        }
    }

    override fun observeConnection(): Flow<Boolean> = connectionState.asStateFlow()

    fun startReading(onFrameReceived: (Frame) -> Unit) {
        readingJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val input = inputStream ?: return@launch
                
                while (isActive && isConnected) {
                    // Read frame length (4 bytes)
                    val lengthBytes = ByteArray(4)
                    var bytesRead = 0
                    while (bytesRead < 4) {
                        val read = input.read(lengthBytes, bytesRead, 4 - bytesRead)
                        if (read == -1) break
                        bytesRead += read
                    }
                    
                    if (bytesRead < 4) break
                    
                    val frameLength = ((lengthBytes[0].toInt() and 0xFF) shl 24) or
                                     ((lengthBytes[1].toInt() and 0xFF) shl 16) or
                                     ((lengthBytes[2].toInt() and 0xFF) shl 8) or
                                     (lengthBytes[3].toInt() and 0xFF)
                    
                    if (frameLength <= 0 || frameLength > Frame.MAX_FRAME_SIZE) {
                        MeshLog.w(MeshLogTags.BLUETOOTH, "Invalid frame length: $frameLength")
                        break
                    }
                    
                    // Read frame data
                    val frameBytes = ByteArray(frameLength)
                    bytesRead = 0
                    while (bytesRead < frameLength) {
                        val read = input.read(frameBytes, bytesRead, frameLength - bytesRead)
                        if (read == -1) break
                        bytesRead += read
                    }
                    
                    if (bytesRead < frameLength) break
                    
                    // Parse and emit frame
                    val frameResult = FrameCodec.decode(frameBytes)
                    if (frameResult.isSuccess()) {
                        onFrameReceived(frameResult.getOrThrow())
                    } else {
                        MeshLog.w(MeshLogTags.BLUETOOTH, "Failed to decode frame: ${frameResult.errorOrNull()}")
                    }
                }
            } catch (e: Exception) {
                if (isActive) {
                    MeshLog.e(MeshLogTags.BLUETOOTH, "Error reading from socket", e)
                }
            } finally {
                connectionState.value = false
            }
        }
    }
}

/**
 * Extension function to convert DiscoveredDevice to Peer
 */
private fun DiscoveredDevice.toPeer(): Peer {
    return Peer(
        id = address,
        name = name,
        transport = TransportType.BLUETOOTH_CLASSIC,
        linkQuality = when {
            rssi != null && rssi!! > -50 -> LinkQuality.EXCELLENT
            rssi != null && rssi!! > -70 -> LinkQuality.GOOD
            rssi != null && rssi!! > -85 -> LinkQuality.POOR
            else -> LinkQuality.UNKNOWN
        },
        lastSeen = System.currentTimeMillis(),
        metadata = mapOf(
            "rssi" to (rssi ?: 0),
            "txPower" to (txPower ?: 0)
        )
    )
}