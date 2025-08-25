package com.appvalence.hayatkurtar

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.appvalence.hayatkurtar.ui.theme.HayatKurtarTheme
import com.appvalence.hayatkurtar.data.mesh.service.MeshNetworkService
import com.appvalence.hayatkurtar.presentation.mesh.MeshChatScreen
import com.appvalence.hayatkurtar.presentation.system.SystemStatusScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isReady: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { !isReady }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            HayatKurtarTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    // Start mesh service when app launches
                    LaunchedEffect(Unit) {
                        MeshNetworkService.startService(this@MainActivity)
                    }
                    
                    // Navigation state
                    var currentScreen by remember { mutableStateOf("status") }
                    
                    when (currentScreen) {
                        "status" -> {
                            SystemStatusScreen(
                                onNavigateToChat = { currentScreen = "chat" }
                            )
                        }
                        "chat" -> {
                            MeshChatScreen(
                                onNavigateToSettings = { currentScreen = "status" }
                            )
                        }
                    }
                }
            }
        }
        isReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop mesh service when app is destroyed
        MeshNetworkService.stopService(this)
    }
}
