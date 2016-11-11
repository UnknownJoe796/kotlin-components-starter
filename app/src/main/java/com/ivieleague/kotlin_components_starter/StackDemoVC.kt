package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.containers.VCStack
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.VCActivity
import com.lightningkite.kotlin_components_starter.styleDefault
import org.jetbrains.anko.*

/**
 * Created as a dummy VC to test out the stack.
 * Created by josep on 11/6/2015.
 */
class StackDemoVC(val stack: VCStack, val depth: Int = 1) : AnkoViewController() {

    override fun getTitle(resources: Resources): String {
        return "Stack Demo ($depth)"
    }

    override fun createView(ui: AnkoContext<VCActivity>): View = ui.verticalLayout {
        gravity = Gravity.CENTER

        textView("This view controller has a depth of $depth.") {
            styleDefault()
        }

        button("Go deeper") {
            styleDefault()
            onClick {
                stack.push(StackDemoVC(stack, depth + 1))
            }
        }

        button("Go back") {
            styleDefault()
            onClick {
                stack.pop()
            }
        }
    }
}