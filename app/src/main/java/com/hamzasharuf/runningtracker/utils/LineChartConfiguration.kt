package com.hamzasharuf.runningtracker.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.res.ResourcesCompat
import com.hamzasharuf.runningtracker.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

object LineChartConfiguration {

    private val blue_light = Color.parseColor("#B22196F3")
    private val blue = Color.parseColor("#2196F3")
    private val red_light = Color.parseColor("#B2F44336")
    private val red = Color.parseColor("#F44336")
    private val CIRCLE_COLORS_ARRAY = arrayListOf(
        red
    )
    val circleColors = CIRCLE_COLORS_ARRAY
    val lineColor = red_light
    val axisLineColor = blue_light
    val valueTextColor = Color.parseColor("#009688")

    val circleRadius = 8f
    val valueTextSize = 14f
    val fillAlpha = 110
    val lineWidth = 5f
    val  xAxisAnimationSpeed = 700
    val visibleXRangeMaximum = 5f

}