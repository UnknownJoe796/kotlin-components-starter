package com.ivieleague.kotlin_components_starter

import android.view.View
import com.lightningkite.kotlincomponents.viewcontroller.AutocleanViewController
import com.lightningkite.kotlincomponents.viewcontroller.ViewController
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.textView

/**
 * Created by josep on 11/6/2015.
 */
class StartVC() : AutocleanViewController() {
    override fun make(activity: VCActivity): View {
        return _LinearLayout(activity).apply {
            textView("Hello World")
        }
    }
}