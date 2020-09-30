package com.hamzasharuf.runningtracker.ui.screens.statistics

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.hamzasharuf.runningtracker.utils.DayAxisValueFormatter
import com.hamzasharuf.runningtracker.utils.LineChartConfiguration
import javax.inject.Inject

class StatisticsHandler @Inject constructor() {

    lateinit var chart: LineChart

    // Chart Initialization
    fun initialize(chart: LineChart) {
        this.chart = chart
        initChart()

        val dataSets = ArrayList<ILineDataSet>()
        val lineData = LineData(dataSets)
        chart.data = lineData
    }

    private fun initChart(){
        chart.setTouchEnabled(true)
        chart.setPinchZoom(true)
        chart.isDragEnabled = true
        chart.axisRight.isEnabled = false
    }

    fun initAxis(timeArray: List<Long>) {
        // Init y-axis
        val leftAxis = chart.axisLeft
        leftAxis.setDrawGridLines(false)
        chart.animateY(1000)

        // chart.setDrawYLabels(false);
        // TODO : Use Dependency Injection
        val xAxisFormatter = DayAxisValueFormatter(chart, timeArray)



        // Init x-axis
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day
        xAxis.labelCount = 3

        xAxis.valueFormatter = xAxisFormatter

    }

    private fun getDistanceSet(entries: ArrayList<Entry>): LineDataSet {

        val distanceSet = LineDataSet(entries, "Running Distance")

        with(distanceSet) {
            fillAlpha = LineChartConfiguration.FILL_ALPHA
            color = LineChartConfiguration.lineColor
            lineWidth = LineChartConfiguration.LINE_WIDTH
            circleColors = LineChartConfiguration.circleColors
            circleRadius = LineChartConfiguration.CIRCLE_RADIUS
            valueTextSize = LineChartConfiguration.VALUE_TEXT_SIZE
            valueFormatter = DistanceValueFormatter()
            setDrawCircleHole(false)
        }

        return distanceSet
    }

    fun addEntry(index: Int, distanceInMeters: Int) {
        val data = chart.data
        if (data != null) {
            // TODO : EDIT THIS AFTER ADDING OTHER SETS
            var set = data.getDataSetByIndex(0)
            // set.addEntry(...); // can be called as well
            if (set == null) {
                set = getDistanceSet(ArrayList())
                data.addDataSet(set)
            }
            data.addEntry(Entry(index.toFloat(), distanceInMeters.toFloat()), 0)
            chart.setDrawGridBackground(false)
            data.notifyDataChanged()

            // let the chart know it's data has changed
            chart.notifyDataSetChanged()

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(20f)
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.entryCount.toFloat())

        }
    }

}