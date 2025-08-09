package com.appvalence.hayatkurtar.presentation.components

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager

@Composable
fun PermissionsGate(content: @Composable () -> Unit) {
    val context = LocalContext.current

    val requiredPermissions = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
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
    }

    fun hasAllPermissions(): Boolean = requiredPermissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    val permissionsGranted = remember { mutableStateOf(hasAllPermissions()) }
    val bluetoothEnabled = remember { mutableStateOf(BluetoothAdapter.getDefaultAdapter()?.isEnabled == true) }

    val requestPermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap ->
        permissionsGranted.value = resultMap.values.all { it }
    }

    val enableBluetoothLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        bluetoothEnabled.value = BluetoothAdapter.getDefaultAdapter()?.isEnabled == true
    }

    LaunchedEffect(Unit) {
        if (!permissionsGranted.value) {
            requestPermissionsLauncher.launch(requiredPermissions)
        }
    }

    when {
        !permissionsGranted.value -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp)
            ) {
                Text("Bluetooth izinlerine ihtiyaç var")
                Spacer(Modifier.height(12.dp))
                Button(onClick = { requestPermissionsLauncher.launch(requiredPermissions) }) {
                    Text("İzin ver")
                }
            }
        }
        !bluetoothEnabled.value -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp)
            ) {
                Text("Bluetooth kapalı. Devam etmek için açınız.")
                Spacer(Modifier.height(12.dp))
                Button(onClick = {
                    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    enableBluetoothLauncher.launch(intent)
                }) {
                    Text("Bluetooth'u Aç")
                }
            }
        }
        else -> content()
    }
}


