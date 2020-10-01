package com.hamzasharuf.runningtracker.ui.screens.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hamzasharuf.runningtracker.R
import com.hamzasharuf.runningtracker.data.database.entities.Run
import com.hamzasharuf.runningtracker.utils.common.getFormattedStopWatchTimee
import com.hamzasharuf.runningtracker.utils.enums.SortType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*
import java.lang.Math.round
import javax.inject.Inject

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private var runs = listOf<Run>()
    private val viewModel: StatisticsViewModel by viewModels()

    @Inject
    lateinit var chartHandler: StatisticsHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        handleRadioCheckChange()
        chartHandler.initialize(chart)
    }


    private fun subscribeToObservers() {
        viewModel.totalTimeRun.observe(viewLifecycleOwner, {
            it?.let {
                val totalTimeRun = getFormattedStopWatchTimee(it)
                tvTotalTime.text = totalTimeRun
            }
        })
        viewModel.totalDistance.observe(viewLifecycleOwner, {
            it?.let {
                val km = it / 1000f
                val totalDistance = round(km * 10f) / 10f
                val totalDistanceString = "${totalDistance} km"
                tvTotalDistance.text = totalDistanceString
            }
        })
        viewModel.totalAvgSpeed.observe(viewLifecycleOwner, {
            it?.let {
                val avgSpeed = round(it * 10f) / 10f
                val avgSpeedString = "${avgSpeed} km/h"
                tvAverageSpeed.text = avgSpeedString
            }
        })
        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner, {
            it?.let {
                val totalCalories = "${it} kcal"
                tvTotalCalories.text = totalCalories
            }

        })
        viewModel.runsSortedByDateAsc.observe(viewLifecycleOwner, {
            runs = it
            radioGroup.check(radioGroup.getChildAt(0).id)
        })

    }

    private fun handleRadioCheckChange() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            chartHandler.clearChart()

            var sortType: SortType? = null

            runs.let {
                val timeArray = it.map { it.timestamp }
                chartHandler.initAxis(timeArray)
            }

            when (checkedId) {
                radioDistance.id -> sortType = SortType.DISTANCE
                radioCalories.id -> sortType = SortType.CALORIES_BURNED
                radioSpeed.id -> sortType = SortType.AVG_SPEED
                radioDuration.id -> sortType = SortType.RUNNING_TIME
            }
            if (sortType != null) {
                chartHandler.initDataSet(sortType)
                runs.indices.forEach { index ->
                    chartHandler.addEntry(
                        index,
                        when (sortType) {
                            SortType.DISTANCE -> runs[index].distanceInMeters
                            SortType.CALORIES_BURNED -> runs[index].caloriesBurned
                            SortType.RUNNING_TIME -> runs[index].timeInMillis
                            SortType.AVG_SPEED -> runs[index].avgSpeedInKMH
                            else -> throw IllegalArgumentException("sortType not found: $sortType")
                        }
                    )
                }
            }


        }
    }

}