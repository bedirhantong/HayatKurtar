package com.appvalence.hayatkurtar.domain.model

data class DeviceInfo(
    val name: String?,
    val address: String,
    val rssi: Int? = null,
    val txPower: Int? = null,
    val estimatedDistanceMeters: Double? = null,
)


