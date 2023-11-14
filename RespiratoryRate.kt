package com.example.chanluliao_project1

import kotlin.math.abs
import kotlin.math.sqrt

fun calculateRespRate(accelX: List<Float>, accelY: List<Float>, accelZ: List<Float>): Int {
    var previousValue = 0f
    var currentValue = 0f
    previousValue = 10f
    var k = 0

    for (i in 11 until accelZ.size) {
        // currentValue = Math.pow(accelValuesZ[i].toDouble(), 2.0).toFloat()
        // currentValue = accelValuesZ[i]
        currentValue = sqrt(Math.pow(accelZ[i].toDouble(), 2.0) +
                Math.pow(accelX[i].toDouble(), 2.0) +
                Math.pow(accelY[i].toDouble(), 2.0)).toFloat()

        if (abs(previousValue - currentValue) > 0.1105) {
            k++
        }

        previousValue = currentValue
    }

    val ret = (k / 45.0)
    return (ret * 60).toInt()
}