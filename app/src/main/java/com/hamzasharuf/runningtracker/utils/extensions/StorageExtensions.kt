package com.hamzasharuf.runningtracker.utils.extensions

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

fun cacheBitmapLocally(localPath: String, bitmap: Bitmap, quality: Int = 100) {
    val file = File(localPath)
    file.createNewFile()
    val ostream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, ostream)
    ostream.flush()
    ostream.close()
}