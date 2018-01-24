package com.ivieleague.kotlin_components_starter

import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.activity.ActivityAccess
import com.lightningkite.kotlin.anko.activity.ViewGenerator
import com.lightningkite.kotlin.anko.activity.anko
import com.lightningkite.kotlin.anko.animation.swapView
import com.lightningkite.kotlin.anko.elevationCompat
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.observable.bind
import com.lightningkite.kotlin.lifecycle.listen
import com.lightningkite.kotlin.observable.property.StackObservableProperty
import com.lightningkite.kotlin.observable.property.bind
import org.jetbrains.anko.appcompat.v7.actionMenuView
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

/**
 * A main view controller.
 *
 * Controls an appcompat toolbar, and you need to attach it to a stack.
 *
 * This methodology is up for refactoring, so this may not be permanent.
 *
 * Created by jivie on 2/11/16.
 */
class MainScreen() : ViewGenerator {
    val stack = StackObservableProperty<ViewGenerator>().apply {
        push(SelectorScreen(this@MainScreen))
    }

    override fun invoke(access: ActivityAccess): View = access.anko {
        verticalLayout {

            lifecycle.listen(access.onBackPressed) { stack.popOrFalse() }

            toolbar {
                elevationCompat = dip(4).toFloat()
                actionMenuView {
                }.lparams(Gravity.RIGHT)

                lifecycle.bind(stack) {
                    this.title = it.toString()
                    setNavigationOnClickListener { stack.pop() }
                    if (stack.stack.size > 1) {
                        setNavigationIcon(R.drawable.ic_back)
                    } else {
                        navigationIcon = null
                    }
                }

            }.lparams(matchParent, wrapContent)

            coordinatorLayout {
                swapView { bind(access, stack) }.lparams(matchParent, matchParent)
            }.lparams(matchParent, 0, 1f)
        }
    }
}