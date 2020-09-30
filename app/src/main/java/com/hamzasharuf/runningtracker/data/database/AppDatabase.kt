package com.hamzasharuf.runningtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hamzasharuf.runningtracker.data.database.DatabaseConfig.DATABASE_VERSION
import com.hamzasharuf.runningtracker.data.database.dao.RunDao
import com.hamzasharuf.runningtracker.data.database.entities.Run

@Database(
    entities = [Run::class],
    version = DATABASE_VERSION
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRunDao(): RunDao
}