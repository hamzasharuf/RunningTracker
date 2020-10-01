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
    fun getAllRunsSortedByTimeInMillisDesc(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY timeInMillis ASC")
    fun getAllRunsSortedByTimeInMillisAsc(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY caloriesBurned DESC")
    fun getAllRunsSortedByCaloriesBurnedDesc(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY caloriesBurned ASC")
    fun getAllRunsSortedByCaloriesBurnedAsc(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY avgSpeedInKMH DESC")
    fun getAllRunsSortedByAvgSpeedDesc(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY avgSpeedInKMH ASC")
    fun getAllRunsSortedByAvgSpeedAsc(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY distanceInMeters DESC")
    fun getAllRunsSortedByDistanceDesc(): LiveData<List<Run>>

    @Query("SELECT * FROM ${Run.TABLE_NAME} ORDER BY distanceInMeters ASC")
    fun getAllRunsSortedByDistanceAsc(): LiveData<List<Run>>

    @Query("SELECT SUM(timeInMillis) FROM ${Run.TABLE_NAME}")
    fun getTotalTimeInMillis(): LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM ${Run.TABLE_NAME}")
    fun getTotalCaloriesBurned(): LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM ${Run.TABLE_NAME}")
    fun getTotalDistance(): LiveData<Int>

    @Query("SELECT AVG(avgSpeedInKMH) FROM ${Run.TABLE_NAME}")
    fun getTotalAvgSpeed(): LiveData<Float>
}
