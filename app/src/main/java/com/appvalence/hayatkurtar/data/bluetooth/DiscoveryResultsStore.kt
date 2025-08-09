package com.appvalence.hayatkurtar.data.bluetooth

import com.appvalence.hayatkurtar.domain.model.DeviceInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoveryResultsStore @Inject constructor() {
    private val _devices = MutableStateFlow<List<DeviceInfo>>(emptyList())
    val devices: StateFlow<List<DeviceInfo>> = _devices.asStateFlow()

    fun reset() {
        _devices.value = emptyList()
    }

    fun addOrUpdate(device: DeviceInfo) {
        val current = _devices.value
        if (current.any { it.address == device.address }) return
        _devices.value = current + device
    }
}


