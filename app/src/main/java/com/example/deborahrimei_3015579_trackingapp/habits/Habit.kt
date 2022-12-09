package com.example.deborahrimei_3015579_trackingapp.habits

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

class Habit(var name: String) {

    //variables
    lateinit var reason: String
    var isCompleted: Boolean = false
    var creationDate: String = ""
    lateinit var period: Period
    lateinit var lastDate: Date
    var totalCompleted: Int = 0
    var maxCompleted: Int = 0

    // build the constructor for the Habit object
    @RequiresApi(Build.VERSION_CODES.O) constructor(name: String, reason: String,
            isCompleted: Boolean, period: Period, creationDate: String) : this(name) {
        this.reason = reason
        this.isCompleted = isCompleted
        this.period = period
        this.creationDate = creationDate
    }


    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("dd MMM YY")


    //create a function that returns the date as a string
    @RequiresApi(Build.VERSION_CODES.O) fun getDateAsString(): String {
        return LocalDate.now().format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O) override fun toString(): String {
        return "$name, $reason, $isCompleted, ${period.toString()}, $creationDate"
    }
}