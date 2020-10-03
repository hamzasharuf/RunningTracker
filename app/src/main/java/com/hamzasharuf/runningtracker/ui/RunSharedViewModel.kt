package com.hamzasharuf.runningtracker.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamzasharuf.runningtracker.data.database.entities.Run
import com.hamzasharuf.runningtracker.data.repositories.RunRepository
import com.hamzasharuf.runningtracker.utils.Resource
import com.hamzasharuf.runningtracker.utils.enums.SortType
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class RunSharedViewModel @ViewModelInject constructor(
    private val repository: RunRepository
) : ViewModel() {

    var isRunResultsLoaded: Boolean  = false

    private val _runsList = MutableLiveData<Resource<List<Run>>>()
    val runsList: LiveData<Resource<List<Run>>>
        get() = _runsList

    var sortType: SortType = SortType.DATE

    val t = Timer()

    fun getRuns(sortType: SortType) {
        when (sortType) {
            SortType.DATE -> {
                fetchData { repository.getAllRunsSortedByDateDesc() }
            }
            SortType.RUNNING_DURATION -> {
                fetchData { repository.getAllRunsSortedByTimeInMillisDesc() }
            }
            SortType.AVG_SPEED -> {
                fetchData { repository.getAllRunsSortedByAvgSpeedDesc() }
            }
            SortType.DISTANCE -> {
                fetchData { repository.getAllRunsSortedByDistanceDesc() }
            }
            SortType.CALORIES_BURNED -> {
                fetchData { repository.getAllRunsSortedByCaloriesBurnedDesc() }
            }
        }
        this.sortType = sortType

    }

    private fun fetchData(predicate: suspend () -> List<Run>) {
        viewModelScope.launch(IO) {
            _runsList.postValue(Resource.loading())
            try {
                val data = async { predicate() }
                if (data.await().isNotEmpty())
                    _runsList.postValue(Resource.success(data.await()))
                else
                    throw Exception("No data available")
            } catch (e: Exception) {
                _runsList.postValue(Resource.error(e.message))
            }
        }
    }

    fun insertRun(run: Run) = viewModelScope.launch {
        repository.insertRun(run)
    }


    /*
    private var mTimer1: Timer? = null
    private var mTt1: TimerTask? = null
    private val mTimerHandler: Handler = Handler(Looper.getMainLooper())

    fun stopTimer() {
        if (mTimer1 != null) {
            mTimer1!!.cancel()
            mTimer1!!.purge()
        }
    }

    fun startTimer(predicate: () -> Unit) {
        mTimer1 = Timer()
        mTt1 = object : TimerTask() {
            override fun run() {
                mTimerHandler.post {
                    predicate()
                }
            }
        }
        mTimer1!!.schedule(mTt1, 1, 2000)
    }
    */
}
