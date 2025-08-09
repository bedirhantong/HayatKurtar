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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceListScreen(
    onDeviceSelected: (address: String) -> Unit,
    onOpenSettings: () -> Unit,
    onBack: () -> Unit,
    viewModel: DevicesViewModel = hiltViewModel(),
) {
    val devices by viewModel.devices.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()
    val isBtEnabled by viewModel.isBluetoothEnabled.collectAsState()
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

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
                            "Cihazlar",
                            color = TelegramColors.TextPrimary,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        )
                    },
                    actions = {
                        // Settings Button
                        OutlinedButton(
                            onClick = onOpenSettings,
                            modifier = Modifier
                                .semantics { contentDescription = "Ayarlar" }
                                .padding(end = 4.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = TelegramColors.Primary
                            ),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                width = 1.dp
                            )
                        ) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Ayarlar", fontSize = 14.sp)
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
                                text = "Yakındaki Cihazlar",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp
                                ),
                                color = TelegramColors.TextPrimary
                            )
                            Text(
                                text = if (isScanning) "Cihazlar aranıyor..." else "Bluetooth cihazlarını bul",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TelegramColors.TextSecondary
                            )
                        }

                        // Scan Button
                        Button(
                            onClick = { if (!isScanning) viewModel.scan() },
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
                            Text(
                                text = if (isScanning) "Taranıyor" else "Tara",
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Divider(
                    thickness = 0.5.dp,
                    color = TelegramColors.Divider.copy(alpha = 0.3f)
                )

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
                                name = device.name ?: "Bilinmeyen Cihaz",
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
                                text = "Henüz cihaz bulunamadı",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = TelegramColors.TextPrimary
                            )

                            Text(
                                text = "Yakındaki cihazları bulmak için tara butonuna basın",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TelegramColors.TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceListItem(name: String, address: String, onClick: () -> Unit) {
    Surface(
        color = TelegramColors.Background,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .semantics { contentDescription = "Cihaz: $name" },
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
                    Icons.Default.ArrowForward,
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

            // Connect Button
            OutlinedButton(
                onClick = onClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TelegramColors.Primary
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Bağlan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
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
                text = "Bluetooth Kapalı",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp
                ),
                color = TelegramColors.TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Cihazları bulmak için Bluetooth'u açmanız gerekiyor",
                style = MaterialTheme.typography.bodyMedium,
                color = TelegramColors.TextSecondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onEnable,
                modifier = Modifier.semantics { contentDescription = "Bluetooth'u aç" },
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
                    text = "Bluetooth'u Aç",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
