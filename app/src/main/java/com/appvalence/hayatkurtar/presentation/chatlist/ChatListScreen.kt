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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Surface
import androidx.compose.material3.Divider
import com.appvalence.hayatkurtar.presentation.chatdetail.components.TelegramColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    onOpenChat: (peerId: String) -> Unit,
    onScan: () -> Unit,
    viewModel: ChatListViewModel = hiltViewModel(),
) {
    val chats = viewModel.chats.collectAsState().value
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
                        IconButton(onClick = { menuExpanded.value = true }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = null,
                                tint = TelegramColors.TextSecondary
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded.value,
                            onDismissRequest = { menuExpanded.value = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Tara") },
                                onClick = {
                                    menuExpanded.value = false
                                    onScan()
                                }
                            )
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

                    Spacer(modifier = Modifier.height(32.dp))

                    ScanButton(onClick = onScan)
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
}
