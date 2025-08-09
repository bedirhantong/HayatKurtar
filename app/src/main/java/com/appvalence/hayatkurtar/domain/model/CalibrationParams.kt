package com.appvalence.hayatkurtar.domain.model

data class CalibrationParams(
    val measuredPower: Int = -59,
    val pathLossExponent: Double = 2.0,
    val smoothingAlpha: Double = 0.5,
)


