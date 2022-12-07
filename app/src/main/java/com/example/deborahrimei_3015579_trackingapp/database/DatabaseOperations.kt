package com.example.deborahrimei_3015579_trackingapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.provider.BaseColumns
import androidx.annotation.RequiresApi
import com.example.deborahrimei_3015579_trackingapp.habits.Habit
import com.example.deborahrimei_3015579_trackingapp.user.User

class DatabaseOperations(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //create a companion object where to store constant variables
    companion object {
        const val DATABASE_NAME = "TrackerHabit.db"
        const val DATABASE_VERSION = 1
    }

    /* === CREATE TABLES === */
    // override function to create both tables habit and user
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseQueries.SQL_CREATE_HABIT_TABLE_QUERY)
        db.execSQL(DatabaseQueries.SQL_CREATE_USER_TABLE_QUERY)
    }

    /* === UPGRADE A TABLE WITH NEW ONE === */
    //override function to delete and recreate the table
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(DatabaseQueries.SQL_DELETE_TABLE_QUERY)
        onCreate(db)
    }

    /* === ADD ITEM IN THE HABIT TABLE === */
    @RequiresApi(Build.VERSION_CODES.O)
    fun addHabit(dbo: DatabaseOperations, habit: Habit) {
        val db = dbo.writableDatabase
        val habitName = habit.name
        val habitReason = habit.reason
        val habitCompletion = habit.isCompleted
        val habitDate = habit.getDateAsString()
        val habitCompletionInteger = if (habitCompletion) 1 else 0

        val contentValues = ContentValues().apply {
            put(DatabaseQueries.HabitTable.COLUMN_HABIT_NAME, habitName)
            put(DatabaseQueries.HabitTable.COLUMN_HABIT_REASON, habitReason)
            put(DatabaseQueries.HabitTable.COLUMN_HABIT_COMPLETION, habitCompletionInteger)
            put(DatabaseQueries.HabitTable.COLUMN_DATE, habitDate)
        }

        //data stored in a variable rowID
        val rowID = db.insert(DatabaseQueries.HabitTable.TABLE_NAME, null, contentValues)
    }

    /* === RETURN ALL ITEMS OF THE HABIT TABLE === */
    // return a cursor (data will be stored as a table with columns and rows)
    fun getAllHabits(dbo: DatabaseOperations): Cursor {
        val db = dbo.readableDatabase

        val tableProjection = arrayOf(
            BaseColumns._ID,
            DatabaseQueries.HabitTable.COLUMN_HABIT_NAME,
            DatabaseQueries.HabitTable.COLUMN_HABIT_REASON,
            DatabaseQueries.HabitTable.COLUMN_HABIT_COMPLETION,
            DatabaseQueries.HabitTable.COLUMN_DATE
        )

        // cursor will need the following variables
        val selection = ""
        val selectionArgs = null
        val groupBy = null
        val having = null
        val sortOrder = null

        val cursor = db.query(
            DatabaseQueries.HabitTable.TABLE_NAME,
            tableProjection,
            selection,
            selectionArgs,
            groupBy,
            having,
            sortOrder
        )
        return cursor
    }

    /* === UPDATE HABIT === */
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateHabit(dbo: DatabaseOperations, oldHabit: Habit, newHabit: Habit) {
        val db = dbo.writableDatabase
        val habitName = newHabit.name
        val habitReason = newHabit.reason
        val isHabitCompleted = newHabit.isCompleted
        val habitCompletion = if (isHabitCompleted) 1 else 0
        val habitDate = newHabit.getDateAsString()

        val contentValues = ContentValues().apply {
            put(DatabaseQueries.HabitTable.COLUMN_HABIT_NAME, habitName)
            put(DatabaseQueries.HabitTable.COLUMN_HABIT_REASON, habitReason)
            put(DatabaseQueries.HabitTable.COLUMN_HABIT_COMPLETION, habitCompletion)
            put(DatabaseQueries.HabitTable.COLUMN_DATE, habitDate)
        }

        val selection = "${DatabaseQueries.HabitTable.COLUMN_HABIT_NAME} LIKE ?"
        val selectionArgs = arrayOf(oldHabit.name)

        db.update(DatabaseQueries.HabitTable.TABLE_NAME, contentValues, selection, selectionArgs)
    }

    /* === DELETE HABIT === */
    fun deleteHabit(dbo: DatabaseOperations, habit: Habit) {
        val db = dbo.writableDatabase
        val selection = "${DatabaseQueries.HabitTable.COLUMN_HABIT_NAME} LIKE ?"
        val selectionArgs = arrayOf(habit.name)

        val deletedRow = db.delete(DatabaseQueries.HabitTable.TABLE_NAME, selection, selectionArgs)
    }

    /* === ADD ITEM IN THE USER TABLE === */
    @RequiresApi(Build.VERSION_CODES.O)
    fun addUser(dbo: DatabaseOperations, user: User) {
        val db = dbo.writableDatabase
        val userName = user.name
        val userReason = user.identity
        val userPicture = user.picture
        val userNumbHabits = user.totalHabit
        val userTotalCompleted = user.totalCompleted
        val userDate = user.getDateAsString()

        val contentValues = ContentValues().apply {
            put(DatabaseQueries.UserTable.COLUMN_USER_NAME, userName)
            put(DatabaseQueries.UserTable.COLUMN_USER_IDENTITY, userReason)
            put(DatabaseQueries.UserTable.COLUMN_USER_PICTURE, userPicture)
            put(DatabaseQueries.UserTable.COLUMN_USER_NUMB_HABITS, userNumbHabits)
            put(DatabaseQueries.UserTable.COLUMN_USER_TOTAL_COMPLETED, userTotalCompleted)
            put(DatabaseQueries.UserTable.COLUMN_USER_DATE, userDate)
        }

        //data stored in a variable rowID
        db.insert(DatabaseQueries.UserTable.TABLE_USER_NAME, null, contentValues)
    }

    /* === UPDATE USER === */
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUser(dbo: DatabaseOperations, user: User) {
        val db = dbo.writableDatabase
        db.delete(DatabaseQueries.UserTable.TABLE_USER_NAME, null, null)
        dbo.addUser(dbo, user)
    }

    /* === RETURN ALL ITEMS OF THE USER TABLE === */
    // return a cursor (data will be stored as a table with columns and rows)
    fun getUser(dbo: DatabaseOperations): Cursor {
        val db = dbo.writableDatabase

        val tableProjection = arrayOf(
            BaseColumns._ID,
            DatabaseQueries.UserTable.COLUMN_USER_NAME,
            DatabaseQueries.UserTable.COLUMN_USER_IDENTITY,
            DatabaseQueries.UserTable.COLUMN_USER_PICTURE,
            DatabaseQueries.UserTable.COLUMN_USER_NUMB_HABITS,
            DatabaseQueries.UserTable.COLUMN_USER_TOTAL_COMPLETED,
            DatabaseQueries.UserTable.COLUMN_USER_DATE
        )

        // cursor will need the following variables
        val selection = ""
        val selectionArgs = null
        val groupBy = null
        val having = null
        val sortOrder = null

        val cursor = db.query(
            DatabaseQueries.UserTable.TABLE_USER_NAME,
            tableProjection,
            selection,
            selectionArgs,
            groupBy,
            having,
            sortOrder
        )
        return cursor
    }

}