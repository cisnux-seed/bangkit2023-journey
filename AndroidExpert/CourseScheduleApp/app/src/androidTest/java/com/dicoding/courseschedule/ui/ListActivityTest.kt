package com.dicoding.courseschedule.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import com.dicoding.courseschedule.ui.list.ListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListActivityTest {
    @get:Rule
    val activity = ActivityScenarioRule(ListActivity::class.java)

    @Test
    fun navigateToAddCourseActivity_Success() {
        Intents.init()
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(AddCourseActivity::class.java.name))
    }
}