package com.appvalence.hayatkurtar.data.bluetooth

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.pow
import kotlin.math.roundToInt

interface DistanceEstimator {
    /**
     * Estimate distance in meters from RSSI/TxPower.
     * Applies smoothing per device address to reduce jitter.
     */
    fun estimateDistanceMeters(address: String, rssi: Int?, txPower: Int?): Double?

    /** Update calibration parameters. */
    fun updateCalibration(measuredPowerDefault: Int? = null, pathLossExponent: Double? = null, smoothingAlpha: Double? = null)
}

@Singleton
class RssiDistanceEstimator @Inject constructor() : DistanceEstimator {
    @Volatile private var measuredPower: Int = -59 // default reference RSSI at 1m
    @Volatile private var pathLossExponent: Double = 2.0 // free space ~2.0, indoor 2.0-3.5
    @Volatile private var alpha: Double = 0.5 // EMA smoothing factor

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


