package com.hamzasharuf.runningtracker.ui.screens.statistics

import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class CaloriesValueFormatter: ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        if (value > 1000f){
            val roundedCalories = ((value/1000.0) * 100.0).roundToInt() / 100.0
            return "${roundedCalories.toInt()} kcal"
        }
        return "${value.toInt()} cal"
    }
}