package com.hamzasharuf.runningtracker.ui.screens.statistics

import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class DistanceValueFormatter: ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return if ((value/1000.0).toInt() == 0){
            "${value.toInt()} m"
        }else{
            val roundedDistance = ((value/1000.0) * 100.0).roundToInt() / 100.0
            "$roundedDistance km"
        }
    }
}