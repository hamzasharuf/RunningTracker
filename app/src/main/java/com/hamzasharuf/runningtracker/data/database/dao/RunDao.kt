package com.hamzasharuf.runningtracker.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hamzasharuf.runningtracker.data.database.entities.Run

@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(runDatabase: Run)

    @Delete
    suspend fun deleteRun(runDatabase: Run)

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY timestamp DESC")
    fun getAllRunsSortedByDateDesc(): List<Run>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY timestamp ASC")
    fun getAllRunsSortedByDateAsc(): List<Run>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY timeInMillis DESC")
    fun getAllRunsSortedByTimeInMillisDesc(): List<Run>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY timeInMillis ASC")
    fun getAllRunsSortedByTimeInMillisAsc(): List<Run>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY caloriesBurned DESC")
    fun getAllRunsSortedByCaloriesBurnedDesc(): List<Run>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY caloriesBurned ASC")
    fun getAllRunsSortedByCaloriesBurnedAsc(): List<Run>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY avgSpeedInKMH DESC")
    fun getAllRunsSortedByAvgSpeedDesc(): List<Run>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY avgSpeedInKMH ASC")
    fun getAllRunsSortedByAvgSpeedAsc(): List<Run>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY distanceInMeters DESC")
    fun getAllRunsSortedByDistanceDesc(): List<Run>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY distanceInMeters ASC")
    fun getAllRunsSortedByDistanceAsc(): List<Run>

    @Query("SELECT SUM(timeInMillis) FROM ${Run.TABLE_NAME}")
    fun getTotalTimeInMillis(): Long

    @Query("SELECT SUM(caloriesBurned) FROM ${Run.TABLE_NAME}")
    fun getTotalCaloriesBurned(): Int

    @Query("SELECT SUM(distanceInMeters) FROM ${Run.TABLE_NAME}")
    fun getTotalDistance(): Int

    @Query("SELECT AVG(avgSpeedInKMH) FROM ${Run.TABLE_NAME}")
    fun getTotalAvgSpeed(): Float
}
