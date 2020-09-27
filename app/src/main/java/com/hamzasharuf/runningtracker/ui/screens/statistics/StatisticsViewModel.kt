package com.hamzasharuf.runningtracker.ui.screens.statistics

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.hamzasharuf.runningtracker.data.repositories.RunRepository

class StatisticsViewModel @ViewModelInject constructor (
    private val repository: RunRepository
): ViewModel() {

    val totalTimeRun = repository.getTotalTimeInMillis()
    val totalDistance = repository.getTotalDistance()
    val totalCaloriesBurned = repository.getTotalCaloriesBurned()
    val totalAvgSpeed = repository.getTotalAvgSpeed()
    val runsSortedByDate = repository.getAllRunsSortedByDate()
}