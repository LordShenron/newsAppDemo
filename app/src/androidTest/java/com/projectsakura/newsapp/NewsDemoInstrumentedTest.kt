package com.projectsakura.newsapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class NewsDemoInstrumentedTest {

    // Specify the activity to be launched before each test
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSearchFunctionality() {
        // Type a query in the search view
        onView(withId(R.id.search_view))
            .perform(typeText("Android"))

        // Verify that the news list is displayed
        onView(withId(R.id.news_list))
            .check(matches(isDisplayed()))

        // Verify that the list contains items
        onView(withId(R.id.news_list))
            .check(matches(hasMinimumChildCount(1)))

        // Optionally, you can verify specific items in the list
        onView(withText("Android News"))
            .check(matches(isDisplayed()))
    }
}