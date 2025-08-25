package com.appvalence.hayatkurtar.presentation.permissions

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appvalence.hayatkurtar.data.permissions.AndroidPermissionManager
import com.appvalence.hayatkurtar.domain.permissions.MeshPermission

/**
 * Professional permission screen following Material Design guidelines and MVVM architecture.
 * 
 * Features:
 * - Modern Activity Result API instead of deprecated methods
 * - Clean Architecture with domain/data/presentation separation
 * - MVVM pattern with reactive state management
 * - Proper lifecycle handling
 * - Professional UI following Material Design 3
 * - Comprehensive error handling
 * 
 * Based on Android best practices and Clean Architecture principles.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeshPermissionsScreen(
    onPermissionsGranted: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PermissionViewModel = hiltViewModel(),
    androidPermissionManager: AndroidPermissionManager = hiltViewModel<PermissionViewModel>().androidPermissionManager
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    // Modern Activity Result API for permission requests
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        viewModel.handlePermissionResult(permissions)
    }
    
    // Settings launcher for permanently denied permissions
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.handleSettingsResult()
    }
    
    // Register launchers with the permission manager
    LaunchedEffect(Unit) {
        androidPermissionManager.registerLaunchers(permissionLauncher, settingsLauncher)
        activity?.let { viewModel.registerActivity(it) }
    }
    
    // Handle UI events
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is PermissionUiEvent.AllPermissionsGranted -> {
                    onPermissionsGranted()
                }
                else -> {
                    // Handle other events (show snackbar, etc.)
                }
            }
        }
    }
    
    ProfessionalPermissionContent(
        uiState = uiState,
        onRequestPermissions = viewModel::requestAllPermissions,
        onRequestSinglePermission = viewModel::requestPermission,
        onOpenSettings = viewModel::openAppSettings,
        onSkipPermissions = viewModel::skipPermissions,
        modifier = modifier
    )
}

@Composable
private fun ProfessionalPermissionContent(
    uiState: PermissionUiState,
    onRequestPermissions: () -> Unit,
    onRequestSinglePermission: (MeshPermission) -> Unit,
    onOpenSettings: () -> Unit,
    onSkipPermissions: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // App Icon and Title
        Icon(
            imageVector = Icons.Default.Security,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "HayatKurtar İzinleri",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Dynamic description based on state
        Text(
            text = when (uiState) {
                is PermissionUiState.PermanentlyDenied -> 
                    "Bazı izinler kalıcı olarak reddedildi. Uygulamayı kullanmak için lütfen ayarlardan izinleri etkinleştirin."
                is PermissionUiState.NeedsPermissions -> 
                    "Mesh ağ iletişimi için aşağıdaki izinler gereklidir. Bu izinler cihazınızı güvenli şekilde diğer cihazlara bağlamanızı sağlar."
                is PermissionUiState.Error -> 
                    "İzin kontrolünde hata oluştu: ${uiState.message}"
                is PermissionUiState.AllGranted -> 
                    "Tüm izinler verildi! Mesh ağ iletişimi için hazırsınız."
                is PermissionUiState.Loading -> 
                    "İzinler kontrol ediliyor..."
            },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Content based on state
        when (uiState) {
            is PermissionUiState.Loading -> {
                CircularProgressIndicator()
            }
            
            is PermissionUiState.NeedsPermissions -> {
                PermissionCardsSection(
                    permissionDetails = uiState.permissionDetails,
                    onRequestSinglePermission = onRequestSinglePermission
                )
            }
            
            is PermissionUiState.PermanentlyDenied -> {
                PermanentlyDeniedSection(
                    permanentlyDeniedPermissions = uiState.permanentlyDeniedPermissions
                )
            }
            
            is PermissionUiState.AllGranted -> {
                AllGrantedSection()
            }
            
            is PermissionUiState.Error -> {
                ErrorSection(message = uiState.message)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Action Buttons
        ActionButtonsSection(
            uiState = uiState,
            onRequestPermissions = onRequestPermissions,
            onOpenSettings = onOpenSettings,
            onSkipPermissions = onSkipPermissions
        )
    }
}

@Composable
private fun PermissionCardsSection(
    permissionDetails: List<PermissionDetail>,
    onRequestSinglePermission: (MeshPermission) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        permissionDetails.forEach { detail ->
            PermissionCard(
                permissionDetail = detail,
                onRequestPermission = { onRequestSinglePermission(detail.permission) }
            )
        }
    }
}

@Composable
private fun PermissionCard(
    permissionDetail: PermissionDetail,
    onRequestPermission: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
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
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = getPermissionIcon(permissionDetail.icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column {
                        Text(
                            text = permissionDetail.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = permissionDetail.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                if (permissionDetail.isRequired) {
                    AssistChip(
                        onClick = { },
                        label = { Text("Gerekli") }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Button(
                onClick = onRequestPermission,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("İzin Ver")
            }
        }
    }
}

@Composable
private fun PermanentlyDeniedSection(
    permanentlyDeniedPermissions: Set<MeshPermission>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Block,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.error
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "İzinler Kalıcı Olarak Reddedildi",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Aşağıdaki izinler kalıcı olarak reddedildi. Lütfen uygulama ayarlarından manuel olarak etkinleştirin:",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            permanentlyDeniedPermissions.forEach { permission ->
                Text(
                    text = "• ${permission.description}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun AllGrantedSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Tüm İzinler Verildi!",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Mesh ağ iletişimi için gerekli tüm izinler verildi. Artık diğer cihazlarla güvenli iletişim kurabilirsiniz.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ErrorSection(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.error
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Hata Oluştu",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ActionButtonsSection(
    uiState: PermissionUiState,
    onRequestPermissions: () -> Unit,
    onOpenSettings: () -> Unit,
    onSkipPermissions: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when (uiState) {
            is PermissionUiState.NeedsPermissions -> {
                Button(
                    onClick = onRequestPermissions,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Security, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tüm İzinleri Ver")
                }
            }
            
            is PermissionUiState.PermanentlyDenied -> {
                Button(
                    onClick = onOpenSettings,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ayarları Aç")
                }
            }
            
            is PermissionUiState.Error -> {
                Button(
                    onClick = onRequestPermissions,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tekrar Dene")
                }
            }
            
            else -> {
                // No action needed for Loading or AllGranted states
            }
        }
        
        // Skip button (optional, depending on app requirements)
        if (uiState is PermissionUiState.NeedsPermissions) {
            OutlinedButton(
                onClick = onSkipPermissions,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Şimdilik Atla")
            }
        }
    }
}

private fun getPermissionIcon(iconName: String): ImageVector {
    return when (iconName) {
        "bluetooth" -> Icons.Default.Bluetooth
        "wifi" -> Icons.Default.Wifi
        "location" -> Icons.Default.LocationOn
        else -> Icons.Default.Security
    }
}

