package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)

        //TODO 11 : Show detail task and implement delete action
        val taskId: Int = intent.getIntExtra(TASK_ID, 0)
        detailTaskViewModel.setTaskId(taskId = taskId)
        detailTaskViewModel.task.observe(this, ::showDetailTask)
        val btnDeleteTask: Button = findViewById(R.id.btn_delete_task)
        btnDeleteTask.setOnClickListener {
            detailTaskViewModel.deleteTask()
            onBackPressed()
        }
    }

    private fun showDetailTask(task: Task?) {
        task?.let {
            val detailEdTitle: TextInputEditText = findViewById(R.id.detail_ed_title)
            val detailEdDescription: TextInputEditText = findViewById(R.id.detail_ed_description)
            val detailEdDueDate: TextInputEditText = findViewById(R.id.detail_ed_due_date)
            detailEdTitle.setText(task.title)
            detailEdDescription.setText(task.description)
            detailEdDueDate.setText(DateConverter.convertMillisToString(timeMillis = task.dueDateMillis))
        }
    }
}