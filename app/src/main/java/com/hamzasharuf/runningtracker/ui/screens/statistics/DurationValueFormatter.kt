package com.hamzasharuf.runningtracker.ui.screens.statistics

import com.github.mikephil.charting.formatter.ValueFormatter

class DurationValueFormatter : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val time = value.toInt()
        val seconds = time / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        return "$hours:$minutes:$seconds"
    }
}
