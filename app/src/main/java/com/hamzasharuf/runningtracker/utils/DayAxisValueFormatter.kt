package com.hamzasharuf.runningtracker.utils

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.concurrent.TimeUnit


class DayAxisValueFormatter(private val chart: LineChart, private val timeArray: List<Long>) :
    ValueFormatter() {


    private val mMonths = arrayOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )


    private val milliSecondsDiff = timeArray.last() - timeArray.first()
    private val secondsDiff = TimeUnit.MILLISECONDS.toSeconds(milliSecondsDiff)
    private val minuteDiff = secondsDiff / 60
    private val hourDiff = minuteDiff / 60
    private val daysDiff = hourDiff / 24
    private val monthsDiff = daysDiff / 30
    private val yearsDiff = monthsDiff / 12


    override fun getFormattedValue(value: Float): String {
        if (value < 0f) return ""
        val timeInMillis = timeArray[value.toInt()]
        val date = DateFormatter.timeMillisToDate(timeInMillis)
        val calendar = DateFormatter.dateToCalendar(date)


        val days = DateFormatter.getDayOfYear(calendar)
        val year = DateFormatter.getYear(calendar)
        val month = DateFormatter.getMonth(calendar)
        val monthName = mMonths[month % mMonths.size]
        val yearName = year.toString()

        // TODO : Add other else if statements to track hourly runs if day difference is less than 2
        return if (monthsDiff > 6) {
            "$monthName $yearName"
        } else {
            val dayOfMonth = DateFormatter.getDayOfMonth(calendar)
            var appendix = "th"
            when (dayOfMonth) {
                1, 21, 31 -> appendix = "st"
                2, 22 -> appendix = "nd"
                3, 23 -> appendix = "rd"
            }
            if (dayOfMonth == 0) "" else "$dayOfMonth$appendix $monthName"
        }
    }


}
