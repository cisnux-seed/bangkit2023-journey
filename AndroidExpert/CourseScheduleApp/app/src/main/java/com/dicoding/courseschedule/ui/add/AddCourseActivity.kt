package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.databinding.ActivityAddCourseBinding
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.Event
import com.dicoding.courseschedule.util.TimePicker
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var binding: ActivityAddCourseBinding
    private val viewModel: AddCourseViewModel by viewModels {
        ListViewModelFactory.createFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initActions()
        viewModel.saved.observe(this, ::showSnackBar)
        binding.tvStartTime.text = getString(R.string.default_start_time)
        binding.tvEndTime.text = getString(R.string.default_end_time)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_insert -> {
                with(binding) {
                    viewModel.insertCourse(
                        courseName = edCourseName.text.toString(),
                        day = spinnerDay.selectedItemPosition,
                        startTime = tvStartTime.text.toString(),
                        endTime = tvEndTime.text.toString(),
                        note = edNote.text.toString(),
                        lecturer = edLecturer.text.toString()
                    )
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    private fun initActions() {
        with(binding) {
            ibStartTime.setOnClickListener {
                showStartTimePicker()
            }
            ibEndTime.setOnClickListener {
                showEndTimePicker()
            }
        }
    }

    private fun showStartTimePicker() {
        val timePickerDialog = TimePickerFragment()
        timePickerDialog.show(supportFragmentManager, TimePicker.START_TIME.name)
    }

    private fun showEndTimePicker() {
        val timePickerDialog = TimePickerFragment()
        timePickerDialog.show(supportFragmentManager, TimePicker.END_TIME.name)
    }

    private fun showSnackBar(eventMessage: Event<Boolean>) {
        val isAlreadySaved = eventMessage.getContentIfNotHandled() ?: return
        if (!isAlreadySaved) {
            Snackbar.make(
                findViewById(R.id.constraint_layout),
                getString(R.string.input_empty_message),
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            onBackPressed()
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        when (tag) {
            TimePicker.START_TIME.name -> {
                binding.tvStartTime.text = dateFormat.format(calendar.time)
            }

            TimePicker.END_TIME.name -> {
                binding.tvEndTime.text = dateFormat.format(calendar.time)
            }
        }
    }
}