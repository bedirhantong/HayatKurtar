package com.appvalence.hayatkurtar

import android.os.Bundle
import android.content.Intent
import android.bluetooth.BluetoothAdapter
import android.os.Build
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.appvalence.hayatkurtar.ui.theme.HayatKurtarTheme
import dagger.hilt.android.AndroidEntryPoint
import com.appvalence.hayatkurtar.presentation.navigation.AppNavGraph
import com.appvalence.hayatkurtar.presentation.onboarding.OnboardingScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import javax.inject.Inject
import com.appvalence.hayatkurtar.data.local.UserPrefsStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isReady: Boolean = false
    @Inject lateinit var userPrefs: UserPrefsStore
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { !isReady }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HayatKurtarTheme {
                Surface(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
                    AppNavGraph()
                }
            }
        }
        isReady = true
    }

    fun makeDiscoverable(durationSec: Int = 120) {
        val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
            putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, durationSec)
        }
        startActivity(discoverableIntent)
    }
}
