package com.appvalence.hayatkurtar.presentation.components

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.core.app.ActivityCompat
import android.app.Activity

@Composable
fun PermissionsGate(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val activity = context as? Activity

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
            val shouldShowRationale = remember {
                requiredPermissions.any { perm ->
                    activity?.let { ActivityCompat.shouldShowRequestPermissionRationale(it, perm) } == true
                }
            }
            Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                Card(colors = CardDefaults.cardColors()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.permissions_reason_title))
                        Spacer(Modifier.height(8.dp))
                        Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.permissions_reason_bullets))
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { requestPermissionsLauncher.launch(requiredPermissions) }) {
                            Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.allow))
                        }
                        if (!shouldShowRationale) {
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = {
                                // Açıkça reddedildiyse veya "bir daha sorma" seçildiyse ayarlara yönlendir
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                                context.startActivity(intent)
                            }) { Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.open_settings)) }
                        }
                    }
                }
            }
        }
        !bluetoothEnabled.value -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp)
            ) {
                Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.bluetooth_off_message))
                Spacer(Modifier.height(12.dp))
                Button(onClick = {
                    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    enableBluetoothLauncher.launch(intent)
                }) {
                    Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.open_bluetooth))
                }
            }
        }
        else -> content()
    }
}


