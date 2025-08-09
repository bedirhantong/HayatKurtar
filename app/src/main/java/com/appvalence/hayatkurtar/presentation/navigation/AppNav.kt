package com.appvalence.hayatkurtar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.net.Uri
import com.appvalence.hayatkurtar.presentation.chatdetail.ChatDetailScreen
import com.appvalence.hayatkurtar.presentation.chatlist.ChatListScreen
import com.appvalence.hayatkurtar.presentation.devices.DeviceListScreen
import com.appvalence.hayatkurtar.presentation.components.PermissionsGate

object Routes {
    const val Devices = "devices"
    const val Chat = "chat"
    const val ChatDetail = "chatDetail/{address}"
    const val ChatList = "chatList"
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    PermissionsGate {
    NavHost(navController = navController, startDestination = Routes.ChatList) {
        composable(Routes.ChatList) {
            ChatListScreen(
                onOpenChat = { peer -> if (peer.isNotBlank()) navController.navigate("chatDetail/${Uri.encode(peer)}") },
                onScan = { navController.navigate(Routes.Devices) }
            )
        }
        composable(Routes.Devices) {
            DeviceListScreen(
                onDeviceSelected = { address -> navController.navigate("chatDetail/$address") },
                onOpenSettings = {},
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ChatDetail) { backStackEntry ->
            val address = backStackEntry.arguments?.getString("address")?.let { Uri.decode(it) } ?: ""
            // Guard against invalid addresses (e.g., chat list might pass non-MAC peer ids). If invalid, do not attempt to connect.
            val safeAddress = if (android.bluetooth.BluetoothAdapter.checkBluetoothAddress(address)) address else ""
            ChatDetailScreen(
                address = safeAddress,
                onBack = { navController.popBackStack() }
            )
        }
    }
    }
}


