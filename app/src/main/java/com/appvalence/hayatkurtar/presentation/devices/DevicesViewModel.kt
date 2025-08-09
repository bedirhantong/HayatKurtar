package com.appvalence.hayatkurtar.presentation.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appvalence.hayatkurtar.domain.model.DeviceInfo
import com.appvalence.hayatkurtar.domain.usecase.IsBluetoothEnabledUseCase
import com.appvalence.hayatkurtar.domain.usecase.ObserveDiscoveredDevicesUseCase
import com.appvalence.hayatkurtar.domain.usecase.ScanDevicesUseCase
import com.appvalence.hayatkurtar.domain.usecase.StartDiscoveryServiceUseCase
import com.appvalence.hayatkurtar.domain.usecase.StopDiscoveryServiceUseCase
import com.appvalence.hayatkurtar.domain.usecase.UpdateDistanceCalibrationUseCase
import com.appvalence.hayatkurtar.domain.usecase.RecalculateDiscoveredDistancesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import com.appvalence.hayatkurtar.data.local.CalibrationStore
import com.appvalence.hayatkurtar.domain.model.CalibrationParams
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val scanDevicesUseCase: ScanDevicesUseCase,
    private val observeDiscoveredDevicesUseCase: ObserveDiscoveredDevicesUseCase,
    private val isBluetoothEnabledUseCase: IsBluetoothEnabledUseCase,
    private val startDiscoveryService: StartDiscoveryServiceUseCase,
    private val stopDiscoveryService: StopDiscoveryServiceUseCase,
    private val updateDistanceCalibration: UpdateDistanceCalibrationUseCase,
    private val calibrationStore: CalibrationStore,
    private val recalcDistances: RecalculateDiscoveredDistancesUseCase,
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
        // Load calibration and apply
        viewModelScope.launch(Dispatchers.IO) {
            calibrationStore.params.collectLatest { p ->
                updateDistanceCalibration(p.measuredPower, p.pathLossExponent, p.smoothingAlpha)
                // Recalculate distances for current list to reflect calibration immediately
                recalcDistances()
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

    fun startBackgroundDiscovery() {
        startDiscoveryService()
    }

    fun stopBackgroundDiscovery() {
        stopDiscoveryService()
    }

    fun calibrate(measuredPower: Int? = null, n: Double? = null, alpha: Double? = null) {
        updateDistanceCalibration(measuredPower, n, alpha)
        val mp = measuredPower ?: -59
        val nn = n ?: 2.0
        val aa = alpha ?: 0.5
        viewModelScope.launch(Dispatchers.IO) {
            calibrationStore.save(CalibrationParams(mp, nn, aa))
        }
        // Also update current visible distances immediately
        recalcDistances()
    }
}
