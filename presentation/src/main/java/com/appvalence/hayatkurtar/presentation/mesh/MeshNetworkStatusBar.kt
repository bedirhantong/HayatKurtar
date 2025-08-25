package com.appvalence.hayatkurtar.presentation.mesh

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appvalence.hayatkurtar.domain.mesh.MeshStats
import com.appvalence.hayatkurtar.domain.transport.TransportStats
import com.appvalence.hayatkurtar.domain.transport.TransportType

/**
 * Network status bar showing real-time mesh network information
 */
@Composable
fun MeshNetworkStatusBar(
    meshStats: MeshStats,
    transportStats: Map<TransportType, TransportStats>,
    isServiceRunning: Boolean,
    onStatusClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(
            containerColor = getStatusColor(meshStats, isServiceRunning)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Compact status bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status indicator
                StatusIndicator(
                    isConnected = meshStats.connectedPeers > 0,
                    isServiceRunning = isServiceRunning
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = getStatusText(meshStats, isServiceRunning),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    if (isServiceRunning) {
                        Text(
                            text = getDetailsText(meshStats, transportStats),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Quick actions
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (meshStats.connectedPeers > 0) {
                        QuickActionButton(
                            icon = Icons.Default.Message,
                            contentDescription = "Send Message",
                            onClick = { /* Handle send message */ }
                        )
                    }
                    
                    QuickActionButton(
                        icon = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        onClick = { isExpanded = !isExpanded }
                    )
                }
            }
            
            // Expanded details
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                ExpandedStatusDetails(
                    meshStats = meshStats,
                    transportStats = transportStats,
                    onStatusClick = onStatusClick
                )
            }
        }
    }
}

@Composable
private fun StatusIndicator(
    isConnected: Boolean,
    isServiceRunning: Boolean,
    modifier: Modifier = Modifier
) {
    val (color, icon) = when {
        !isServiceRunning -> Pair(Color.Gray, Icons.Default.PowerSettingsNew)
        isConnected -> Pair(Color.Green, Icons.Default.BluetoothConnected)
        else -> Pair(Color(0xFFFFA500), Icons.Default.BluetoothSearching)
    }
    
    Box(
        modifier = modifier
            .size(40.dp)
            .background(color.copy(alpha = 0.2f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        
        // Animated pulse for searching
        if (isServiceRunning && !isConnected) {
            var isVisible by remember { mutableStateOf(false) }
            
            LaunchedEffect(Unit) {
                while (true) {
                    isVisible = !isVisible
                    kotlinx.coroutines.delay(1000)
                }
            }
            
            AnimatedVisibility(
                visible = isVisible,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color.copy(alpha = 0.1f), CircleShape)
                )
            }
        }
    }
}

@Composable
private fun QuickActionButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(32.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun ExpandedStatusDetails(
    meshStats: MeshStats,
    transportStats: Map<TransportType, TransportStats>,
    onStatusClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
    ) {
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
        )
        
        // Mesh statistics
        StatsRow(
            label = "Messages",
            value = "${meshStats.messagesReceived}↓ ${meshStats.messagesSent}↑ ${meshStats.messagesForwarded}→"
        )
        
        StatsRow(
            label = "Network",
            value = "${meshStats.connectedPeers} peers, ${meshStats.knownRoutes} routes"
        )
        
        if (meshStats.duplicatesBlocked > 0) {
            StatsRow(
                label = "Duplicates blocked",
                value = meshStats.duplicatesBlocked.toString()
            )
        }
        
        if (meshStats.averageLatencyMs > 0) {
            StatsRow(
                label = "Avg latency",
                value = "${meshStats.averageLatencyMs.toInt()}ms"
            )
        }
        
        // Transport details
        if (transportStats.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            
            transportStats.forEach { (transportType, stats) ->
                TransportStatusCard(
                    transportType = transportType,
                    stats = stats
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onStatusClick,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Settings")
            }
            
            OutlinedButton(
                onClick = { /* Handle diagnostics */ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Analytics,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Diagnostics")
            }
        }
    }
}

@Composable
private fun StatsRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun TransportStatusCard(
    transportType: TransportType,
    stats: TransportStats,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getTransportIcon(transportType),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (stats.isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transportType.name.replace("_", " "),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                if (stats.isActive) {
                    Text(
                        text = "${stats.connectedPeers} connected, ${stats.messagesSent} sent",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Text(
                        text = "Inactive",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            if (stats.isActive) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Green, CircleShape)
                )
            }
        }
    }
}

private fun getStatusColor(meshStats: MeshStats, isServiceRunning: Boolean): Color {
    return when {
        !isServiceRunning -> Color.Gray.copy(alpha = 0.1f)
        meshStats.connectedPeers > 0 -> Color.Green.copy(alpha = 0.1f)
        else -> Color(0xFFFFA500).copy(alpha = 0.1f)
    }
}

private fun getStatusText(meshStats: MeshStats, isServiceRunning: Boolean): String {
    return when {
        !isServiceRunning -> "Mesh Network Offline"
        meshStats.connectedPeers > 0 -> "Connected to ${meshStats.connectedPeers} peer${if (meshStats.connectedPeers > 1) "s" else ""}"
        else -> "Searching for peers..."
    }
}

private fun getDetailsText(meshStats: MeshStats, transportStats: Map<TransportType, TransportStats>): String {
    val activeTransports = transportStats.values.count { it.isActive }
    return buildString {
        append("$activeTransports transport${if (activeTransports != 1) "s" else ""} active")
        if (meshStats.messagesReceived > 0 || meshStats.messagesSent > 0) {
            append(" • ${meshStats.messagesReceived + meshStats.messagesSent} messages")
        }
    }
}

private fun getTransportIcon(transportType: TransportType): ImageVector {
    return when (transportType) {
        TransportType.BLUETOOTH_CLASSIC -> Icons.Default.Bluetooth
        TransportType.WIFI_DIRECT -> Icons.Default.Wifi
        TransportType.UNKNOWN -> Icons.Default.DeviceUnknown
    }
}