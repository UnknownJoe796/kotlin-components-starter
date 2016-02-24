package com.ivieleague.kotlin_components_starter

import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.lightningkite.kotlincomponents.addView
import com.lightningkite.kotlincomponents.viewcontroller.AutocleanViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.button
import org.jetbrains.anko.onClick

/**
 * Created as a dummy VC to test out the stack.
 * Created by josep on 11/6/2015.
 */
class AnotherVC(val stack: VCStack) : AutocleanViewController() {
    override fun make(activity: VCActivity): View {
        return LinearLayout(activity).apply {
            setGravity(Gravity.CENTER)
            orientation = LinearLayout.VERTICAL

            addView<TextView>() {
                text = "Something else"
            }

            button("asdf") {
                onClick {
                    activity.alert("This is a test message.", "Test Message") {
                        positiveButton("Yes") {}
                        negativeButton("no") { }
                    }.show()
                }
            }

            addView<Button>() {
                text = "Go back"
                setOnClickListener {
                    stack.pop()
                }
            }
            /*return linearLayout(activity) {
                setGravity(Gravity.CENTER)
                orientation = vertical

                textView("Something else")

                button("Go back") {
                    onClick {
                        stack.pop()
                    }
                }
            }*/
        }
    }
}