package com.ivieleague.kotlin_components_starter

import android.view.Gravity
import android.view.View
import com.lightningkite.kotlincomponents.viewcontroller.AutocleanViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.gravity
import org.jetbrains.anko.textView

/**
 * Created by josep on 11/6/2015.
 */
class StartVC(val stack: VCStack) : AutocleanViewController() {
    override fun make(activity: VCActivity): View {

        return _LinearLayout(activity).apply {
            gravity = Gravity.CENTER
            textView("Hello World")
        }
    }
}