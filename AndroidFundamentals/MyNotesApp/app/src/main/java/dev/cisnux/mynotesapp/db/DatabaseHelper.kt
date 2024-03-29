package dev.cisnux.mynotesapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import dev.cisnux.mynotesapp.db.DatabaseContract.NoteColumns

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbnoteapp"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_NOTE = """
            CREATE TABLE ${NoteColumns.TABLE_NAME}
            (${NoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${NoteColumns.TITLE} TEXT NOT NULL,
            ${NoteColumns.DESCRIPTION} TEXT NOT NULL,
            ${NoteColumns.DATE} TEXT NOT NULL)
        """.trimIndent()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE ${NoteColumns.TABLE_NAME}")
        onCreate(db)
    }
}