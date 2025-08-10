package com.appvalence.bluetooth.api

interface DistanceEstimator {
    fun estimateDistanceMeters(address: String, rssi: Int?, txPower: Int?): Double?
    fun updateCalibration(measuredPowerDefault: Int? = null, pathLossExponent: Double? = null, smoothingAlpha: Double? = null)
}


