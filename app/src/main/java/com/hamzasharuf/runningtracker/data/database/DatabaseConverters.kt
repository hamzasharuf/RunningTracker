package com.hamzasharuf.runningtracker.data.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64.*
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class DatabaseConverters {

    @TypeConverter
    fun toBitmap(encodedString: String): Bitmap {
        val encodeByte = decode(encodedString, DEFAULT);
        return BitmapFactory . decodeByteArray (encodeByte, 0, encodeByte.size)
    }

    @TypeConverter
    fun fromBitmap(bmp: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return encodeToString(baos.toByteArray(), DEFAULT)
    }
}