package com.ivieleague.kotlin_components_starter

import android.view.View
import android.view.ViewGroup
import com.lightningkite.kotlincomponents.ui.setUpWithVCTabs
import com.lightningkite.kotlincomponents.viewcontroller.MainViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCTabs
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

/**
 * Created by jivie on 2/11/16.
 */
class MainVC(val stack: VCStack) : MainViewController(R.drawable.abc_ic_ab_back_mtrl_am_alpha, {}) {

    val tabs = VCTabs(0, NetTestVC(this, stack), ListTestVC(stack), SocketIOVC(stack))

    override fun ViewGroup.makeSubview(activity: VCActivity): View {

        attach(activity, tabs)

        return verticalLayout {
            tabLayout {
                setUpWithVCTabs(tabs, {}, {})
            }.lparams(matchParent, wrapContent)

            viewContainer(tabs).lparams(matchParent, 0, 1f)
        }
    }

}