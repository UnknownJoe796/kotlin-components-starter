package com.ivieleague.kotlin_components_starter

import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.elevationCompat
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.anko.viewcontrollers.containers.VCStack
import com.lightningkite.kotlin.lifecycle.bind
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.actionMenuView
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.coordinatorLayout

/**
 * A main view controller.
 *
 * Controls an appcompat toolbar, and you need to attach it to a stack.
 *
 * This methodology is up for refactoring, so this may not be permanent.
 *
 * Created by jivie on 2/11/16.
 */
class MainVC() : AnkoViewController() {
    val stack = autoDispose(VCStack().apply {
        push(SelectorVC(this@MainVC))
    })

    override fun createView(ui: AnkoContext<VCContext>): View = ui.verticalLayout {
        toolbar {
            elevationCompat = dip(4).toFloat()
            actionMenuView {
            }.lparams(Gravity.RIGHT)

            lifecycle.bind(stack.onSwap, stack.current) {
                this.title = it.getTitle(resources)
                setNavigationOnClickListener { stack.pop() }
                if (stack.size > 1) {
                    setNavigationIcon(R.drawable.ic_back)
                } else {
                    navigationIcon = null
                }
            }

        }.lparams(matchParent, wrapContent)

        coordinatorLayout {
            viewContainer(ui.owner, stack).lparams(matchParent, matchParent)
        }.lparams(matchParent, 0, 1f)
    }

    override fun onBackPressed(backAction: () -> Unit) {
        stack.onBackPressed(backAction)
    }
}