package com.appvalence.hayatkurtar.data.mesh.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.appvalence.hayatkurtar.core.logging.MeshLog
import com.appvalence.hayatkurtar.core.logging.MeshLogTags
import com.appvalence.hayatkurtar.core.result.MeshResult
import com.appvalence.hayatkurtar.domain.mesh.MeshEvent
import com.appvalence.hayatkurtar.domain.mesh.MeshRouter
import com.appvalence.hayatkurtar.domain.mesh.MeshStats
import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Foreground service for continuous mesh networking operations
 * Handles background scanning, connection management, and battery optimization
 */
@AndroidEntryPoint
class MeshNetworkService : Service() {

    companion object {
        const val ACTION_START_MESH = "START_MESH"
        const val ACTION_STOP_MESH = "STOP_MESH"
        const val ACTION_SEND_EMERGENCY = "SEND_EMERGENCY"
        
        const val EXTRA_EMERGENCY_MESSAGE = "emergency_message"
        const val EXTRA_LOCATION = "location"
        
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "mesh_network_service"
        
        fun startService(context: Context) {
            val intent = Intent(context, MeshNetworkService::class.java).apply {
                action = ACTION_START_MESH
            }
            ContextCompat.startForegroundService(context, intent)
        }
        
        fun stopService(context: Context) {
            val intent = Intent(context, MeshNetworkService::class.java).apply {
                action = ACTION_STOP_MESH
            }
            context.startService(intent)
        }
        
        fun sendEmergencyMessage(context: Context, message: String, location: String? = null) {
            val intent = Intent(context, MeshNetworkService::class.java).apply {
                action = ACTION_SEND_EMERGENCY
                putExtra(EXTRA_EMERGENCY_MESSAGE, message)
                putExtra(EXTRA_LOCATION, location)
            }
            context.startService(intent)
        }
    }

    @Inject
    lateinit var meshRouter: MeshRouter
    
    @Inject
    lateinit var transportMultiplexer: TransportMultiplexer
    
    private val serviceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val binder = MeshServiceBinder()
    
    // Service state
    private var isServiceRunning = false
    private var wakeLock: PowerManager.WakeLock? = null
    
    // Statistics and monitoring
    private var lastStats = MeshStats()
    private val serviceStats = ServiceStats()
    
    // Battery optimization
    private var batteryOptimizationLevel = BatteryOptimizationLevel.NORMAL
    private var lastScanTime = 0L
    private val scanIntervals = mapOf(
        BatteryOptimizationLevel.AGGRESSIVE to 60_000L, // 1 minute
        BatteryOptimizationLevel.NORMAL to 30_000L,     // 30 seconds  
        BatteryOptimizationLevel.PERFORMANCE to 10_000L // 10 seconds
    )

    override fun onCreate() {
        super.onCreate()
        MeshLog.i(MeshLogTags.SERVICE, "Mesh network service created")
        
        createNotificationChannel()
        acquireWakeLock()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_MESH -> startMeshNetworking()
            ACTION_STOP_MESH -> stopMeshNetworking()
            ACTION_SEND_EMERGENCY -> handleEmergencyMessage(intent)
        }
        
        return START_STICKY // Restart if killed by system
    }

    override fun onBind(intent: Intent): IBinder = binder

    override fun onDestroy() {
        super.onDestroy()
        MeshLog.i(MeshLogTags.SERVICE, "Mesh network service destroyed")
        
        stopMeshNetworking()
        releaseWakeLock()
        serviceScope.cancel()
    }

    private fun startMeshNetworking() {
        if (isServiceRunning) {
            MeshLog.d(MeshLogTags.SERVICE, "Mesh networking already running")
            return
        }
        
        MeshLog.i(MeshLogTags.SERVICE, "Starting mesh networking")
        
        startForeground(NOTIFICATION_ID, createNotification())
        
        serviceScope.launch {
            try {
                // Start mesh router and transport multiplexer
                meshRouter.start()
                transportMultiplexer.start()
                
                isServiceRunning = true
                serviceStats.startTime = System.currentTimeMillis()
                
                // Start monitoring and optimization tasks
                startPeriodicTasks()
                startEventMonitoring()
                startBatteryOptimization()
                
                MeshLog.i(MeshLogTags.SERVICE, "Mesh networking started successfully")
                
            } catch (e: Exception) {
                MeshLog.e(MeshLogTags.SERVICE, "Failed to start mesh networking", e)
                stopSelf()
            }
        }
    }

    private fun stopMeshNetworking() {
        if (!isServiceRunning) {
            MeshLog.d(MeshLogTags.SERVICE, "Mesh networking not running")
            return
        }
        
        MeshLog.i(MeshLogTags.SERVICE, "Stopping mesh networking")
        
        serviceScope.launch {
            try {
                meshRouter.stop()
                transportMultiplexer.stop()
                
                isServiceRunning = false
                MeshLog.i(MeshLogTags.SERVICE, "Mesh networking stopped")
                
            } catch (e: Exception) {
                MeshLog.e(MeshLogTags.SERVICE, "Error stopping mesh networking", e)
            } finally {
                stopForeground(true)
                stopSelf()
            }
        }
    }

    private fun handleEmergencyMessage(intent: Intent) {
        val message = intent.getStringExtra(EXTRA_EMERGENCY_MESSAGE)
        val location = intent.getStringExtra(EXTRA_LOCATION)
        
        if (message == null) {
            MeshLog.w(MeshLogTags.SERVICE, "Emergency message is null")
            return
        }
        
        serviceScope.launch {
            try {
                val emergencyText = buildString {
                    append("🆘 EMERGENCY: ")
                    append(message)
                    if (location != null) {
                        append(" | Location: $location")
                    }
                    append(" | Time: ${System.currentTimeMillis()}")
                }
                
                val result = meshRouter.sendMessage(
                    content = emergencyText.toByteArray(Charsets.UTF_8),
                    priority = com.appvalence.hayatkurtar.core.protocol.Priority.HIGH,
                    ttl = 10 // Higher TTL for emergency messages
                )
                
                when (result) {
                    is MeshResult.Success -> {
                        MeshLog.i(MeshLogTags.SERVICE, "Emergency message sent: ${result.data}")
                        serviceStats.emergencyMessagesSent++
                        updateNotification("Emergency message sent")
                    }
                    is MeshResult.Error -> {
                        MeshLog.e(MeshLogTags.SERVICE, "Failed to send emergency message: ${result.exception}")
                        updateNotification("Failed to send emergency message")
                    }
                }
                
            } catch (e: Exception) {
                MeshLog.e(MeshLogTags.SERVICE, "Error sending emergency message", e)
            }
        }
    }

    private fun startPeriodicTasks() {
        // Statistics update task
        serviceScope.launch {
            while (isServiceRunning) {
                delay(5000) // Every 5 seconds
                updateServiceStatistics()
                updateNotification()
            }
        }
        
        // Cleanup task
        serviceScope.launch {
            while (isServiceRunning) {
                delay(300_000) // Every 5 minutes
                performCleanup()
            }
        }
        
        // Health check task
        serviceScope.launch {
            while (isServiceRunning) {
                delay(60_000) // Every minute
                performHealthCheck()
            }
        }
    }

    private fun startEventMonitoring() {
        serviceScope.launch {
            meshRouter.events.collect { event ->
                handleMeshEvent(event)
            }
        }
    }

    private fun startBatteryOptimization() {
        serviceScope.launch {
            while (isServiceRunning) {
                val interval = scanIntervals[batteryOptimizationLevel] ?: 30_000L
                delay(interval)
                
                // Adaptive scanning based on activity and battery level
                if (shouldPerformScan()) {
                    performAdaptiveScan()
                }
            }
        }
    }

    private fun handleMeshEvent(event: MeshEvent) {
        when (event) {
            is MeshEvent.MessageReceived -> {
                serviceStats.messagesReceived++
                MeshLog.d(MeshLogTags.SERVICE, "Message received from ${event.fromPeer.id}")
                
                // Check if it's an emergency message
                val content = String(event.message.content, Charsets.UTF_8)
                if (content.contains("🆘 EMERGENCY")) {
                    showEmergencyNotification(content, event.fromPeer.name ?: event.fromPeer.id)
                }
            }
            is MeshEvent.MessageDelivered -> {
                serviceStats.messagesDelivered++
            }
            is MeshEvent.MessageForwarded -> {
                serviceStats.messagesForwarded++
            }
            is MeshEvent.NetworkStateChanged -> {
                serviceStats.connectedPeers = event.connectedPeers
                serviceStats.knownRoutes = event.knownRoutes
                
                // Adjust battery optimization based on network activity
                adjustBatteryOptimization(event.connectedPeers)
            }
            is MeshEvent.MessageDropped -> {
                serviceStats.messagesDropped++
                MeshLog.w(MeshLogTags.SERVICE, "Message dropped: ${event.reason}")
            }
            else -> {
                // Handle other events
            }
        }
    }

    private fun shouldPerformScan(): Boolean {
        val currentTime = System.currentTimeMillis()
        val timeSinceLastScan = currentTime - lastScanTime
        val interval = scanIntervals[batteryOptimizationLevel] ?: 30_000L
        
        return timeSinceLastScan >= interval
    }

    private suspend fun performAdaptiveScan() {
        try {
            lastScanTime = System.currentTimeMillis()
            
            // Start peer discovery if no active connections
            if (serviceStats.connectedPeers == 0) {
                transportMultiplexer.discoverPeers().take(1).collect { peer ->
                    MeshLog.d(MeshLogTags.SERVICE, "Discovered peer: ${peer.id}")
                    
                    // Attempt connection if no existing connections
                    if (serviceStats.connectedPeers == 0) {
                        transportMultiplexer.connectToPeer(peer)
                    }
                }
            }
            
        } catch (e: Exception) {
            MeshLog.e(MeshLogTags.SERVICE, "Error in adaptive scan", e)
        }
    }

    private fun adjustBatteryOptimization(connectedPeers: Int) {
        val newLevel = when {
            connectedPeers == 0 -> BatteryOptimizationLevel.AGGRESSIVE // No connections, save battery
            connectedPeers >= 3 -> BatteryOptimizationLevel.PERFORMANCE // Good connectivity, maintain performance
            else -> BatteryOptimizationLevel.NORMAL // Balanced approach
        }
        
        if (newLevel != batteryOptimizationLevel) {
            batteryOptimizationLevel = newLevel
            MeshLog.i(MeshLogTags.SERVICE, "Battery optimization level changed to: $newLevel")
        }
    }

    private suspend fun updateServiceStatistics() {
        val currentStats = meshRouter.getStats()
        lastStats = currentStats
        serviceStats.updateFromMeshStats(currentStats)
    }

    private suspend fun performCleanup() {
        try {
            // Let the mesh router handle its cleanup
            // This could include cleaning up expired messages, old routes, etc.
            MeshLog.d(MeshLogTags.SERVICE, "Performing periodic cleanup")
        } catch (e: Exception) {
            MeshLog.e(MeshLogTags.SERVICE, "Error during cleanup", e)
        }
    }

    private suspend fun performHealthCheck() {
        try {
            val stats = meshRouter.getStats()
            val transportStats = transportMultiplexer.getTransportStats()
            
            // Check if mesh router is healthy
            val isHealthy = stats.connectedPeers > 0 || 
                           transportStats.values.any { it.isActive }
            
            if (!isHealthy) {
                MeshLog.w(MeshLogTags.SERVICE, "Mesh network appears unhealthy, attempting recovery")
                // Attempt recovery by restarting discovery
                performAdaptiveScan()
            }
            
            serviceStats.lastHealthCheck = System.currentTimeMillis()
            
        } catch (e: Exception) {
            MeshLog.e(MeshLogTags.SERVICE, "Error during health check", e)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Mesh Network Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Background mesh networking operations"
                setShowBadge(false)
                enableLights(false)
                enableVibration(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(content: String = "Mesh network active"): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            packageManager.getLaunchIntentForPackage(packageName),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val stopIntent = PendingIntent.getService(
            this,
            0,
            Intent(this, MeshNetworkService::class.java).apply { action = ACTION_STOP_MESH },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("HayatKurtar Mesh Network")
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with app icon
            .setContentIntent(pendingIntent)
            .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Stop", stopIntent)
            .setOngoing(true)
            .setShowWhen(true)
            .build()
    }

    private fun updateNotification(content: String? = null) {
        val notificationText = content ?: buildString {
            append("Connected: ${serviceStats.connectedPeers}")
            if (serviceStats.messagesReceived > 0) {
                append(" | Received: ${serviceStats.messagesReceived}")
            }
        }
        
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, createNotification(notificationText))
    }

    private fun showEmergencyNotification(message: String, fromPeer: String) {
        val emergencyNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("🆘 Emergency Message")
            .setContentText("From: $fromPeer")
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(2000 + fromPeer.hashCode(), emergencyNotification)
    }

    private fun acquireWakeLock() {
        wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "HayatKurtar::MeshNetworkService").apply {
                acquire(10 * 60 * 1000L) // 10 minutes
            }
        }
    }

    private fun releaseWakeLock() {
        wakeLock?.let {
            if (it.isHeld) {
                it.release()
            }
        }
        wakeLock = null
    }

    /**
     * Binder for service communication
     */
    inner class MeshServiceBinder : Binder() {
        fun getService(): MeshNetworkService = this@MeshNetworkService
    }

    /**
     * Get current service statistics
     */
    fun getServiceStats(): ServiceStats = serviceStats.copy()

    /**
     * Get current mesh statistics
     */
    fun getCurrentMeshStats(): MeshStats = lastStats

    /**
     * Check if service is running
     */
    fun isRunning(): Boolean = isServiceRunning
}

/**
 * Battery optimization levels for adaptive behavior
 */
enum class BatteryOptimizationLevel {
    AGGRESSIVE,    // Minimal scanning, maximum battery saving
    NORMAL,        // Balanced approach
    PERFORMANCE    // Frequent scanning, optimal connectivity
}

/**
 * Service-specific statistics
 */
data class ServiceStats(
    var startTime: Long = 0,
    var messagesReceived: Long = 0,
    var messagesDelivered: Long = 0,
    var messagesForwarded: Long = 0,
    var messagesDropped: Long = 0,
    var emergencyMessagesSent: Long = 0,
    var connectedPeers: Int = 0,
    var knownRoutes: Int = 0,
    var lastHealthCheck: Long = 0
) {
    fun updateFromMeshStats(meshStats: MeshStats) {
        messagesReceived = meshStats.messagesReceived
        messagesDelivered = meshStats.messagesSent
        messagesForwarded = meshStats.messagesForwarded
        messagesDropped = meshStats.messagesDropped
        connectedPeers = meshStats.connectedPeers
        knownRoutes = meshStats.knownRoutes
    }
    
    fun getUptimeMs(): Long = if (startTime > 0) System.currentTimeMillis() - startTime else 0
}