package com.hamzasharuf.runningtracker.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.hamzasharuf.runningtracker.data.repositories.RunRepository

class RunSharedViewModel @ViewModelInject constructor (
    val repository: RunRepository
): ViewModel() {
}