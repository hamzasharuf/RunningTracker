package com.hamzasharuf.runningtracker.ui.screens.statistics

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.hamzasharuf.runningtracker.R
import com.hamzasharuf.runningtracker.data.model.DataSetInfo
import com.hamzasharuf.runningtracker.utils.LineChartConfiguration
import com.hamzasharuf.runningtracker.utils.enums.SortType
import java.lang.IllegalArgumentException
import javax.inject.Inject

class StatisticsHandler @Inject constructor(private val context: Context) {

    lateinit var chart: LineChart

    private val distanceSetInfo = DataSetInfo("Distance", DistanceValueFormatter())
    private val caloriesSetInfo = DataSetInfo("Calories Burned", CaloriesValueFormatter())
    private val durationSetInfo = DataSetInfo("Duration", DurationValueFormatter())
    private val speedSetInfo = DataSetInfo("Speed", SpeedValueFormatter())

    // Chart Initialization
    fun initialize(chart: LineChart) {
        this.chart = chart
        initChart()

        val dataSets = ArrayList<ILineDataSet>()
        val lineData = LineData(dataSets)
        chart.data = lineData
    }

    private fun initChart() {
        chart.setTouchEnabled(true)
        chart.setPinchZoom(true)
        chart.isDragEnabled = true
        chart.axisRight.isEnabled = false
        chart.setNoDataTextTypeface(ResourcesCompat.getFont(context, R.font.gilroy_bold))

        val description = Description()
        description.text = ""
        chart.description = description
    }

    fun initDataSet(sortType: SortType) {
        if (chart.data.getDataSetByIndex(0) != null)
            chart.data.removeDataSet(0)
        val ds = when(sortType){
            SortType.DISTANCE -> getSet(distanceSetInfo)
            SortType.CALORIES_BURNED -> getSet(caloriesSetInfo)
            SortType.RUNNING_DURATION -> getSet(durationSetInfo)
            SortType.AVG_SPEED -> getSet(speedSetInfo)
            else -> throw IllegalArgumentException("Unknown sortType: $sortType")
        }

        chart.data.addDataSet(ds)
    }

    fun initAxis(timeArray: List<Long>) {
        // Init y-axis
        val leftAxis = chart.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.axisLineWidth = 3f
        leftAxis.axisLineColor = LineChartConfiguration.axisLineColor
        leftAxis.typeface = ResourcesCompat.getFont(context, R.font.gilroy_bold)

        // chart.setDrawYLabels(false);
        // TODO : Use Dependency Injection
        val xAxisFormatter = DayAxisValueFormatter(chart, timeArray)

        // Init x-axis
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day
        xAxis.labelCount = 3
        xAxis.axisLineWidth = 3f
        xAxis.axisLineColor = LineChartConfiguration.axisLineColor
        xAxis.typeface = ResourcesCompat.getFont(context, R.font.gilroy_bold)

        xAxis.valueFormatter = xAxisFormatter

    }

    private fun getSet(dsi: DataSetInfo): LineDataSet {

        val mSet = LineDataSet(ArrayList(), dsi.label)
        with(mSet) {
            fillAlpha = LineChartConfiguration.fillAlpha
            color = LineChartConfiguration.lineColor
            lineWidth = LineChartConfiguration.lineWidth
            circleColors = LineChartConfiguration.circleColors
            circleRadius = LineChartConfiguration.circleRadius
            valueTextSize = LineChartConfiguration.valueTextSize
            valueTextColor = LineChartConfiguration.valueTextColor
            valueFormatter = dsi.valueFormatter
            valueTypeface = ResourcesCompat.getFont(context, R.font.gilroy_bold)

            setDrawCircleHole(false)
        }

        return mSet
    }

    fun clearChart() {
        chart.data.removeDataSet(0)
    }

    fun addEntry(index: Int, value: Number) {

        val data = chart.data

        data.addEntry(Entry(index.toFloat(), value.toFloat()), 0)
        chart.setDrawGridBackground(false)

        chart.animateX(LineChartConfiguration.xAxisAnimationSpeed, Easing.Linear)
        data.notifyDataChanged()
        // let the chart know it's data has changed
        chart.notifyDataSetChanged()

        // limit the number of visible entries
        chart.setVisibleXRangeMaximum(LineChartConfiguration.visibleXRangeMaximum)

        // move to the latest entry
        chart.moveViewToX(data.entryCount.toFloat())


    }

}