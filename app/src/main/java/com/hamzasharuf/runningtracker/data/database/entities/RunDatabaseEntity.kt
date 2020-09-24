package com.hamzasharuf.runningtracker.data.database.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RunDatabaseEntity.TABLE_NAME)
data class RunDatabaseEntity(
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    companion object{
        const val TABLE_NAME = "runs_table"
    }
}