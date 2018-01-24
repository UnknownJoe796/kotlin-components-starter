package com.ivieleague.kotlin_components_starter

import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.activity.ActivityAccess
import com.lightningkite.kotlin.anko.activity.ViewGenerator
import com.lightningkite.kotlin.anko.activity.anko
import com.lightningkite.kotlin.observable.property.StackObservableProperty
import org.jetbrains.anko.button
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

/**
 * Created as a dummy VC to test out the stack.
 * Created by josep on 11/6/2015.
 */
class StackDemoVC(val stack: StackObservableProperty<ViewGenerator>, val depth: Int = 1) : ViewGenerator {

    override fun toString(): String = "Stack Demo ($depth)"

    override fun invoke(access: ActivityAccess): View = access.anko {
        verticalLayout {
            gravity = Gravity.CENTER

            textView("This view controller has a depth of $depth.") {
                styleDefault()
            }

            button("Go deeper") {
                styleDefault()
                setOnClickListener { it: View? ->
                    stack.push(StackDemoVC(stack, depth + 1))
                }
            }

            button("Go back") {
                styleDefault()
                setOnClickListener { it: View? ->
                    stack.pop()
                }
            }
        }
    }
}