package com.hamzasharuf.runningtracker.utils.extensions

import android.graphics.Bitmap
import timber.log.Timber
import java.io.File

private const val TAG = "timberTag  --> "

fun Any.timber(message: String?) {
    Timber.d(TAG.plus(message))
}

fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}
