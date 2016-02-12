package com.ivieleague.kotlin_components_starter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.lightningkite.kotlincomponents.adapter.ActionItemTouchHelperListener
import com.lightningkite.kotlincomponents.adapter.horizontalDivider
import com.lightningkite.kotlincomponents.adapter.makeAdapter
import com.lightningkite.kotlincomponents.adapter.swipe
import com.lightningkite.kotlincomponents.observable.KObservableList
import com.lightningkite.kotlincomponents.observable.bindString
import com.lightningkite.kotlincomponents.selectableItemBackgroundResource
import com.lightningkite.kotlincomponents.ui.verticalRecyclerView
import com.lightningkite.kotlincomponents.viewcontroller.StandardViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import com.lightningkite.kotlincomponents.viewcontroller.verticalLayout
import org.jetbrains.anko.*

/**
 * Created by jivie on 2/10/16.
 */
class ListTestVC(val stack: VCStack) : StandardViewController() {

    val items = KObservableList(arrayListOf<String>())

    override fun makeView(activity: VCActivity): View = verticalLayout(activity) {

        gravity = Gravity.CENTER

        items.add("First")
        items.add("Second")
        items.add("Third")
        items.add("Fourth")
        items.add("Fifth")

        textView("List Test") {
            gravity = Gravity.CENTER
            textSize = 18f
            textColor = Color.BLACK
            minimumHeight = dip(40)
            backgroundColor = resources.getColor(R.color.colorPrimary)
        }.lparams(matchParent, wrapContent)

        verticalRecyclerView() {

            makeAdapter(items) {
                TextView(activity).apply {
                    bindString(it)
                    gravity = Gravity.CENTER
                    textSize = 18f
                    textColor = Color.BLACK
                    minimumHeight = dip(40)
                    backgroundResource = selectableItemBackgroundResource
                }.lparams(matchParent, wrapContent)
            }

            swipe(
                    ActionItemTouchHelperListener.SwipeAction(
                            Color.RED,
                            resources.getDrawable(android.R.drawable.ic_menu_delete),
                            { true },
                            { items.removeAt(it) }
                    ),
                    ActionItemTouchHelperListener.SwipeAction(
                            Color.RED,
                            resources.getDrawable(android.R.drawable.ic_menu_delete),
                            { true },
                            { items.removeAt(it) }
                    ),
                    dip(4)
            )

            horizontalDivider(ColorDrawable(Color.LTGRAY))

        }.lparams(matchParent, 0, 1f)

        button("Add") {
            onClick {
                items.add(Math.random().toString())
            }
        }.lparams(matchParent, wrapContent)

    }
}