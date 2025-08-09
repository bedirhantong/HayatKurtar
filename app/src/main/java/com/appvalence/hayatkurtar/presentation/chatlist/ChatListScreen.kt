package com.appvalence.hayatkurtar.presentation.chatlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appvalence.hayatkurtar.presentation.chatlist.components.ChatListItem
import com.appvalence.hayatkurtar.presentation.chatlist.components.ScanButton
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Surface
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import com.appvalence.hayatkurtar.presentation.chatdetail.components.TelegramColors
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    onOpenChat: (peerId: String) -> Unit,
    onScan: () -> Unit,
    viewModel: ChatListViewModel = hiltViewModel(),
) {
    val chats = viewModel.chats.collectAsState().value
    val localName = viewModel.localDeviceName.collectAsState().value
    val context = LocalContext.current
    val isBtEnabled = viewModel.isBluetoothEnabled.collectAsState().value
    val promptShown = viewModel.promptShown.collectAsState().value
    val autoVisibility = viewModel.autoVisibility.collectAsState().value
    val isAdvertising = viewModel.isAdvertising.collectAsState().value
    val showDiscoverableDialog = remember { mutableStateOf(false) }
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)
    val menuExpanded = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 1.dp,
                color = TelegramColors.Background
            ) {
                TopAppBar(
                    title = {
                        Text(
                            "Sohbetler",
                            color = TelegramColors.TextPrimary,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        )
                    },
                    actions = {
                        // Quick Scan action to boost user feel
                        if (localName.isNotBlank()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    localName,
                                    color = TelegramColors.TextSecondary,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(Modifier.width(8.dp))
                                androidx.compose.material3.Switch(
                                    checked = isAdvertising,
                                    onCheckedChange = { checked ->
                                        if (checked) {
                                            // Start BLE advertising and request Classic discoverable
                                            viewModel.enableVisibility()
                                            (context as? com.appvalence.hayatkurtar.MainActivity)?.makeDiscoverable(300)
                                        } else {
                                            viewModel.disableVisibility()
                                        }
                                    }
                                )
                            }
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
        floatingActionButton =  {
            OutlinedButton(onClick = onScan) {
                Icon(
                    painter = painterResource(id = com.appvalence.hayatkurtar.R.drawable.ic_scan),
                    contentDescription = null,
                )
                Spacer(Modifier.width(6.dp))
                Text("Diğer cihazları keşfet")
            }
        },
        containerColor = TelegramColors.Background
    ) { padding ->
        if (chats.isEmpty()) {
            // Empty State - Modern Design
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(TelegramColors.Background)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = com.appvalence.hayatkurtar.R.drawable.ic_scan),
                        contentDescription = null,
                        tint = TelegramColors.Primary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Empty state text
                    Text(
                        text = "Henüz sohbet yok",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 22.sp
                        ),
                        color = TelegramColors.TextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Yeni bir sohbet başlatmak için tara butonuna basın",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TelegramColors.TextSecondary
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(padding)
                    .background(TelegramColors.Background),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(chats, key = { it.id }) { message ->
                     ChatListItem(
                         message = message,
                         onClick = { peer ->
                             // Only navigate if it looks like a real Bluetooth MAC address
                             val isMac = android.bluetooth.BluetoothAdapter.checkBluetoothAddress(peer)
                             if (isMac) onOpenChat(peer)
                         },
                        onDelete = { peer -> viewModel.delete(peer) }
                    )

                    // Divider between items
                    Divider(
                        modifier = Modifier.padding(start = 72.dp),
                        thickness = 0.5.dp,
                        color = TelegramColors.Divider.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }

    // Enforce discoverable after Bluetooth enabled
    LaunchedEffect(isBtEnabled, promptShown, isAdvertising) {
        if (isBtEnabled && !promptShown && !isAdvertising) {
            // Prompt only if not already advertising and not shown before
            showDiscoverableDialog.value = true
        }
    }

    if (showDiscoverableDialog.value) {
        AlertDialog(
            onDismissRequest = { showDiscoverableDialog.value = false },
            title = { Text("Keşfedilebilirliği Açın") },
            text = {
                Column {
                    Text("Uygulamanın yakınınızdaki kişileri hızlı ve güvenilir biçimde bulabilmesi için Bluetooth keşfedilebilirliğini açık tutmanız önerilir. Kapatırsanız performans etkilenebilir.")
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val auto = remember { mutableStateOf(true) }
                        androidx.compose.material3.Checkbox(checked = auto.value, onCheckedChange = { auto.value = it })
                        Text("Her açılışta otomatik görünür yap")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showDiscoverableDialog.value = false
                    (context as? com.appvalence.hayatkurtar.MainActivity)?.makeDiscoverable(300)
                    viewModel.enableVisibility()
                    viewModel.saveAutoVisibilityPreference(true)
                    viewModel.markDiscoverablePromptShown()
                }) { Text("Aç (Önerilen)") }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDiscoverableDialog.value = false
                    viewModel.saveAutoVisibilityPreference(false)
                    viewModel.markDiscoverablePromptShown()
                }) { Text("Daha sonra") }
            }
        )
    }
}
