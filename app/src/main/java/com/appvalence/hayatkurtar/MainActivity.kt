package com.appvalence.hayatkurtar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.appvalence.hayatkurtar.ui.theme.HayatKurtarTheme
import dagger.hilt.android.AndroidEntryPoint
import com.appvalence.hayatkurtar.presentation.navigation.AppNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HayatKurtarTheme {
                Surface(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
                    AppNavGraph()
                }
            }
        }
    }
}