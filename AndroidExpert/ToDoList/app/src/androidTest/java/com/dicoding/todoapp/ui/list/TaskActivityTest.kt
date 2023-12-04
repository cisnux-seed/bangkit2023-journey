package com.dicoding.todoapp.ui.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.add.AddTaskActivity

//TODO 16 : Write UI test to validate when user tap Add Task (+), the AddTaskActivity displayed
@RunWith(AndroidJUnit4::class)
class TaskActivityTest {
    @get:Rule
    val activity = ActivityScenarioRule(TaskActivity::class.java)

    @Test
    fun navigateToTaskActivity_Success() {
        Intents.init()
        onView(withId(R.id.fab)).perform(click())
        intended(hasComponent(AddTaskActivity::class.java.name))
    }
}