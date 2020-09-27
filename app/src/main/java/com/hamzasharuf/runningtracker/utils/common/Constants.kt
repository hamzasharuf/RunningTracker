package com.hamzasharuf.runningtracker.utils.common

import android.content.Context
import android.graphics.Color
import javax.inject.Inject

object Constants {

    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"

    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L

    var POLYLINE_COLOR = Color.parseColor("#B22196F3")
    const val POLYLINE_WIDTH = 16f
    const val MAP_ZOOM = 17f

    const val TIMER_UPDATE_INTERVAL = 50L
}