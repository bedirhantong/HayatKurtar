package com.appvalence.hayatkurtar.presentation.system

import android.content.Intent
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appvalence.hayatkurtar.domain.system.*
import com.appvalence.hayatkurtar.data.permissions.AndroidPermissionManager
import com.appvalence.hayatkurtar.domain.permissions.MeshPermission
import com.appvalence.hayatkurtar.presentation.permissions.PermissionViewModel
import com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState
import com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SystemStatusViewModel @Inject constructor(
    private val systemStatusManager: SystemStatusManager
) : ViewModel() {

    val systemStatus = systemStatusManager.getSystemStatus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val discoveredPeers = systemStatusManager.getDiscoveredPeers()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val deviceVisibility = systemStatusManager.getDeviceVisibility()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun requestPermissions() {
        viewModelScope.launch {
            systemStatusManager.requestPermissions()
        }
    }

    fun enableServices() {
        viewModelScope.launch {
            systemStatusManager.enableRequiredServices()
        }
    }

    fun startDiscovery() {
        viewModelScope.launch {
            systemStatusManager.startDiscoveryAndAdvertising()
        }
    }

    fun stopDiscovery() {
        viewModelScope.launch {
            systemStatusManager.stopDiscoveryAndAdvertising()
        }
    }

    fun refreshStatus() {
        viewModelScope.launch {
            // Small delay to allow permission state changes to propagate
            delay(300)
            // Trigger a refresh of system status
            systemStatusManager.requestPermissions()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemStatusScreen(
    onNavigateToChat: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SystemStatusViewModel = hiltViewModel(),
    permissionViewModel: PermissionViewModel = hiltViewModel(),
    androidPermissionManager: AndroidPermissionManager = hiltViewModel<PermissionViewModel>().androidPermissionManager
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    val systemStatus by viewModel.systemStatus.collectAsState()
    val discoveredPeers by viewModel.discoveredPeers.collectAsState()
    val deviceVisibility by viewModel.deviceVisibility.collectAsState()

    // Professional permission management state
    val permissionUiState by permissionViewModel.uiState.collectAsStateWithLifecycle()

    // Modern permission launcher using Activity Result API
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionViewModel.handlePermissionResult(permissions)
        viewModel.refreshStatus()
    }

    // Settings launcher for permanently denied permissions
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        permissionViewModel.handleSettingsResult()
        viewModel.refreshStatus()
    }

    // Service enable launchers
    val bluetoothEnableLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.refreshStatus()
    }

    val wifiEnableLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.refreshStatus()
    }

    // Register launchers with the permission manager
    LaunchedEffect(Unit) {
        androidPermissionManager.registerLaunchers(permissionLauncher, settingsLauncher)
        activity?.let { permissionViewModel.registerActivity(it) }
    }

    // Handle permission events
    LaunchedEffect(Unit) {
        permissionViewModel.uiEvents.collect { event ->
            when (event) {
                is PermissionUiEvent.AllPermissionsGranted -> {
                    viewModel.refreshStatus()
                }
                is PermissionUiEvent.ShowError -> {
                    // Handle permission errors if needed
                }
                else -> {
                    // Handle other events
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "HayatKurtar Mesh Network",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        systemStatus?.let { status ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Overall Status Card
                item {
                    OverallStatusCard(
                        status = status,
                        onStartDiscovery = viewModel::startDiscovery,
                        onStopDiscovery = viewModel::stopDiscovery,
                        onNavigateToChat = onNavigateToChat
                    )
                }

                // System Requirements Section
                item {
                    Text(
                        text = "Sistem Gereksinimleri",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // Permission Status
                item {
                    ProfessionalPermissionStatusCard(
                        permissionStatus = status.permissions,
                        permissionUiState = permissionUiState,
                        onRequestPermissions = {
                            permissionViewModel.requestAllPermissions()
                        },
                        onOpenSettings = {
                            permissionViewModel.openAppSettings()
                        }
                    )
                }

                // Service Status
                item {
                    ServiceStatusCard(
                        serviceStatus = status.services,
                        onEnableServices = { serviceName ->
                            when (serviceName) {
                                "Bluetooth" -> {
                                    val enableBtIntent = Intent(android.bluetooth.BluetoothAdapter.ACTION_REQUEST_ENABLE)
                                    bluetoothEnableLauncher.launch(enableBtIntent)
                                }
                                "Wi-Fi" -> {
                                    val wifiIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
                                    wifiEnableLauncher.launch(wifiIntent)
                                }
                                else -> {
                                    // General settings
                                    val settingsIntent = Intent(Settings.ACTION_SETTINGS)
                                    wifiEnableLauncher.launch(settingsIntent)
                                }
                            }
                        }
                    )
                }

                // Connectivity Section
                item {
                    Text(
                        text = "Bağlantı Durumu",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // Device Visibility
                deviceVisibility?.let { visibility ->
                    item {
                        DeviceVisibilityCard(visibility = visibility)
                    }
                }

                // Discovered Peers
                item {
                    DiscoveredPeersCard(
                        peers = discoveredPeers,
                        discoveryStatus = status.discovery
                    )
                }

                // Connectivity Stats
                item {
                    ConnectivityStatsCard(connectivity = status.connectivity)
                }

                // System Errors (if any)
                if (status.errors.isNotEmpty()) {
                    item {
                        Text(
                            text = "Sistem Uyarıları",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(status.errors) { error ->
                        SystemErrorCard(
                            error = error,
                            onRequestPermissions = if (error.type == ErrorType.PERMISSION_ERROR) {
                                {
                                    permissionViewModel.requestAllPermissions()
                                }
                            } else null
                        )
                    }
                }
            }
        } ?: run {
            // Loading state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun OverallStatusCard(
    status: SystemStatus,
    onStartDiscovery: () -> Unit,
    onStopDiscovery: () -> Unit,
    onNavigateToChat: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (status.overallState) {
                SystemState.READY -> Color(0xFFE8F5E8)
                SystemState.DISCOVERING -> Color(0xFFE3F2FD)
                SystemState.NEEDS_PERMISSIONS -> Color(0xFFFFF3E0)
                SystemState.NEEDS_SERVICES -> Color(0xFFFFF3E0)
                SystemState.ERROR -> Color(0xFFFFEBEE)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Status Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = when (status.overallState) {
                            SystemState.READY -> Color(0xFF4CAF50)
                            SystemState.DISCOVERING -> Color(0xFF2196F3)
                            SystemState.NEEDS_PERMISSIONS -> Color(0xFFFF9800)
                            SystemState.NEEDS_SERVICES -> Color(0xFFFF9800)
                            SystemState.ERROR -> Color(0xFFF44336)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (status.overallState) {
                        SystemState.READY -> Icons.Default.CheckCircle
                        SystemState.DISCOVERING -> Icons.Default.Search
                        SystemState.NEEDS_PERMISSIONS -> Icons.Default.Security
                        SystemState.NEEDS_SERVICES -> Icons.Default.Settings
                        SystemState.ERROR -> Icons.Default.Error
                    },
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Status Text
            Text(
                text = when (status.overallState) {
                    SystemState.READY -> "Mesh Ağı Hazır"
                    SystemState.DISCOVERING -> "Cihazlar Aranıyor"
                    SystemState.NEEDS_PERMISSIONS -> "İzinler Gerekli"
                    SystemState.NEEDS_SERVICES -> "Servisler Gerekli"
                    SystemState.ERROR -> "Sistem Hatası"
                },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when (status.overallState) {
                    SystemState.READY -> "Acil durum iletişimi için hazır. Mesajlaşmaya başlayabilirsiniz."
                    SystemState.DISCOVERING -> "Yakındaki HayatKurtar cihazları aranıyor..."
                    SystemState.NEEDS_PERMISSIONS -> "Bluetooth ve Wi-Fi izinleri gerekli."
                    SystemState.NEEDS_SERVICES -> "Bluetooth ve Wi-Fi servislerini etkinleştirin."
                    SystemState.ERROR -> "Sistem hatası oluştu. Tekrar deneyin."
                },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (status.overallState) {
                    SystemState.READY -> {
                        if (!status.discovery.isDiscovering) {
                            Button(
                                onClick = onStartDiscovery,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Search, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Cihaz Ara")
                            }
                        } else {
                            Button(
                                onClick = onStopDiscovery,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Icon(Icons.Default.Stop, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Aramayı Durdur")
                            }
                        }

                        if (status.connectivity.connectedPeers > 0) {
                            Button(
                                onClick = onNavigateToChat,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Chat, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Sohbet")
                            }
                        }
                    }
                    SystemState.DISCOVERING -> {
                        Button(
                            onClick = onStopDiscovery,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Stop, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Durdur")
                        }
                    }
                    else -> {
                        // Show fix action buttons for other states
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfessionalPermissionStatusCard(
    permissionStatus: PermissionStatus,
    permissionUiState: PermissionUiState,
    onRequestPermissions: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val isHealthy = permissionStatus.allGranted
    val statusText = when {
        permissionUiState is PermissionUiState.Loading -> "İzinler kontrol ediliyor..."
        permissionUiState is PermissionUiState.AllGranted -> "Tüm izinler verildi"
        permissionUiState is PermissionUiState.PermanentlyDenied -> "Bazı izinler kalıcı olarak reddedildi"
        permissionUiState is PermissionUiState.NeedsPermissions -> "${permissionUiState.missingPermissions.size} izin eksik"
        permissionUiState is PermissionUiState.Error -> "İzin hatası: ${permissionUiState.message}"
        else -> if (isHealthy) "Tüm izinler verildi" else "Eksik izinler var"
    }

    val actionText = when (permissionUiState) {
        is PermissionUiState.PermanentlyDenied -> "Ayarları Aç"
        is PermissionUiState.NeedsPermissions -> "İzin Ver"
        is PermissionUiState.Error -> "Tekrar Dene"
        else -> if (!isHealthy) "İzin Ver" else null
    }

    val onAction = when (permissionUiState) {
        is PermissionUiState.PermanentlyDenied -> onOpenSettings
        is PermissionUiState.NeedsPermissions -> onRequestPermissions
        is PermissionUiState.Error -> onRequestPermissions
        else -> if (!isHealthy) onRequestPermissions else null
    }

    StatusCard(
        title = "İzinler",
        icon = Icons.Default.Security,
        isHealthy = isHealthy,
        statusText = statusText,
        actionText = actionText,
        onAction = onAction
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            when (permissionUiState) {
                is PermissionUiState.Loading -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Kontrol ediliyor...",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                is PermissionUiState.NeedsPermissions -> {
                    Text(
                        text = "Eksik izinler:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                    permissionUiState.permissionDetails.take(3).forEach { detail ->
                        Text(
                            text = "• ${detail.title}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (permissionUiState.permissionDetails.size > 3) {
                        Text(
                            text = "ve ${permissionUiState.permissionDetails.size - 3} izin daha...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                is PermissionUiState.PermanentlyDenied -> {
                    Text(
                        text = "Kalıcı olarak reddedilen izinler:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.error
                    )
                    permissionUiState.permanentlyDeniedPermissions.forEach { permission ->
                        Text(
                            text = "• ${permission.description}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                is PermissionUiState.AllGranted -> {
                    PermissionItem("Bluetooth", com.appvalence.hayatkurtar.domain.system.PermissionState.GRANTED)
                    PermissionItem("Wi-Fi Direct", com.appvalence.hayatkurtar.domain.system.PermissionState.GRANTED)
                    PermissionItem("Konum", com.appvalence.hayatkurtar.domain.system.PermissionState.GRANTED)
                }

                is PermissionUiState.Error -> {
                    Text(
                        text = permissionUiState.message,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            // Fallback to old permission display if new system is not working
            if (permissionUiState is PermissionUiState.Loading) {
                PermissionItem("Bluetooth", permissionStatus.bluetooth)
                PermissionItem("Wi-Fi Direct", permissionStatus.wifiDirect)
                PermissionItem("Konum", permissionStatus.location)
            }
        }
    }
}

@Composable
private fun PermissionStatusCard(
    permissionStatus: PermissionStatus,
    onRequestPermissions: () -> Unit
) {
    StatusCard(
        title = "İzinler",
        icon = Icons.Default.Security,
        isHealthy = permissionStatus.allGranted,
        statusText = if (permissionStatus.allGranted) "Tüm izinler verildi" else "Eksik izinler var",
        actionText = if (!permissionStatus.allGranted) "İzin Ver" else null,
        onAction = if (!permissionStatus.allGranted) onRequestPermissions else null
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PermissionItem("Bluetooth", permissionStatus.bluetooth)
            PermissionItem("Wi-Fi Direct", permissionStatus.wifiDirect)
            PermissionItem("Konum", permissionStatus.location)
        }
    }
}

@Composable
private fun ServiceStatusCard(
    serviceStatus: ServiceStatus,
    onEnableServices: (String) -> Unit
) {
    StatusCard(
        title = "Servisler",
        icon = Icons.Default.Settings,
        isHealthy = serviceStatus.allEnabled,
        statusText = if (serviceStatus.allEnabled) "Tüm servisler aktif" else "Eksik servisler var",
        actionText = if (!serviceStatus.allEnabled) "Servisleri Aç" else null,
        onAction = if (!serviceStatus.allEnabled) {
            {
                // Enable the first disabled service
                when {
                    serviceStatus.bluetooth != ServiceState.ENABLED -> onEnableServices("Bluetooth")
                    serviceStatus.wifi != ServiceState.ENABLED -> onEnableServices("Wi-Fi")
                    else -> onEnableServices("Settings")
                }
            }
        } else null
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ServiceItem("Bluetooth", serviceStatus.bluetooth)
            ServiceItem("Wi-Fi", serviceStatus.wifi)
            ServiceItem("Konum", serviceStatus.location)
        }
    }
}

@Composable
private fun DeviceVisibilityCard(visibility: DeviceVisibilityStatus) {
    StatusCard(
        title = "Cihaz Görünürlüğü",
        icon = Icons.Default.Visibility,
        isHealthy = visibility.isVisible,
        statusText = if (visibility.isVisible) "Keşfedilebilir" else "Gizli"
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Cihaz Adı:")
                Text(
                    text = visibility.deviceName,
                    fontWeight = FontWeight.Medium
                )
            }

            if (visibility.isVisible) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Sinyal Gücü:")
                    Text(
                        text = "${visibility.advertisementStrength}%",
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Erişim Mesafesi:")
                    Text(
                        text = when (visibility.visibilityRange) {
                            VisibilityRange.SHORT -> "~10m"
                            VisibilityRange.MEDIUM -> "~30m"
                            VisibilityRange.LONG -> "~100m"
                        },
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun DiscoveredPeersCard(
    peers: List<DiscoveredPeer>,
    discoveryStatus: DiscoveryStatus
) {
    StatusCard(
        title = "Keşfedilen Cihazlar",
        icon = Icons.Default.Devices,
        isHealthy = peers.isNotEmpty(),
        statusText = "${peers.size} cihaz bulundu"
    ) {
        if (peers.isEmpty()) {
            Text(
                text = if (discoveryStatus.isDiscovering) {
                    "Cihazlar aranıyor..."
                } else {
                    "Henüz cihaz bulunamadı. Aramayı başlatın."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                peers.take(5).forEach { peer -> // Show max 5 peers
                    PeerItem(peer = peer)
                }
                if (peers.size > 5) {
                    Text(
                        text = "ve ${peers.size - 5} cihaz daha...",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ConnectivityStatsCard(connectivity: ConnectivityStatus) {
    StatusCard(
        title = "Bağlantı İstatistikleri",
        icon = Icons.Default.NetworkCheck,
        isHealthy = connectivity.isConnectedToMesh,
        statusText = if (connectivity.isConnectedToMesh) {
            "Mesh ağına bağlı"
        } else {
            "Mesh ağına bağlı değil"
        }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Bağlı Cihazlar:")
                Text(
                    text = connectivity.connectedPeers.toString(),
                    fontWeight = FontWeight.Medium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Ağ Boyutu:")
                Text(
                    text = connectivity.meshNetworkSize.toString(),
                    fontWeight = FontWeight.Medium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Bağlantı Kalitesi:")
                ConnectionQualityIndicator(quality = connectivity.connectionQuality)
            }
        }
    }
}

@Composable
private fun StatusCard(
    title: String,
    icon: ImageVector,
    isHealthy: Boolean,
    statusText: String,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isHealthy) {
                MaterialTheme.colorScheme.surface
            } else {
                Color(0xFFFFF3E0)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isHealthy) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color(0xFFFF9800)
                        },
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = statusText,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (actionText != null && onAction != null) {
                    TextButton(onClick = onAction) {
                        Text(actionText)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun PermissionItem(name: String, state: PermissionState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name)
        StatusIndicator(
            isHealthy = state == PermissionState.GRANTED,
            text = when (state) {
                PermissionState.GRANTED -> "Verildi"
                PermissionState.DENIED -> "Reddedildi"
                PermissionState.NOT_DETERMINED -> "Belirsiz"
                PermissionState.PERMANENTLY_DENIED -> "Kalıcı Red"
            }
        )
    }
}

@Composable
private fun ServiceItem(name: String, state: ServiceState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name)
        StatusIndicator(
            isHealthy = state == ServiceState.ENABLED,
            text = when (state) {
                ServiceState.ENABLED -> "Aktif"
                ServiceState.DISABLED -> "Devre Dışı"
                ServiceState.UNAVAILABLE -> "Kullanılamaz"
                ServiceState.ENABLING -> "Etkinleştiriliyor"
                ServiceState.ERROR -> "Hata"
            }
        )
    }
}

@Composable
private fun PeerItem(peer: DiscoveredPeer) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = peer.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${peer.transportType} • ${peer.distance}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = "${peer.signalStrength}%",
            style = MaterialTheme.typography.bodySmall,
            color = when {
                peer.signalStrength >= 70 -> Color(0xFF4CAF50)
                peer.signalStrength >= 40 -> Color(0xFFFF9800)
                else -> Color(0xFFF44336)
            }
        )
    }
}

@Composable
private fun ConnectionQualityIndicator(quality: ConnectionQuality) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val (color, text) = when (quality) {
            ConnectionQuality.EXCELLENT -> Color(0xFF4CAF50) to "Mükemmel"
            ConnectionQuality.GOOD -> Color(0xFF8BC34A) to "İyi"
            ConnectionQuality.FAIR -> Color(0xFFFF9800) to "Orta"
            ConnectionQuality.POOR -> Color(0xFFF44336) to "Zayıf"
            ConnectionQuality.NONE -> Color(0xFF9E9E9E) to "Yok"
        }

        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun StatusIndicator(isHealthy: Boolean, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = if (isHealthy) Color(0xFF4CAF50) else Color(0xFFF44336),
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = if (isHealthy) Color(0xFF4CAF50) else Color(0xFFF44336),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun SystemErrorCard(
    error: SystemError,
    onRequestPermissions: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (error.type) {
                            ErrorType.PERMISSION_ERROR -> Icons.Default.Security
                            ErrorType.SERVICE_ERROR -> Icons.Default.Settings
                            ErrorType.HARDWARE_ERROR -> Icons.Default.Hardware
                            ErrorType.NETWORK_ERROR -> Icons.Default.NetworkCheck
                            ErrorType.UNKNOWN_ERROR -> Icons.Default.Error
                        },
                        contentDescription = null,
                        tint = Color(0xFFF44336),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = error.message,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Add permission request button for permission errors
                if (error.type == ErrorType.PERMISSION_ERROR && onRequestPermissions != null) {
                    Button(
                        onClick = onRequestPermissions,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("İzin Ver")
                    }
                }
            }

            error.suggestedAction?.let { action ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Önerilen eylem: $action",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

