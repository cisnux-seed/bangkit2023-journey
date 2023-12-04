package com.dicoding.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO 1 : Define a local database table using the schema in app/schema/tasks.json
private const val TASKS = "tasks"
@Entity(tableName = TASKS)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    @ColumnInfo(name = "dueDate")
    val dueDateMillis: Long,
    @ColumnInfo(name = "completed")
    val isCompleted: Boolean = false
)
