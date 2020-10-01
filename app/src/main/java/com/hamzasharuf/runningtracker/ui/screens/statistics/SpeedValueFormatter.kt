package com.hamzasharuf.runningtracker.ui.screens.statistics

import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class SpeedValueFormatter: ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return "${value.toInt()} km/h"
    }
}