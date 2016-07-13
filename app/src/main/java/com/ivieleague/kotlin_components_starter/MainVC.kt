package com.ivieleague.kotlin_components_starter

import android.view.View
import android.view.ViewGroup
import com.ivieleague.kotlin.anko.viewcontrollers.MainViewController
import com.ivieleague.kotlin.anko.viewcontrollers.containers.VCStack
import com.ivieleague.kotlin.anko.viewcontrollers.implementations.VCActivity

/**
 * A main view controller.
 *
 * Controls an appcompat toolbar, and you need to attach it to a stack.
 *
 * This methodology is up for refactoring, so this may not be permanent.
 *
 * Created by jivie on 2/11/16.
 */
class MainVC() : MainViewController(R.drawable.abc_ic_ab_back_mtrl_am_alpha, {}) {

    val stack = VCStack().apply {
        push(SelectorVC(this@MainVC))
    }

    override fun ViewGroup.makeSubview(activity: VCActivity): View {

        attach(activity, stack)
        return viewContainer(stack)
    }

}