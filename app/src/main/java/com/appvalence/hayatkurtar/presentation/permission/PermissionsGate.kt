package com.appvalence.hayatkurtar.presentation.permission

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// Modern Permission Card Component
@Composable
private fun ModernPermissionCard(
    icon: ImageVector,
    title: String,
    description: String,
    primaryButtonText: String,
    onPrimaryClick: () -> Unit,
    secondaryButtonText: String? = null,
    onSecondaryClick: (() -> Unit)? = null,
    iconColor: Color = Color(0xFF6366F1),
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon with gradient background
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                iconColor.copy(alpha = 0.1f),
                                iconColor.copy(alpha = 0.05f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = iconColor
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Center,
                color = Color(0xFF1F2937)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                textAlign = TextAlign.Center,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Primary Button
            Button(
                onClick = onPrimaryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = iconColor
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp
                )
            ) {
                Text(
                    text = primaryButtonText,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    ),
                    color = Color.White
                )
            }

            // Secondary Button
            if (secondaryButtonText != null && onSecondaryClick != null) {
                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = onSecondaryClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Color(0xFF6B7280)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = secondaryButtonText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            ),
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }
        }
    }
}

// Helper function to get permission state
@Composable
private fun rememberPermissionState(context: Context): Triple<Boolean, Boolean, Array<String>> {
    return remember {
        val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADVERTISE,
            )
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        }

        val hasAllPermissions = requiredPermissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        val isBluetoothEnabled = BluetoothAdapter.getDefaultAdapter()?.isEnabled == true

        Triple(hasAllPermissions, isBluetoothEnabled, requiredPermissions)
    }
}

// Main PermissionsGate Composable
@Composable
fun PermissionsGate(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity

    var (hasPermissions, bluetoothEnabled, requiredPermissions) = rememberPermissionState(context)
    var permissionsGranted by remember { mutableStateOf(hasPermissions) }
    var isBluetoothEnabled by remember { mutableStateOf(bluetoothEnabled) }

    val requestPermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap ->
        permissionsGranted = resultMap.values.all { it }
    }

    val enableBluetoothLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        isBluetoothEnabled = BluetoothAdapter.getDefaultAdapter()?.isEnabled == true
    }

    LaunchedEffect(Unit) {
        if (!permissionsGranted) {
            requestPermissionsLauncher.launch(requiredPermissions)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .systemBarsPadding()
    ) {
        when {
            !permissionsGranted -> {
                val shouldShowRationale = requiredPermissions.any { perm ->
                    activity?.let {
                        ActivityCompat.shouldShowRequestPermissionRationale(it, perm)
                    } == true
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ModernPermissionCard(
                        icon = Icons.Default.Security,
                        title = "İzinler Gerekli",
                        description = "Uygulamanın düzgün çalışması için Bluetooth ve konum izinlerine ihtiyacımız var.",
                        primaryButtonText = "İzin Ver",
                        onPrimaryClick = {
                            requestPermissionsLauncher.launch(requiredPermissions)
                        },
                        secondaryButtonText = if (!shouldShowRationale) "Ayarları Aç" else null,
                        onSecondaryClick = if (!shouldShowRationale) {
                            {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                                context.startActivity(intent)
                            }
                        } else null,
                        iconColor = Color(0xFFEF4444)
                    )
                }
            }

            !isBluetoothEnabled -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ModernPermissionCard(
                        icon = Icons.Default.Bluetooth,
                        title = "Bluetooth Gerekli",
                        description = "Cihazlarla iletişim kurabilmek için Bluetooth'u açmanız gerekiyor.",
                        primaryButtonText = "Bluetooth'u Aç",
                        onPrimaryClick = {
                            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                            enableBluetoothLauncher.launch(intent)
                        },
                        iconColor = Color(0xFF3B82F6)
                    )
                }
            }

            else -> content()
        }
    }
}
