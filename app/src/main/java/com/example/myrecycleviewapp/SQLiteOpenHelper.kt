package com.example.myrecycleviewapp

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

internal const val TOPIC_COLUMN = "TOPIC_NAME"

private const val LOG_TAG = "RSSchoolSQLiteOpenHelper"

private const val DATABASE_NAME = "itemslist.db"
private const val DATABASE_VERSION = 1
private const val CREATE_TABLE_SQL =
    "CREATE TABLE IF NOT EXISTS $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME VARCHAR(50), $COLUMN_AGE INT(50), $COLUMN_BREED VARCHAR(50));"
private const val UPDATE_TABLE_SQL =
    "DROP TABLE IF EXISTS $TABLE_NAME"


class SQLiteOpenHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    @SuppressLint("LongLogTag")
    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_TABLE_SQL)
            (1..40).forEach {
                db.execSQL("INSERT INTO $TABLE_NAME ($TOPIC_COLUMN) VALUES ('Storage Part $it');")
            }
        } catch (exception: SQLException) {
            Log.e(LOG_TAG, "Exception while trying to create database", exception)
        }
    }

    @SuppressLint("LongLogTag")
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            db.execSQL(CREATE_TABLE_SQL)
            onCreate(db)
        } catch (exception: SQLException) {
            Log.e(LOG_TAG, "Exception while trying to update database", exception)
        }
    }

    fun getCursorWithTopics(): Cursor {
        return readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getListOfTopics(): List<String> {
        val listOfTopics = mutableListOf<String>()
        getCursorWithTopics().use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val topicName = cursor.getString(cursor.getColumnIndex(TOPIC_COLUMN))
                    listOfTopics.add("From list: $topicName")
                } while (cursor.moveToNext())
            }
        }
        return listOfTopics
    }
}