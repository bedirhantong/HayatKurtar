package com.appvalence.hayatkurtar.presentation.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appvalence.hayatkurtar.domain.model.DeviceInfo
import com.appvalence.hayatkurtar.domain.usecase.IsBluetoothEnabledUseCase
import com.appvalence.hayatkurtar.domain.usecase.ObserveDiscoveredDevicesUseCase
import com.appvalence.hayatkurtar.domain.usecase.ScanDevicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val scanDevicesUseCase: ScanDevicesUseCase,
    private val observeDiscoveredDevicesUseCase: ObserveDiscoveredDevicesUseCase,
    private val isBluetoothEnabledUseCase: IsBluetoothEnabledUseCase,
) : ViewModel() {

    private val _devices = MutableStateFlow<List<DeviceInfo>>(emptyList())
    val devices: StateFlow<List<DeviceInfo>> = _devices.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _isBluetoothEnabled = MutableStateFlow(true)
    val isBluetoothEnabled: StateFlow<Boolean> = _isBluetoothEnabled.asStateFlow()

    init {
        _isBluetoothEnabled.value = isBluetoothEnabledUseCase()
        viewModelScope.launch {
            observeDiscoveredDevicesUseCase().collectLatest { list ->
                _devices.value = list
            }
        }
    }

    fun scan() {
        viewModelScope.launch(Dispatchers.IO) {
            _isScanning.value = true
            scanDevicesUseCase()
            _isScanning.value = false
        }
    }
}
