package com.dicoding.todoapp.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.data.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel(private val repository: TaskRepository) : ViewModel() {
    fun insertNewTask(title: String, description: String, dueDateMillis: Long) {
        val task = Task(title = title, description = description, dueDateMillis = dueDateMillis)
        viewModelScope.launch {
            repository.insertTask(newTask = task)
        }
    }
}