package com.dicoding.habitapp.ui.list

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.habitapp.ui.add.AddHabitActivity
import org.junit.Rule
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test
import org.junit.runner.RunWith
import com.dicoding.habitapp.R

//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed
@RunWith(AndroidJUnit4::class)
class HabitActivityTest {
    @get:Rule
    val activity = ActivityScenarioRule(HabitListActivity::class.java)

    @Test
    fun navigateToAddHabitActivity_Success() {
        Intents.init()
        Espresso.onView(withId(R.id.fab)).perform(ViewActions.click())
        Intents.intended(hasComponent(AddHabitActivity::class.java.name))
    }
}