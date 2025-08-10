package com.appvalence.bluetooth.api

import java.util.UUID

interface BleAdvertiser {
    fun start(serviceUuid: UUID? = null): Boolean
    fun stop()
    fun isAdvertising(): Boolean
}


