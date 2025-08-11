package com.appvalence.hayatkurtar.presentation.devices

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.appvalence.hayatkurtar.presentation.chatdetail.components.TelegramColors
import android.app.Activity
import android.companion.AssociationRequest
import android.companion.BluetoothDeviceFilter
import android.companion.CompanionDeviceManager
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import android.bluetooth.BluetoothDevice
import android.content.IntentSender
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloat
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceListScreen(
    onDeviceSelected: (address: String) -> Unit,
    onOpenSettings: () -> Unit,
    onBack: () -> Unit,
    viewModel: DevicesViewModel = hiltViewModel(),
) {
    val haptics = androidx.compose.ui.platform.LocalHapticFeedback.current
    val devices by viewModel.devices.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()
    val isBtEnabled by viewModel.isBluetoothEnabled.collectAsState()
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val openSystemPicker = rememberSystemBluetoothDeviceChooser(onDeviceSelected)
    val showCalibration = rememberSaveable { mutableStateOf(false) }
    val measuredPowerState = rememberSaveable { mutableStateOf(-59f) }
    val pathLossState = rememberSaveable { mutableStateOf(2.0f) }
    val alphaState = rememberSaveable { mutableStateOf(0.5f) }
    val showCompletion = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isScanning, devices) {
        if (!isScanning && devices.isNotEmpty()) {
            showCompletion.value = true
            delay(2000)
            showCompletion.value = false
            // Scan completion feedback
            haptics.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove)
        }
    }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 1.dp,
                color = TelegramColors.Background
            ) {
                TopAppBar(
                    navigationIcon =
                        {
                            IconButton(onClick = onBack, modifier = Modifier.size(48.dp)) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Geri", tint = TelegramColors.Primary)
                            }
                        },
                    title = {
                        Text(
                            androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.devices_title),
                            color = TelegramColors.TextPrimary,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        )
                    },
                    actions = {
                        // Calibration Button
                        OutlinedButton(
                            onClick = { showCalibration.value = true },
                            modifier = Modifier
                                .semantics { contentDescription = "Kalibrasyon" }
                                .padding(end = 8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = TelegramColors.Primary
                            ),
                            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
                        ) {
                            Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.calibration), fontSize = 14.sp)
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = TelegramColors.Background,
                        scrolledContainerColor = TelegramColors.Background
                    )
                )
            }
        },
        containerColor = TelegramColors.Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(TelegramColors.Background)
        ) {
            if (!isBtEnabled) {
                BluetoothOffPlaceholder(onEnable = onOpenSettings)
            } else {
                // Scan Section
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = TelegramColors.Background
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.nearby_devices),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp
                                ),
                                color = TelegramColors.TextPrimary
                            )
                            Text(
                                text = if (isScanning) androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.scanning_devices) else androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.find_bluetooth_devices),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TelegramColors.TextSecondary
                            )
                        }

                        // High performance scan button (Classic + BLE)
                        Button(
                            onClick = {
                                if (!isScanning) {
                                    viewModel.scan()
                                    // Start scan feedback
                                    haptics.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                }
                            },
                            enabled = !isScanning,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TelegramColors.Primary,
                                disabledContainerColor = TelegramColors.Primary.copy(alpha = 0.6f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            if (isScanning) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                                Spacer(Modifier.width(8.dp))
                            } else {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                            }
                            Text(text = if (isScanning) androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.scanning) else androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.advanced_scan), fontSize = 14.sp)
                        }
                    }
                }

                Divider(
                    thickness = 0.5.dp,
                    color = TelegramColors.Divider.copy(alpha = 0.3f)
                )

                // Live scan status banner
                AnimatedVisibility(visible = isScanning) {
                    Surface(color = TelegramColors.Background) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Pulsating radar indicator
                            val infinite = androidx.compose.animation.core.rememberInfiniteTransition(label = "scanPulse")
                            val pulse by infinite.animateFloat(
                                initialValue = 0.85f,
                                targetValue = 1.15f,
                                animationSpec = androidx.compose.animation.core.infiniteRepeatable(
                                    animation = androidx.compose.animation.core.tween(900,
                                        easing = androidx.compose.animation.core.FastOutSlowInEasing
                                    ),
                                    repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
                                ), label = "pulse"
                            )

                            Box(
                                modifier = Modifier
                                    .size((20.dp * pulse).coerceAtLeast(1.dp))
                                    .clip(CircleShape)
                                    .background(TelegramColors.Primary.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size((12.dp * pulse).coerceAtLeast(1.dp))
                                        .clip(CircleShape)
                                        .background(TelegramColors.Primary)
                                )
                            }

                            LinearProgressIndicator(modifier = Modifier.weight(1f))
                            Text(text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.scanning_status_with_count, devices.size))
                        }
                    }
                }

                // Scan completed banner
                AnimatedVisibility(visible = showCompletion.value && !isScanning) {
                    Surface(color = TelegramColors.Background) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TelegramColors.Primary)
                            Text(text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.scan_completed_with_count, devices.size))
                        }
                    }
                }

                // Devices List
                AnimatedVisibility(
                    visible = devices.isNotEmpty(),
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(devices, key = { it.address }) { device ->
                            DeviceListItem(
                                name = device.name ?: device.address,
                                address = device.address,
                                onClick = { onDeviceSelected(device.address) }
                            )

                            Divider(
                                modifier = Modifier.padding(start = 72.dp),
                                thickness = 0.5.dp,
                                color = TelegramColors.Divider.copy(alpha = 0.3f)
                            )
                        }
                    }
                }

                // Empty State
                if (devices.isEmpty() && !isScanning) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = TelegramColors.TextSecondary.copy(alpha = 0.6f)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.no_devices_found),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = TelegramColors.TextPrimary
                            )

                            Text(
                                text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.empty_devices_hint),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TelegramColors.TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
    if (showCalibration.value) {
        CalibrationDialog(
            measuredPower = measuredPowerState.value,
            pathLoss = pathLossState.value,
            alpha = alphaState.value,
            onDismiss = { showCalibration.value = false },
            onApply = { mp, n, a ->
                measuredPowerState.value = mp
                pathLossState.value = n
                alphaState.value = a
                viewModel.calibrate(mp.toInt(), n.toDouble(), a.toDouble())
                showCalibration.value = false
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun rememberSystemBluetoothDeviceChooser(
    onDeviceSelected: (address: String) -> Unit,
): () -> Unit {
    val context = LocalContext.current

    val intentSenderLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val device: BluetoothDevice? = if (Build.VERSION.SDK_INT >= 33) {
                data?.getParcelableExtra(CompanionDeviceManager.EXTRA_DEVICE, BluetoothDevice::class.java)
            } else {
                @Suppress("DEPRECATION")
                data?.getParcelableExtra(CompanionDeviceManager.EXTRA_DEVICE)
            }
            val address = device?.address
            if (!address.isNullOrBlank()) {
                onDeviceSelected(address)
            }
        }
    }

    return remember {
        {
            val cdm = ContextCompat.getSystemService(context, CompanionDeviceManager::class.java)

            if (cdm != null) {
                val filter = BluetoothDeviceFilter.Builder()
                    .build()

                val request = AssociationRequest.Builder()
                    .addDeviceFilter(filter)
                    .setSingleDevice(true)
                    .build()

                cdm.associate(
                    request,
                    object : CompanionDeviceManager.Callback() {
                        override fun onDeviceFound(chooserLauncher: IntentSender) {
                            intentSenderLauncher.launch(
                                IntentSenderRequest.Builder(chooserLauncher).build()
                            )
                        }

                        override fun onFailure(error: CharSequence?) {
                            // no-op
                        }
                    },
                    null
                )
            }
        }
    }
}

@Composable
private fun CalibrationDialog(
    measuredPower: Float,
    pathLoss: Float,
    alpha: Float,
    onDismiss: () -> Unit,
    onApply: (Float, Float, Float) -> Unit,
) {
    var mp = rememberSaveable { mutableStateOf(measuredPower) }
    var n = rememberSaveable { mutableStateOf(pathLoss) }
    var a = rememberSaveable { mutableStateOf(alpha) }

        AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onApply(mp.value, n.value, a.value) }) {
                Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.apply))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.cancel)) }
        },
        title = { Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.calibration_title)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.measured_power_label, mp.value.toInt()))
                Slider(value = mp.value, onValueChange = { mp.value = it }, valueRange = -90f..-30f)
                Spacer(Modifier.height(8.dp))
                Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.path_loss_label, n.value))
                Slider(value = n.value, onValueChange = { n.value = it }, valueRange = 1.5f..4.0f)
                Spacer(Modifier.height(8.dp))
                Text(androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.smoothing_alpha_label, a.value))
                Slider(value = a.value, onValueChange = { a.value = it }, valueRange = 0.0f..1.0f)
            }
        }
    )
}

@Composable
fun DeviceListItem(name: String, address: String, onClick: () -> Unit) {
    Surface(
        color = TelegramColors.Background,
        modifier = Modifier.fillMaxWidth()
    ) {
        val deviceDesc = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.device_content_desc, name)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .semantics { contentDescription = deviceDesc },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Device Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(TelegramColors.Primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = androidx.compose.ui.res.painterResource(id = com.appvalence.hayatkurtar.R.drawable.ic_bluetooth),
                    contentDescription = null,
                    tint = TelegramColors.Primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Device Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = TelegramColors.TextPrimary
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = address,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 13.sp
                    ),
                    color = TelegramColors.TextSecondary
                )
            }

            // Tap anywhere to open chat; no extra button
        }
    }
}

@Composable
fun BluetoothOffPlaceholder(onEnable: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            // Bluetooth Off Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE53E3E).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = null,
                    tint = Color(0xFFE53E3E),
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.bluetooth_off),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp
                ),
                color = TelegramColors.TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.bluetooth_off_hint),
                style = MaterialTheme.typography.bodyMedium,
                color = TelegramColors.TextSecondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            val openBt = androidx.compose.ui.res.stringResource(id = com.appvalence.hayatkurtar.R.string.open_bluetooth)
            Button(
                onClick = onEnable,
                modifier = Modifier.semantics { contentDescription = openBt },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TelegramColors.Primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = openBt,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
