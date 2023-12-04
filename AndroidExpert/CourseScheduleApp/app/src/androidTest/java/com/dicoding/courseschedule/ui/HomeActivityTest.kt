package com.dicoding.courseschedule.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @get:Rule
    val activity = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun navigateToAddCourseActivity_Success() {
        Intents.init()
        Espresso.onView(withId(R.id.action_add)).perform(ViewActions.click())
        Intents.intended(hasComponent(AddCourseActivity::class.java.name))
    }
}