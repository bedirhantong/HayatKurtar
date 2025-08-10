package com.appvalence.bluetooth.impl

import com.appvalence.bluetooth.api.DistanceEstimator
import kotlin.math.pow
import kotlin.math.roundToInt

class RssiDistanceEstimator : DistanceEstimator {
    @Volatile private var measuredPower: Int = -59
    @Volatile private var pathLossExponent: Double = 2.0
    @Volatile private var alpha: Double = 0.5

    private val lastDistances: MutableMap<String, Double> = mutableMapOf()

    override fun estimateDistanceMeters(address: String, rssi: Int?, txPower: Int?): Double? {
        val r = rssi ?: return null
        val ref = txPower ?: measuredPower
        val raw = 10.0.pow((ref - r).toDouble() / (10.0 * pathLossExponent))
        val previous = lastDistances[address]
        val smoothed = if (previous == null) raw else (alpha * raw + (1 - alpha) * previous)
        lastDistances[address] = smoothed
        return (smoothed * 100.0).roundToInt() / 100.0
    }

    override fun updateCalibration(measuredPowerDefault: Int?, pathLossExponent: Double?, smoothingAlpha: Double?) {
        measuredPowerDefault?.let { this.measuredPower = it }
        pathLossExponent?.let { this.pathLossExponent = it }
        smoothingAlpha?.let { this.alpha = it.coerceIn(0.0, 1.0) }
    }
}


