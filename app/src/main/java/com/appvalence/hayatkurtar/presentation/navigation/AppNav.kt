package com.appvalence.hayatkurtar.presentation.navigation

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appvalence.hayatkurtar.presentation.chatdetail.ChatDetailScreen
import com.appvalence.hayatkurtar.presentation.chatlist.ChatListScreen
import com.appvalence.hayatkurtar.presentation.components.PermissionsGate
import com.appvalence.hayatkurtar.presentation.devices.DeviceListScreen

object Routes {
    const val Devices = "devices"
    const val Chat = "chat"
    const val ChatDetail = "chatDetail/{address}"
    const val ChatList = "chatList"
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    PermissionsGate {
    NavHost(navController = navController, startDestination = Routes.ChatList) {
        // Start destination: minimal fade in; when leaving, slide left; when returning, slide right
        composable(
            route = Routes.ChatList,
            enterTransition = { fadeIn(animationSpec = tween(150)) },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = { fadeOut(animationSpec = tween(120)) }
        ) {
            ChatListScreen(
                onOpenChat = { peer -> if (peer.isNotBlank()) navController.navigate("chatDetail/${Uri.encode(peer)}") },
                onScan = { navController.navigate(Routes.Devices) }
            )
        }
        // Forward from ChatList -> Devices: slide left; back: slide right
        composable(
            route = Routes.Devices,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                )
            }
        ) {
            DeviceListScreen(
                onDeviceSelected = { address -> navController.navigate("chatDetail/$address") },
                onOpenSettings = {},
                onBack = { navController.popBackStack() }
            )
        }
        // Forward ChatList/Devices -> ChatDetail: slide left; back: slide right
        composable(
            route = Routes.ChatDetail,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                )
            }
        ) { backStackEntry ->
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


