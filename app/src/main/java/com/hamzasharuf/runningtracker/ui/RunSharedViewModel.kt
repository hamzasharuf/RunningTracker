package com.hamzasharuf.runningtracker.ui

import android.provider.ContactsContract
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hamzasharuf.runningtracker.data.database.entities.Run
import com.hamzasharuf.runningtracker.data.repositories.RunRepository
import com.hamzasharuf.runningtracker.utils.enums.SortType
import kotlinx.coroutines.launch

class RunSharedViewModel @ViewModelInject constructor(
    private val repository: RunRepository
) : ViewModel() {

    private val runsSortedByDate = repository.getAllRunsSortedByDate()
    private val runsSortedByDistance = repository.getAllRunsSortedByDistance()
    private val runsSortedByCaloriesBurned = repository.getAllRunsSortedByCaloriesBurned()
    private val runsSortedByTimeInMillis = MutableLiveData<List<Run>>()
    private val runsSortedByAvgSpeed = repository.getAllRunsSortedByAvgSpeed()


    val runs = MediatorLiveData<List<Run>>()

    var sortType = SortType.DATE

    init {

        runs.addSource(runsSortedByDate) { result ->
            if(sortType == SortType.DATE) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByAvgSpeed) { result ->
            if(sortType == SortType.AVG_SPEED) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByCaloriesBurned) { result ->
            if(sortType == SortType.CALORIES_BURNED) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByDistance) { result ->
            if(sortType == SortType.DISTANCE) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByTimeInMillis) { result ->
            if(sortType == SortType.RUNNING_TIME) {
                result?.let { runs.value = it }
                runsSortedByTimeInMillis.postValue(repository.getAllRunsSortedByTimeInMillis().value)
            }
        }
    }

    fun sortRuns(sortType: SortType) = when(sortType) {
        SortType.DATE -> runsSortedByDate.value?.let { runs.value = it }
        SortType.RUNNING_TIME -> runsSortedByTimeInMillis.value?.let { runs.value = it }
        SortType.AVG_SPEED -> runsSortedByAvgSpeed.value?.let { runs.value = it }
        SortType.DISTANCE -> runsSortedByDistance.value?.let { runs.value = it }
        SortType.CALORIES_BURNED -> runsSortedByCaloriesBurned.value?.let { runs.value = it }
    }.also {
        this.sortType = sortType
    }

    fun insertRun(run: Run) = viewModelScope.launch {
        repository.insertRun(run)
    }
}