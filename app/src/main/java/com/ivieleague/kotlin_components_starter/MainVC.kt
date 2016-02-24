package com.ivieleague.kotlin_components_starter

import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlincomponents.ui.*
import com.lightningkite.kotlincomponents.viewcontroller.StandardViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCTabs
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import com.lightningkite.kotlincomponents.viewcontroller.verticalLayout
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.wrapContent

/**
 * Created by jivie on 2/11/16.
 */
class MainVC(val stack: VCStack) : StandardViewController() {

    val tabs = VCTabs(0, NetTestVC(stack), ListTestVC(stack), SocketIOVC(stack))

    lateinit var toolbar: Toolbar

    override fun makeView(activity: VCActivity): View = verticalLayout(activity) {

        toolbar = toolBar {
            backgroundResource = R.color.colorPrimary
            elevationCompat = dip(4).toFloat()
            title = "Test Title"
            subtitle = "Test Subtitle"
            setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha)
            setNavigationOnClickListener { activity.onBackPressed() }
            actionMenuView().lparams(Gravity.END or Gravity.CENTER_VERTICAL)
        }

        tabLayout {
            setUpWithVCTabs(tabs, {}, {})
        }.lparams(matchParent, wrapContent)

        viewContainer(tabs).lparams(matchParent, 0, 1f)
    }

}