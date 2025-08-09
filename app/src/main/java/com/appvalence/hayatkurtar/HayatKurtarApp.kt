package com.appvalence.hayatkurtar

import android.app.Application
import com.google.crypto.tink.config.TinkConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HayatKurtarApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Tink once
        TinkConfig.register()
    }
}


