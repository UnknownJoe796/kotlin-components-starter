package com.ivieleague.kotlin_components_starter

import android.view.Gravity
import android.view.View
import com.lightningkite.kotlincomponents.vertical
import com.lightningkite.kotlincomponents.viewcontroller.AutocleanViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import com.lightningkite.kotlincomponents.viewcontroller.linearLayout
import org.jetbrains.anko.button
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textView

/**
 * Created as a dummy VC to test out the stack.
 * Created by josep on 11/6/2015.
 */
class AnotherVC(val stack: VCStack) : AutocleanViewController() {
    override fun make(activity: VCActivity): View {
        return linearLayout(activity) {
            setGravity(Gravity.CENTER)
            orientation = vertical

            textView("Something else")

            button("Go back") {
                onClick {
                    stack.pop()
                }
            }
        }
    }
}