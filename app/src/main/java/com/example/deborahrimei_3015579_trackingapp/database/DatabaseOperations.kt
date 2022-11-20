package com.example.deborahrimei_3015579_trackingapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOperations(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //create a companion object where to store constant variables
        companion object {
            const val DATABASE_NAME = "TrackerHabit.db"
            const val DATABASE_VERSION = 1
        }

    // override function to create a table
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    //override function to delete and recreate the table
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}