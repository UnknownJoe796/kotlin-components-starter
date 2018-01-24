package com.ivieleague.kotlin_components_starter

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.ViewMatchers.withInputType
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.lightningkite.kotlin.anko.FullInputType
import com.lightningkite.kotlin.async.Async
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 *
 * Created by joseph on 11/3/17.
 */
@RunWith(value = AndroidJUnit4::class)
@LargeTest
class ExampleLoginVCEspressoTest {
    var loginData: LoginData? = null
    init {
        MainActivity.main = MainScreen().apply {
            stack.root()
            stack.push(ExampleLoginVC({ loginData = it; stack.pop() }))
        }
    }

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)


    val asyncIdlingResource = object : IdlingResource {
        var callback: IdlingResource.ResourceCallback? = null
        override fun getName(): String = "Async"
        override fun isIdleNow(): Boolean {
            val idle = Async.threadPool.activeCount == 0
            if (idle) {
                callback?.onTransitionToIdle()
            }
            return idle
        }

        override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
            this.callback = callback
        }
    }

    @Before
    fun before() {
        Espresso.registerIdlingResources(asyncIdlingResource)
    }

    @After
    fun after() {
        Espresso.unregisterIdlingResources(asyncIdlingResource)
    }

    @Test
    fun testLogin() {
        loginData = null

        onView(withInputType(FullInputType.EMAIL))
                .perform(click())
                .perform(typeText("test@gmail.com"))

        onView(withInputType(FullInputType.PASSWORD))
                .perform(click())
                .perform(typeText("testpass"))

        onView(withText(R.string.log_in))
                .perform(click())

        assert(MainActivity.main.stack.size == 1)
        assert(loginData != null)
    }
}