package com.example.deborahrimei_3015579_trackingapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.deborahrimei_3015579_trackingapp.Habit

class DatabaseOperations(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //create a companion object where to store constant variables
    companion object {
        const val DATABASE_NAME = "TrackerHabit.db"
        const val DATABASE_VERSION = 1
    }

    /* === CREATE A TABLE === */
    // override function to create a table
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseQueries.SQL_CREATE_TABLE_QUERY)
    }

    /* === UPGRADE A TABLE WITH NEW ONE === */
    //override function to delete and recreate the table
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(DatabaseQueries.SQL_DELETE_TABLE_QUERY)
        onCreate(db)
    }

    /* === ADD ITEM IN THE TABLE === */
    fun addHabit(dbo: DatabaseOperations, habit: Habit) {
        val db = dbo.writableDatabase
        val habitName = habit.name
        val habitReason = habit.reason
        val habitCompletion = habit.isCompleted
        val habitDate = habit.getDateAsString()
        val habitCompletionInteger = if (habitCompletion) 1 else 0

        val contentValues = ContentValues().apply {
            put(DatabaseQueries.TableInfo.COLUMN_HABIT_NAME, habitName)
            put(DatabaseQueries.TableInfo.COLUMN_HABIT_REASON, habitReason)
            put(DatabaseQueries.TableInfo.COLUMN_HABIT_COMPLETION, habitCompletionInteger)
            put(DatabaseQueries.TableInfo.COLUMN_DATE, habitDate)
        }

        //data stored in a variable rowID
        val rowID = db.insert(DatabaseQueries.TableInfo.TABLE_NAME, null, contentValues)

    }


    /* === RETURN ALL ITEMS OF THE TABLE === */
    // return a cursor (data will be stored as a table with columns and rows)
    fun getAllHabits(db0: DatabaseOperations): Cursor {
        val db = db0.readableDatabase

        val tableProjection = arrayOf(
            BaseColumns._ID,
            DatabaseQueries.TableInfo.COLUMN_HABIT_NAME,
            DatabaseQueries.TableInfo.COLUMN_HABIT_REASON,
            DatabaseQueries.TableInfo.COLUMN_HABIT_COMPLETION,
            DatabaseQueries.TableInfo.COLUMN_DATE
        )

        // cursor will need the following variables
        val selection = ""
        val selectionArgs = null
        val groupBy = null
        val having = null
        val sortOrder = null

        val cursor = db.query(
            DatabaseQueries.TableInfo.TABLE_NAME,
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
    fun updateItem(dbo: DatabaseOperations, oldHabit: Habit, newHabit: Habit) {
        val db = dbo.writableDatabase
        val habitName = newHabit.name
        val habitReason = newHabit.reason
        val isHabitCompleted = newHabit.isCompleted
        val habitCompletion = if (isHabitCompleted) 1 else 0
        val itemDate = newHabit.getDateAsString()

        val contentValues = ContentValues().apply {
            put(DatabaseQueries.TableInfo.COLUMN_HABIT_NAME, habitName)
            put(DatabaseQueries.TableInfo.COLUMN_HABIT_REASON, habitReason)
            put(DatabaseQueries.TableInfo.COLUMN_HABIT_COMPLETION, habitCompletion)
            put(DatabaseQueries.TableInfo.COLUMN_DATE, itemDate)
        }

        val selection = "${DatabaseQueries.TableInfo.COLUMN_HABIT_NAME} LIKE ?"
        val selectionArgs = arrayOf(oldHabit.name)

        db.update(DatabaseQueries.TableInfo.TABLE_NAME, contentValues, selection, selectionArgs)
    }

    /* === DELETE HABIT === */
    fun deleteHabit(dbo: DatabaseOperations, habit: Habit) {
        val db = dbo.writableDatabase
        val selection = "${DatabaseQueries.TableInfo.COLUMN_HABIT_NAME} LIKE ?"
        val selectionArgs = arrayOf(habit.name)

        val deletedRow = db.delete(DatabaseQueries.TableInfo.TABLE_NAME, selection, selectionArgs)
    }

}