package com.hamzasharuf.runningtracker.utils

import android.graphics.Color

object LineChartConfiguration {

    private val blue_light = Color.parseColor("#B22196F3")
    private val blue = Color.parseColor("#2196F3")
    private val CIRCLE_COLORS_ARRAY = arrayListOf(
        blue
    )

    val circleColors = CIRCLE_COLORS_ARRAY
    val lineColor = blue_light

    const val CIRCLE_RADIUS = 8f
    const val VALUE_TEXT_SIZE = 16f
    const val FILL_ALPHA = 110
    const val LINE_WIDTH = 5f
}