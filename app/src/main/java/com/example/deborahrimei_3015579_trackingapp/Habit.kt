package com.example.deborahrimei_3015579_trackingapp

import java.util.*

class Habit(var name: String) {
    var reason: String = ""
    var isCompleted: Boolean = false
    var date = Calendar.getInstance()

    // build the constructor for the Habit object
    constructor(name: String, reason: String, isCompleted: Boolean) : this(name) {
        this.reason = reason
        this.isCompleted = isCompleted
    }

    //create a function that returns the date as a string
    fun getDateAsString(): String {
        val year = date.get(Calendar.YEAR).toString()
        val month = date.get(Calendar.MONTH).toString()
        val day = date.get(Calendar.DAY_OF_MONTH).toString()
        return "$day/${month + 1}/$year"
    }



    override fun toString(): String {
        return "$name, $reason, $isCompleted, ${getDateAsString()}"
    }
}