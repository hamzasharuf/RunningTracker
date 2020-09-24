package com.hamzasharuf.runningtracker.ui.screens.statistics

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.hamzasharuf.runningtracker.data.repositories.RunRepository

class StatisticsViewModel @ViewModelInject constructor (
    val repository: RunRepository
): ViewModel() {
}