package com.hamzasharuf.runningtracker.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamzasharuf.runningtracker.data.database.entities.Run
import com.hamzasharuf.runningtracker.data.repositories.RunRepository
import kotlinx.coroutines.launch

class RunSharedViewModel @ViewModelInject constructor(
    val repository: RunRepository
) : ViewModel() {
    val runsSortedByDate = repository.getAllRunsSortedByDate()

    fun insertRun(run: Run) = viewModelScope.launch {
        repository.insertRun(run)
    }
}