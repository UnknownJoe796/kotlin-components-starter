package com.ivieleague.kotlin_components_starter

import android.view.View
import android.view.ViewGroup
import com.ivieleague.kotlin.anko.viewcontrollers.MainViewController
import com.ivieleague.kotlin.anko.viewcontrollers.containers.VCStack
import com.ivieleague.kotlin.anko.viewcontrollers.implementations.VCActivity

/**
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