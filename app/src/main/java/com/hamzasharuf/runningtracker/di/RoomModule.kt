package com.hamzasharuf.runningtracker.di

import android.content.Context
import androidx.room.Room
import com.hamzasharuf.runningtracker.data.database.AppDatabase
import com.hamzasharuf.runningtracker.data.database.DatabaseConfig.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: AppDatabase) = db.getRunDao()
}