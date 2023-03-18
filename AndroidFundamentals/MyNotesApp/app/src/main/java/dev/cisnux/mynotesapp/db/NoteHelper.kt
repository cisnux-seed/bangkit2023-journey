package dev.cisnux.mynotesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import dev.cisnux.mynotesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import dev.cisnux.mynotesapp.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException

class NoteHelper(context: Context) {
    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor = database.query(
        DATABASE_TABLE,
        null,
        null,
        null,
        null,
        null,
        "$_ID ASC"
    )

    fun queryById(id: String): Cursor = database.query(
        DATABASE_TABLE,
        null,
        "$_ID = ?",
        arrayOf(id),
        null,
        null,
        null,
        null
    )

    fun insert(values: ContentValues?): Long =
        database.insert(DATABASE_TABLE, null, values)

    fun updateById(id: String, values: ContentValues?): Int =
        database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))

    fun deleteById(id: String):Int =
        // use parameterized ? to prevent SQL injection
        // is same as "$_ID = $'id'" but with parameterized
        // it's more safety than without it
        database.delete(DATABASE_TABLE, "$_ID = ?", arrayOf(id))

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }
    }
}