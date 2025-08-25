package com.appvalence.hayatkurtar.data.transport.wifidirect

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.*
import android.net.wifi.p2p.WifiP2pManager.*
import android.os.Looper
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
import java.net.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Wi-Fi Direct transport strategy implementation
 */
@Singleton
class WiFiDirectTransportStrategy @Inject constructor(
    @ApplicationContext private val context: Context
) : TransportStrategy {

    override val name: String = "WiFi-Direct"
    override val transportType: TransportType = TransportType.WIFI_DIRECT
    
    private val manager: WifiP2pManager? by lazy {
        context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager?
    }
    
    private var channel: WifiP2pManager.Channel? = null
    private var isInitialized = false
    
    // Connection management
    private val connections = ConcurrentHashMap<String, WiFiDirectLink>()
    private val discoveredPeers = ConcurrentHashMap<String, WifiP2pDevice>()
    private val mutex = Mutex()
    
    // Event channels
    private val eventChannel = Channel<TransportEvent>(Channel.UNLIMITED)
    private val peerChannel = Channel<Peer>(Channel.UNLIMITED)
    
    // Background operations
    private var scope: CoroutineScope? = null
    private var receiver: WiFiDirectBroadcastReceiver? = null
    
    // Server socket for incoming connections
    private var serverSocket: ServerSocket? = null
    private var serverJob: Job? = null
    
    // Configuration
    private var config = TransportConfig()
    
    override val isAvailable: Boolean
        get() = manager != null && context.packageManager.hasSystemFeature("android.hardware.wifi.direct")

    override fun start(scope: CoroutineScope): Flow<TransportEvent> {
        this.scope = scope
        
        if (!isAvailable) {
            MeshLog.e(MeshLogTags.WIFI_DIRECT, "Wi-Fi Direct not available on this device")
            return flowOf(TransportEvent.Error(MeshException.Transport.TransportNotSupported("WiFi-Direct")))
        }
        
        initialize()
        
        return eventChannel.receiveAsFlow()
    }

    @SuppressLint("MissingPermission")
    private fun initialize() {
        if (isInitialized) return
        
        try {
            channel = manager?.initialize(context, Looper.getMainLooper()) {
                MeshLog.e(MeshLogTags.WIFI_DIRECT, "Wi-Fi Direct channel disconnected")
                eventChannel.trySend(TransportEvent.Error(Exception("Channel disconnected")))
            }
            
            if (channel == null) {
                throw Exception("Failed to initialize Wi-Fi Direct channel")
            }
            
            // Register broadcast receiver
            receiver = WiFiDirectBroadcastReceiver()
            val intentFilter = IntentFilter().apply {
                addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
                addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
                addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
                addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
            }
            context.registerReceiver(receiver, intentFilter)
            
            // Start server socket
            startServerSocket()
            
            isInitialized = true
            MeshLog.i(MeshLogTags.WIFI_DIRECT, "Wi-Fi Direct transport initialized")
            
        } catch (e: Exception) {
            MeshLog.e(MeshLogTags.WIFI_DIRECT, "Failed to initialize Wi-Fi Direct", e)
            eventChannel.trySend(TransportEvent.Error(e))
        }
    }

    override fun discoverPeers(): Flow<Peer> {
        if (!isInitialized) {
            return flowOf()
        }
        
        scope?.launch {
            startPeerDiscovery()
        }
        
        return peerChannel.receiveAsFlow()
    }

    @SuppressLint("MissingPermission")
    private suspend fun startPeerDiscovery() {
        try {
            manager?.discoverPeers(channel, object : ActionListener {
                override fun onSuccess() {
                    MeshLog.d(MeshLogTags.WIFI_DIRECT, "Peer discovery started")
                }

                override fun onFailure(reason: Int) {
                    MeshLog.w(MeshLogTags.WIFI_DIRECT, "Peer discovery failed: $reason")
                }
            })
        } catch (e: SecurityException) {
            MeshLog.e(MeshLogTags.WIFI_DIRECT, "Permission denied for peer discovery", e)
            eventChannel.trySend(TransportEvent.Error(MeshException.Permission.WiFiDirectNotGranted()))
        }
    }

    override suspend fun connectTo(peer: Peer): MeshResult<Link> {
        if (!isInitialized) {
            return MeshException.Transport.TransportNotSupported("WiFi-Direct not initialized").toError()
        }
        
        val p2pDevice = discoveredPeers[peer.id] 
            ?: return MeshException.Transport.ConnectionFailed(peer.id, Exception("Peer not found")).toError()
        
        return mutex.withLock {
            // Check if already connected
            connections[peer.id]?.let { existingLink ->
                if (existingLink.isConnected) {
                    return@withLock existingLink.toSuccess()
                }
            }
            
            connectToDevice(p2pDevice, peer)
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun connectToDevice(device: WifiP2pDevice, peer: Peer): MeshResult<Link> = withContext(Dispatchers.IO) {
        try {
            val config = WifiP2pConfig().apply {
                deviceAddress = device.deviceAddress
                wps.setup = WpsInfo.PBC // Push button configuration
                groupOwnerIntent = 0 // Let the system decide who becomes group owner
            }
            
            val result = CompletableDeferred<MeshResult<Link>>()
            
            manager?.connect(channel, config, object : ActionListener {
                override fun onSuccess() {
                    // Connection initiated successfully
                    MeshLog.d(MeshLogTags.WIFI_DIRECT, "Connection initiated to ${device.deviceAddress}")
                }

                override fun onFailure(reason: Int) {
                    val error = MeshException.Transport.ConnectionFailed(
                        device.deviceAddress, 
                        Exception("Connection failed with reason: $reason")
                    )
                    result.complete(error.toError())
                }
            })
            
            // Wait for connection to be established (handled in broadcast receiver)
            // This is a simplified approach - in real implementation, you'd have more sophisticated connection tracking
            delay(30000) // 30 second timeout
            
            val link = connections[peer.id]
            if (link?.isConnected == true) {
                result.complete(link.toSuccess())
            } else {
                val error = MeshException.Transport.ConnectionFailed(peer.id, Exception("Connection timeout"))
                result.complete(error.toError())
            }
            
            result.await()
            
        } catch (e: Exception) {
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
        MeshLog.i(MeshLogTags.WIFI_DIRECT, "Stopping Wi-Fi Direct transport")
        
        // Close all connections
        connections.values.forEach { it.disconnect() }
        connections.clear()
        
        // Stop server
        serverJob?.cancel()
        serverSocket?.close()
        serverSocket = null
        
        // Unregister receiver
        receiver?.let { context.unregisterReceiver(it) }
        receiver = null
        
        // Close channels
        eventChannel.close()
        peerChannel.close()
        
        isInitialized = false
        scope = null
    }

    private fun startServerSocket() {
        serverJob = scope?.launch(Dispatchers.IO) {
            try {
                serverSocket = ServerSocket(8888) // Fixed port for mesh communication
                MeshLog.i(MeshLogTags.WIFI_DIRECT, "Server socket started on port 8888")
                
                while (isActive) {
                    try {
                        val clientSocket = serverSocket?.accept()
                        if (clientSocket != null) {
                            launch {
                                handleIncomingConnection(clientSocket)
                            }
                        }
                    } catch (e: IOException) {
                        if (isActive) {
                            MeshLog.w(MeshLogTags.WIFI_DIRECT, "Server socket error", e)
                        }
                    }
                }
            } catch (e: Exception) {
                MeshLog.e(MeshLogTags.WIFI_DIRECT, "Failed to start server socket", e)
            }
        }
    }

    private suspend fun handleIncomingConnection(socket: Socket) {
        try {
            val remoteAddress = socket.remoteSocketAddress.toString()
            MeshLog.d(MeshLogTags.WIFI_DIRECT, "Incoming connection from $remoteAddress")
            
            // Create a peer representation for the incoming connection
            val peer = Peer(
                id = remoteAddress,
                name = "WiFi-$remoteAddress",
                transport = TransportType.WIFI_DIRECT,
                linkQuality = LinkQuality.GOOD
            )
            
            val link = WiFiDirectLink(peer, socket)
            connections[peer.id] = link
            
            eventChannel.trySend(TransportEvent.Connected(link))
            
            // Start reading frames
            link.startReading { frame ->
                eventChannel.trySend(TransportEvent.FrameReceived(frame, peer))
            }
            
        } catch (e: Exception) {
            MeshLog.e(MeshLogTags.WIFI_DIRECT, "Error handling incoming connection", e)
            socket.close()
        }
    }

    /**
     * Broadcast receiver for Wi-Fi Direct events
     */
    private inner class WiFiDirectBroadcastReceiver : BroadcastReceiver() {
        
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                WIFI_P2P_STATE_CHANGED_ACTION -> {
                    val state = intent.getIntExtra(EXTRA_WIFI_STATE, -1)
                    val isEnabled = state == WifiP2pManager.WIFI_P2P_STATE_ENABLED
                    MeshLog.d(MeshLogTags.WIFI_DIRECT, "Wi-Fi P2P state changed: enabled=$isEnabled")
                }
                
                WIFI_P2P_PEERS_CHANGED_ACTION -> {
                    // Request available peers from the wifi p2p manager
                    manager?.requestPeers(channel) { peers ->
                        handlePeersChanged(peers)
                    }
                }
                
                WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                    val networkInfo = intent.getParcelableExtra<android.net.NetworkInfo>(EXTRA_NETWORK_INFO)
                    if (networkInfo?.isConnected == true) {
                        manager?.requestConnectionInfo(channel) { info ->
                            handleConnectionEstablished(info)
                        }
                    }
                }
                
                WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                    val device = intent.getParcelableExtra<WifiP2pDevice>(EXTRA_WIFI_P2P_DEVICE)
                    MeshLog.d(MeshLogTags.WIFI_DIRECT, "This device changed: ${device?.deviceName}")
                }
            }
        }

        private fun handlePeersChanged(peerList: WifiP2pDeviceList) {
            discoveredPeers.clear()
            
            for (device in peerList.deviceList) {
                discoveredPeers[device.deviceAddress] = device
                
                val peer = Peer(
                    id = device.deviceAddress,
                    name = device.deviceName ?: "Unknown",
                    transport = TransportType.WIFI_DIRECT,
                    linkQuality = when (device.status) {
                        WifiP2pDevice.AVAILABLE -> LinkQuality.GOOD
                        WifiP2pDevice.CONNECTED -> LinkQuality.EXCELLENT
                        else -> LinkQuality.POOR
                    }
                )
                
                peerChannel.trySend(peer)
                eventChannel.trySend(TransportEvent.PeerDiscovered(peer))
            }
            
            MeshLog.d(MeshLogTags.WIFI_DIRECT, "Discovered ${peerList.deviceList.size} peers")
        }

        private fun handleConnectionEstablished(info: WifiP2pInfo) {
            scope?.launch(Dispatchers.IO) {
                try {
                    if (info.groupFormed) {
                        if (info.isGroupOwner) {
                            // This device is group owner - server socket is already running
                            MeshLog.i(MeshLogTags.WIFI_DIRECT, "Device is group owner")
                        } else {
                            // This device is client - connect to group owner
                            val groupOwnerAddress = info.groupOwnerAddress
                            MeshLog.i(MeshLogTags.WIFI_DIRECT, "Connecting to group owner: $groupOwnerAddress")
                            
                            val socket = Socket()
                            socket.connect(InetSocketAddress(groupOwnerAddress, 8888), config.connectionTimeoutMs.toInt())
                            
                            val peer = Peer(
                                id = groupOwnerAddress.hostAddress ?: "unknown",
                                name = "WiFi-GO",
                                transport = TransportType.WIFI_DIRECT,
                                linkQuality = LinkQuality.EXCELLENT
                            )
                            
                            val link = WiFiDirectLink(peer, socket)
                            connections[peer.id] = link
                            
                            eventChannel.trySend(TransportEvent.Connected(link))
                            
                            link.startReading { frame ->
                                eventChannel.trySend(TransportEvent.FrameReceived(frame, peer))
                            }
                        }
                    }
                } catch (e: Exception) {
                    MeshLog.e(MeshLogTags.WIFI_DIRECT, "Error establishing connection", e)
                    eventChannel.trySend(TransportEvent.Error(e))
                }
            }
        }
    }
}

/**
 * Wi-Fi Direct link implementation using TCP sockets
 */
class WiFiDirectLink(
    override val peer: Peer,
    private val socket: Socket
) : Link {
    
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    private val connectionState = MutableStateFlow(socket.isConnected)
    private var readingJob: Job? = null
    
    override val isConnected: Boolean
        get() = socket.isConnected && !socket.isClosed
    
    override val linkQuality: LinkQuality
        get() = if (isConnected) LinkQuality.EXCELLENT else LinkQuality.POOR

    init {
        try {
            inputStream = socket.getInputStream()
            outputStream = socket.getOutputStream()
        } catch (e: Exception) {
            MeshLog.e(MeshLogTags.WIFI_DIRECT, "Failed to get socket streams", e)
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
            MeshException.Transport.SendFailed("WiFi Direct send failed", e).toError()
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
                        MeshLog.w(MeshLogTags.WIFI_DIRECT, "Invalid frame length: $frameLength")
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
                        MeshLog.w(MeshLogTags.WIFI_DIRECT, "Failed to decode frame: ${frameResult.errorOrNull()}")
                    }
                }
            } catch (e: Exception) {
                if (isActive) {
                    MeshLog.e(MeshLogTags.WIFI_DIRECT, "Error reading from socket", e)
                }
            } finally {
                connectionState.value = false
            }
        }
    }
}