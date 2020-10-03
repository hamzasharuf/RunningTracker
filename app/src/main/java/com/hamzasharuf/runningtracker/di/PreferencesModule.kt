package com.hamzasharuf.runningtracker.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.hamzasharuf.runningtracker.data.preferences.PreferencesConfig.IS_FIRST_RUN_KEY
import com.hamzasharuf.runningtracker.data.preferences.PreferencesConfig.NAME_KEY
import com.hamzasharuf.runningtracker.data.preferences.PreferencesConfig.PREFERENCES_NAME_KEY
import com.hamzasharuf.runningtracker.data.preferences.PreferencesConfig.WEIGHT_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context): SharedPreferences =
        app.getSharedPreferences(PREFERENCES_NAME_KEY, MODE_PRIVATE)

    /*
     * If you have multiple strings or floats you have to use annotations to differentiate between them
     */

    @Singleton
    @Provides
    fun provideName(sharedPref: SharedPreferences) = sharedPref.getString(NAME_KEY, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPref: SharedPreferences) = sharedPref.getFloat(WEIGHT_KEY, 80f)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
        sharedPref.getBoolean(IS_FIRST_RUN_KEY, true)
}
