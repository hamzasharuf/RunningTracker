package com.hamzasharuf.runningtracker.ui.screens.statistics

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamzasharuf.runningtracker.data.database.entities.Run
import com.hamzasharuf.runningtracker.data.repositories.RunRepository
import com.hamzasharuf.runningtracker.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class StatisticsViewModel @ViewModelInject constructor(
    private val repository: RunRepository
) : ViewModel() {

    private val _runInfo = MutableLiveData<Resource<Run>>()
    val runInfo: LiveData<Resource<Run>>
        get() = _runInfo

    private val _runsList = MutableLiveData<Resource<List<Run>>>()
    val runsList: LiveData<Resource<List<Run>>>
        get() = _runsList

    fun getRunsList() {
        viewModelScope.launch(Dispatchers.IO) {
            _runsList.postValue(Resource.loading())
            try {
                val data = async { repository.getAllRunsSortedByDateAsc() }
                if (data.await().isNotEmpty())
                    _runsList.postValue(Resource.success(data.await()))
                else
                    throw Exception("No data available")
            } catch (e: Exception) {
                _runsList.postValue(Resource.error(e.message))
            }
        }
    }

    fun getRunInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _runsList.postValue(Resource.loading())
            try {

                val totalTimeRun = async { repository.getTotalTimeInMillis() }
                val totalDistance = async { repository.getTotalDistance() }
                val totalCaloriesBurned = async { repository.getTotalCaloriesBurned() }
                val totalAvgSpeed = async { repository.getTotalAvgSpeed() }

                val result = Run(
                    null,
                    0,
                    totalAvgSpeed.await(),
                    totalDistance.await(),
                    totalTimeRun.await(),
                    totalCaloriesBurned.await()
                )
                _runInfo.postValue(Resource.success(result))

            } catch (e: Exception) {
                _runInfo.postValue(Resource.error(e.message))
            }
        }
    }


}