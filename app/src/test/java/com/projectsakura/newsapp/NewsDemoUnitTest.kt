package com.projectsakura.newsapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsDemoUnitTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testNetworkAvailability() {
        // Test with active internet connection
        onView(withId(R.id.news_list)).check(matches(isDisplayed()))

        // Test without internet connection
        // Disable internet connectivity
        // Verify that the empty view is displayed
        onView(withId(R.id.empty_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testUIBehavior() {
        // Perform a click action on a RecyclerView item
        onView(withId(R.id.news_list)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Verify that the NewsDetailActivity is launched and displays the correct content
        onView(withId(R.id.news_content_text_view)).check(matches(withText("Article content")))
    }
}
