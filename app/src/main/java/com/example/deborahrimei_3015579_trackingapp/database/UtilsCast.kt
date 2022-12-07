package com.example.deborahrimei_3015579_trackingapp.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

object UtilsCast {

    fun getBytes(bitmap : Bitmap) : ByteArray{
        var stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }

    fun getImage(data : ByteArray) : Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }
}