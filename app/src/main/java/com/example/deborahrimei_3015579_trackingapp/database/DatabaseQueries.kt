package com.example.deborahrimei_3015579_trackingapp.database

import android.provider.BaseColumns

object DatabaseQueries {

    // query that will initiliase the table
    const val SQL_CREATE_HABIT_TABLE_QUERY =
        "CREATE TABLE ${HabitTable.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${HabitTable.COLUMN_HABIT_NAME} TEXT," +
                "${HabitTable.COLUMN_HABIT_REASON} TEXT," +
                "${HabitTable.COLUMN_HABIT_COMPLETION} INTEGER," +
                "${HabitTable.COLUMN_DATE} TEXT" + ")"

    // query that will delete the table
    const val SQL_DELETE_TABLE_QUERY = "DROP TABLE IF EXISTS ${HabitTable.TABLE_NAME}"

    //create interface for the Table with all the habit fields
    object HabitTable : BaseColumns {
        const val TABLE_NAME = "habitsTable"
        const val COLUMN_HABIT_NAME = "habitName"
        const val COLUMN_HABIT_REASON = "habitReason"
        const val COLUMN_HABIT_COMPLETION = "habitCompletion"
        const val COLUMN_DATE = "habitDate"
    }

    // query that will initiliase the table
    const val SQL_CREATE_USER_TABLE_QUERY =
        "CREATE TABLE ${UserTable.TABLE_USER_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${UserTable.COLUMN_USER_NAME} TEXT," +
                "${UserTable.COLUMN_USER_IDENTITY} TEXT," +
                "${UserTable.COLUMN_USER_PICTURE} BLOB, " +
                "${UserTable.COLUMN_USER_NUMB_HABITS} TEXT,"+
                "${UserTable.COLUMN_USER_TOTAL_COMPLETED} TEXT,"+
                "${UserTable.COLUMN_USER_DATE} TEXT)"

    // query that will delete the table
    const val SQL_DELETE_USER_QUERY = "DROP TABLE IF EXISTS ${UserTable.TABLE_USER_NAME}"

    //create interface for the Table with all the habit fields
    object UserTable : BaseColumns {
        const val TABLE_USER_NAME = "userTable"
        const val COLUMN_USER_NAME = "userName"
        const val COLUMN_USER_IDENTITY = "userIdentity"
        const val COLUMN_USER_PICTURE = "userPicture"
        const val COLUMN_USER_NUMB_HABITS = "userNumbHabits"
        const val COLUMN_USER_TOTAL_COMPLETED = "userTotalCompleted"
        const val COLUMN_USER_DATE = "userStartDate"

    }
}