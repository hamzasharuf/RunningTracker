package com.hamzasharuf.runningtracker.data.repositories

import com.hamzasharuf.runningtracker.data.database.dao.RunDao
import com.hamzasharuf.runningtracker.data.database.entities.RunDatabaseEntity
import javax.inject.Inject

class RunRepository @Inject constructor(
    val runDao: RunDao
) {
    suspend fun insertRun(run: RunDatabaseEntity) = runDao.insertRun(run)

    suspend fun deleteRun(run: RunDatabaseEntity) = runDao.deleteRun(run)

    fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()

    fun getAllRunsSortedByDistance() = runDao.getAllRunsSortedByDistance()

    fun getAllRunsSortedByTimeInMillis() = runDao.getAllRunsSortedByTimeInMillis()

    fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

    fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()

    fun getTotalDistance() = runDao.getTotalDistance()

    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()
}