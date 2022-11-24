package com.example.deborahrimei_3015579_trackingapp.database

import android.provider.BaseColumns

object DatabaseQueries {

    // query that will initiliase the table
    const val SQL_CREATE_TABLE_QUERY =
        "CREATE TABLE ${TableInfo.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.COLUMN_HABIT_NAME} TEXT," +
                "${TableInfo.COLUMN_HABIT_REASON} TEXT," +
                "${TableInfo.COLUMN_HABIT_COMPLETION} INTEGER," +
                "${TableInfo.COLUMN_DATE} TEXT" + ")"

    // query that will delete the table
    const val SQL_DELETE_TABLE_QUERY = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"

    //create interface for the Table with all the habit fields
    object TableInfo : BaseColumns {
        const val TABLE_NAME = "habitsTable"
        const val COLUMN_HABIT_NAME = "habitName"
        const val COLUMN_HABIT_REASON = "habitReason"
        const val COLUMN_HABIT_COMPLETION = "habitCompletion"
        const val COLUMN_DATE = "habitDate"
    }
}