package com.ivieleague.kotlin_components_starter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.test.InstrumentationRegistry
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.lightningkite.kotlin.anko.FullInputType
import com.lightningkite.kotlin.anko.forThisAndAllChildrenRecursive
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.async.Async
import com.lightningkite.kotlin.async.AsyncInterface
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.childrenSequence
import org.junit.Assert
import org.junit.Test

/**
 *
 * Created by joseph on 11/3/17.
 */
class ExampleLoginVCFullTest {


    @Test
    fun testLogins() {
        // Mocking
        val appContext = InstrumentationRegistry.getTargetContext()
        val mockVCContext = object : VCContext {
            override val activity: Activity? get() = null
            override val context: Context = appContext
            override val onResume: MutableCollection<() -> Unit> = ArrayList()
            override val onPause: MutableCollection<() -> Unit> = ArrayList()
            override val onSaveInstanceState: MutableCollection<(outState: Bundle) -> Unit> = ArrayList()
            override val onLowMemory: MutableCollection<() -> Unit> = ArrayList()
            override val onDestroy: MutableCollection<() -> Unit> = ArrayList()
            override val onActivityResult: MutableCollection<(request: Int, result: Int, data: Intent?) -> Unit> = ArrayList()
            override fun prepareOnResult(presetCode: Int, onResult: (Int, Intent?) -> Unit): Int = 0
            override fun requestPermissions(permission: Array<String>, onResult: (Map<String, Int>) -> Unit) {
                onResult.invoke(permission.associate { it to 0 })
            }

            override fun requestPermission(permission: String, onResult: (Boolean) -> Unit) {
                onResult.invoke(true)
            }
        }
        val fakeUIThread = HandlerThread("FakeUIThread")
        fakeUIThread.start()
        val handler = Handler(fakeUIThread.looper)
        Async.uiThreadInterface = object : AsyncInterface {
            override fun sendToThread(action: () -> Unit) {
                handler.post(action)
            }
        }


        //Make the VC
        var loginData: LoginData? = null
        val vc = ExampleLoginVC({
            loginData = it
        })


        //Pull components out of the view
        val view = vc.createView(AnkoContext.Companion.create(ContextThemeWrapper(appContext, R.style.AppTheme), mockVCContext))
        view.forThisAndAllChildrenRecursive {
            it.lifecycle.onViewAttachedToWindow(it)
        }

        val emailEditText = view.childrenRecursive()
                .findOfType { it: EditText -> it.inputType == FullInputType.EMAIL } ?: throw IllegalStateException("Email view not found!")

        val passwordEditText = view.childrenRecursive()
                .findOfType { it: EditText -> it.inputType == FullInputType.PASSWORD } ?: throw IllegalStateException("Password view not found!")

        val loginButton = view.childrenRecursive()
                .findOfType { it: Button -> it.text.contains("log", ignoreCase = true) } ?: throw IllegalStateException("Login view not found!")


        //Email empty
        handler.post {
            emailEditText.setText("")
            passwordEditText.setText("testpass")
        }
        handler.post {
            loginButton.performClick()
        }
        Thread.sleep(50)
        Assert.assertEquals(appContext.getString(R.string.validation_email_blank), emailEditText.error)

        //Email invalid
        handler.post {
            emailEditText.setText("invalid")
            passwordEditText.setText("testpass")
        }
        handler.post {
            loginButton.performClick()
        }
        Thread.sleep(50)
        Assert.assertTrue(emailEditText.error?.contains(appContext.getString(R.string.validation_email_invalid, "invalid")) ?: false)

        //Password empty
        handler.post {
            emailEditText.setText("test@gmail.com")
            passwordEditText.setText("")
        }
        handler.post {
            loginButton.performClick()
        }
        Thread.sleep(50)
        Assert.assertEquals(appContext.getString(R.string.validation_password_empty), passwordEditText.error)

        //Password short
        handler.post {
            emailEditText.setText("test@gmail.com")
            passwordEditText.setText("wrong")
        }
        handler.post {
            loginButton.performClick()
        }
        Thread.sleep(50)
        Assert.assertEquals(appContext.getString(R.string.validation_password_short), passwordEditText.error)

        //Success
        handler.post {
            emailEditText.setText("test@gmail.com")
            passwordEditText.setText("testpass")
        }
        handler.post {
            loginButton.performClick()
        }
        Thread.sleep(500)
        assert(loginData != null)




        fakeUIThread.quitSafely()
    }

    fun View.childrenRecursive(): Sequence<View> = object : Sequence<View> {
        override fun iterator(): Iterator<View> = object : Iterator<View> {

            val parentQueue = ArrayList<ViewGroup>()
            var currentIterator = this@childrenRecursive.childrenSequence().iterator()

            override fun hasNext(): Boolean {
                while (!currentIterator.hasNext() && parentQueue.isNotEmpty()) {
                    currentIterator = parentQueue.removeAt(0).childrenSequence().iterator()
                }
                return currentIterator.hasNext()
            }

            override fun next(): View {
                if (!hasNext()) throw NoSuchElementException()
                val new = currentIterator.next()
                if (new is ViewGroup) {
                    parentQueue.add(new)
                }
                return new
            }

        }
    }

    inline fun <A, reified B : A> Sequence<A>.findOfType(predicate: (B) -> Boolean): B? {
        return firstOrNull {
            if (it is B) predicate(it)
            else false
        } as? B
    }
}