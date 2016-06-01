package com.ivieleague.kotlin_components_starter

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.ivieleague.kotlin.anko.viewcontrollers.AutocleanViewController
import com.ivieleague.kotlin.anko.viewcontrollers.containers.VCStack
import com.ivieleague.kotlin.anko.viewcontrollers.implementations.VCActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.button
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textView

/**
 * Created as a dummy VC to test out the stack.
 * Created by josep on 11/6/2015.
 */
class AnotherVC(val stack: VCStack) : AutocleanViewController() {
    override fun make(activity: VCActivity): View {
        return LinearLayout(activity).apply {
            setGravity(Gravity.CENTER)
            orientation = LinearLayout.VERTICAL

            textView {
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

            button() {
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