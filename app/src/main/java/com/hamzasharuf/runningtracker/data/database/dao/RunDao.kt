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
    fun getAllRunsSortedByDateDesc(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY timestamp ASC")
    fun getAllRunsSortedByDateAsc(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY timeInMillis DESC")
    fun getAllRunsSortedByTimeInMillis(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY caloriesBurned DESC")
    fun getAllRunsSortedByCaloriesBurned(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY avgSpeedInKMH DESC")
    fun getAllRunsSortedByAvgSpeed(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY distanceInMeters DESC")
    fun getAllRunsSortedByDistance(): LiveData<List<Run>>

    @Query("SELECT SUM(timeInMillis) FROM ${Run.TABLE_NAME}")
    fun getTotalTimeInMillis(): LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM ${Run.TABLE_NAME}")
    fun getTotalCaloriesBurned(): LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM ${Run.TABLE_NAME}")
    fun getTotalDistance(): LiveData<Int>

    @Query("SELECT AVG(avgSpeedInKMH) FROM ${Run.TABLE_NAME}")
    fun getTotalAvgSpeed(): LiveData<Float>
}
