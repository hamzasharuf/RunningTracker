package com.hamzasharuf.runningtracker

import android.app.Application
import com.hamzasharuf.runningtracker.utils.services.TrackingNotification
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        setupTimber()
        TrackingNotification.createNotificationChannel(this)
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}