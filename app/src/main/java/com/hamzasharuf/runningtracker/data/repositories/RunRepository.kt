package com.hamzasharuf.runningtracker.data.repositories

import com.hamzasharuf.runningtracker.data.database.dao.RunDao
import com.hamzasharuf.runningtracker.data.database.entities.Run
import javax.inject.Inject

class RunRepository @Inject constructor(
    val runDao: RunDao
) {
    suspend fun insertRun(run: Run) = runDao.insertRun(run)

    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

    fun getAllRunsSortedByDateDesc() = runDao.getAllRunsSortedByDateDesc()

    fun getAllRunsSortedByDateAsc() = runDao.getAllRunsSortedByDateAsc()

    fun getAllRunsSortedByDistanceDesc() = runDao.getAllRunsSortedByDistanceDesc()

    fun getAllRunsSortedByDistanceAsc() = runDao.getAllRunsSortedByDistanceAsc()

    fun getAllRunsSortedByTimeInMillisDesc() = runDao.getAllRunsSortedByTimeInMillisDesc()

    fun getAllRunsSortedByTimeInMillisAsc() = runDao.getAllRunsSortedByTimeInMillisAsc()

    fun getAllRunsSortedByAvgSpeedDesc() = runDao.getAllRunsSortedByAvgSpeedDesc()

    fun getAllRunsSortedByAvgSpeedAsc() = runDao.getAllRunsSortedByAvgSpeedAsc()

    fun getAllRunsSortedByCaloriesBurnedDesc() = runDao.getAllRunsSortedByCaloriesBurnedDesc()

    fun getAllRunsSortedByCaloriesBurnedAsc() = runDao.getAllRunsSortedByCaloriesBurnedAsc()

    fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()

    fun getTotalDistance() = runDao.getTotalDistance()

    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()
}