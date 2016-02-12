package com.ivieleague.kotlin_components_starter

import android.graphics.Color
import android.view.View
import com.lightningkite.kotlincomponents.ui.setUpWithVCTabs
import com.lightningkite.kotlincomponents.ui.tabLayout
import com.lightningkite.kotlincomponents.viewcontroller.StandardViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCTabs
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import com.lightningkite.kotlincomponents.viewcontroller.verticalLayout
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.wrapContent

/**
 * Created by jivie on 2/11/16.
 */
class MainVC(val stack: VCStack) : StandardViewController() {

    val tabs = VCTabs(0, NetTestVC(stack), ListTestVC(stack), SocketIOVC(stack))

    override fun makeView(activity: VCActivity): View = verticalLayout(activity) {
        tabLayout {
            backgroundColor = Color.BLACK
            setTabTextColors(Color.BLUE, Color.LTGRAY)
            setUpWithVCTabs(tabs, {}, {})
        }.lparams(matchParent, wrapContent)
        viewContainer(tabs).lparams(matchParent, 0, 1f)
    }

}