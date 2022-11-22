package com.example.deborahrimei_3015579_trackingapp

import java.util.*

class Habit(var name: String) {

    var reason : String = ""
    var priority = false
    var date = Calendar.getInstance()

    // build the constructor for the Habit object
    constructor(name: String, reason: String): this(name) {
        this.reason = reason
        this.priority = priority
    }

    //create a function that returns the date as a string
    fun getDateAsString(): String {
        val year = date.get(Calendar.YEAR).toString()
        val month = date.get(Calendar.MONTH).toString()
        val day = date.get(Calendar.DAY_OF_MONTH).toString()
        return "$day/$month/$year"
    }
}