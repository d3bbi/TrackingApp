package com.example.deborahrimei_3015579_trackingapp.user

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class User() {
    lateinit var name: String
    lateinit var identity: String
    var picture : ByteArray? = null
    var totalHabit : Int = 0
    var totalCompleted: Int = 0
    lateinit var startDate: String


    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("dd MMM YY")

    constructor(name: String, reason: String, picture: ByteArray?, totalHabit:Int, totalCompleted:Int) : this() {
        this.name = name
        this.identity = reason
        this.picture = picture
        this.totalHabit = totalHabit
        this.totalCompleted = totalCompleted
    }



    //create a function that returns the date as a string
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateAsString(): String {
        return LocalDate.now().format(formatter)
    }

    override fun toString(): String {
        return "User: $name , $identity , $startDate"
    }
}

