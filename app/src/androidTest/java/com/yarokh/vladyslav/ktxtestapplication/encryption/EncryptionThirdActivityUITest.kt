package com.yarokh.vladyslav.ktxtestapplication.encryption

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yarokh.vladyslav.ktxtestapplication.R
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class EncryptionThirdActivityUITest {
    private var alias = ""
    private var str = ""

    private lateinit var encryptionHelper: EncryptionHelper

    @get:Rule
    var rule: ActivityScenarioRule<EncryptionThirdActivity> = ActivityScenarioRule(
        EncryptionThirdActivity::class.java
    )

    @Test
    fun encryptEmpty(){
        onView(withId(R.id.btnEncrypt)).perform(click())
        onView(withId(R.id.tvResult)).check(matches(`is`(withText(""))))
    }

    @Test
    fun encrypt(){
        onView(withId(R.id.etAlias)).perform(typeText(alias))
        onView(withId(R.id.btnEncrypt)).perform(click())
        onView(withId(R.id.tvResult)).check(matches(not(withText(""))))
    }

    @Test
    fun generateKey(){
        onView(withId(R.id.btnRegkey)).perform(click())
        onView(withId(R.id.tvResult)).check(matches(`is`(withText("true"))))
    }

    @Test
    fun decrypt(){
        onView(withId(R.id.etAlias)).perform(typeText(alias))
        onView(withId(R.id.btnEncrypt)).perform(click())
        onView(withId(R.id.btnDecrypt)).perform(click())
        onView(withId(R.id.tvResult)).check(matches(`is`(withText(str))))
    }

    @Before
    fun setUp() {
        alias = "DSheremetov@beeline.kz"
        str = "vlados the best"

        rule.scenario.onActivity {
            encryptionHelper = it.encryptionHelper
        }
    }

    @After
    fun tearDown() {
        rule.scenario.close()
    }
}