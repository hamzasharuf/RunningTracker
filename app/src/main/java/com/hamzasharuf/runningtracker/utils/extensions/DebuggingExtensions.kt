package com.hamzasharuf.runningtracker.utils.extensions

import android.app.Activity
import androidx.fragment.app.Fragment
import timber.log.Timber

private const val TAG = "timberTag  --> "

fun Any.timber(message: String?){
    Timber.d(TAG.plus(message))
}