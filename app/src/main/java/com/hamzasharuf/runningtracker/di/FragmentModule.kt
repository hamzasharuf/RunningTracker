package com.hamzasharuf.runningtracker.di

import android.content.Context
import com.hamzasharuf.runningtracker.ui.screens.statistics.StatisticsHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @FragmentScoped
    @Provides
    fun provideStatisticsHandler(@ApplicationContext context: Context) = StatisticsHandler(context)
}