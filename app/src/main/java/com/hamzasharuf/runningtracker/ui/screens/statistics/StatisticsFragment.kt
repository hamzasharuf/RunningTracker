package com.hamzasharuf.runningtracker.ui.screens.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hamzasharuf.runningtracker.R
import com.hamzasharuf.runningtracker.data.database.entities.Run
import com.hamzasharuf.runningtracker.utils.common.getFormattedStopWatchTimee
import com.hamzasharuf.runningtracker.utils.enums.SortType
import com.hamzasharuf.runningtracker.utils.enums.StateStatus
import com.hamzasharuf.runningtracker.utils.extensions.gone
import com.hamzasharuf.runningtracker.utils.extensions.visibile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.menu_item_back_arrow.*
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private var runs = listOf<Run>()
    private val viewModel: StatisticsViewModel by viewModels()

    @Inject
    lateinit var chartHandler: StatisticsHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        setupClickListeners()
        viewModel.getRunInfo()
        viewModel.getRunsList()
        chartHandler.initialize(chart)
    }

    private fun setupClickListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        handleRadioCheckChange()
    }


    private fun setUi(run: Run) {
        val totalTimeRun = getFormattedStopWatchTimee(run.timeInMillis)
        tvTotalTime.text = totalTimeRun

        val km = run.distanceInMeters / 1000f
        val totalDistance = (km * 10f).roundToInt() / 10f
        val totalDistanceString = "$totalDistance km"
        tvTotalDistance.text = totalDistanceString

        val avgSpeed = (run.avgSpeedInKMH * 10f).roundToInt() / 10f
        val avgSpeedString = "$avgSpeed km/h"
        tvAverageSpeed.text = avgSpeedString

        val totalCalories = "${run.caloriesBurned} kcal"
        tvTotalCalories.text = totalCalories
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
                radioDuration.id -> sortType = SortType.RUNNING_DURATION
            }
            if (sortType != null) {
                chartHandler.initDataSet(sortType)
                runs.indices.forEach { index ->
                    chartHandler.addEntry(
                        index,
                        when (sortType) {
                            SortType.DISTANCE -> runs[index].distanceInMeters
                            SortType.CALORIES_BURNED -> runs[index].caloriesBurned
                            SortType.RUNNING_DURATION -> runs[index].timeInMillis
                            SortType.AVG_SPEED -> runs[index].avgSpeedInKMH
                            else -> throw IllegalArgumentException("sortType not found: $sortType")
                        }
                    )
                }
            }


        }
    }

    private fun subscribeToObservers() {

        viewModel.runInfo.observe(viewLifecycleOwner) {
            when (it.status) {
                StateStatus.SUCCESS -> {
                    progress_bar1.gone()
                    setUi(it.data!!)
                }
                StateStatus.ERROR -> {
                    progress_bar1.gone()
                }
                StateStatus.LOADING -> {
                    progress_bar1.visibile()
                }
            }
        }

        viewModel.runsList.observe(viewLifecycleOwner, {
            when (it.status) {
                StateStatus.SUCCESS -> {
                    radioGroup.visibile()
                    chart.visibile()
                    progress_bar2.gone()
                    noRecordTextView.gone()
                    runs = it.data!!
                    radioGroup.check(radioGroup.getChildAt(0).id)
                }
                StateStatus.ERROR -> {
                    radioGroup.gone()
                    chart.gone()
                    progress_bar2.gone()
                    noRecordTextView.visibile()
                }
                StateStatus.LOADING -> {
                    radioGroup.gone()
                    chart.gone()
                    progress_bar2.visibile()
                    noRecordTextView.gone()
                }
            }

        })

    }
}