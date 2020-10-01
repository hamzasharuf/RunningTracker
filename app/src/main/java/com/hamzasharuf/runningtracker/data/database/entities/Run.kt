package com.hamzasharuf.runningtracker.data.database.entities

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Run.TABLE_NAME)
data class Run(
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    companion object{
        const val TABLE_NAME = "runs_table"
    }
}