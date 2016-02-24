package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.lightningkite.kotlincomponents.adapter.ActionItemTouchHelperListener
import com.lightningkite.kotlincomponents.adapter.makeAdapter
import com.lightningkite.kotlincomponents.adapter.swipe
import com.lightningkite.kotlincomponents.observable.KObservableList
import com.lightningkite.kotlincomponents.observable.bindString
import com.lightningkite.kotlincomponents.selectableItemBackgroundResource
import com.lightningkite.kotlincomponents.toFloatMaybe
import com.lightningkite.kotlincomponents.ui.horizontalDivider
import com.lightningkite.kotlincomponents.ui.snackbar
import com.lightningkite.kotlincomponents.ui.stickyHeaders
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

    val items = KObservableList(arrayListOf("first", "second", "third", "fourth", "fifth"))

    override fun getTitle(resources: Resources): String = "List Test"

    override fun makeView(activity: VCActivity): View = verticalLayout(activity) {

        gravity = Gravity.CENTER

        verticalRecyclerView() {

            val adap = makeAdapter(items) { obs ->
                TextView(activity).apply {
                    bindString(obs)
                    gravity = Gravity.CENTER
                    textSize = 18f
                    minimumHeight = dip(40)
                    backgroundResource = selectableItemBackgroundResource
                    onLongClick {
                        items.removeAt(obs.position)
                        true
                    }
                }.lparams(matchParent, wrapContent)
            }

            stickyHeaders(adap, {
                when(it.toFloatMaybe()){
                    null -> "Not a Number"
                    in 0f .. .5f -> "Low"
                    else -> "High"
                }
            }, {
                TextView(context).apply{
                    text = it
                    backgroundColor = resources.getColor(R.color.colorPrimary)
                    padding = dip(8)
                }
            })

            swipe(
                    ActionItemTouchHelperListener.SwipeAction(
                            Color.RED,
                            resources.getDrawable(android.R.drawable.ic_menu_delete),
                            { true },
                            { index ->
                                val item = items.removeAt(index)
                                snackbar("Item deleted.") {
                                    setAction("Undo") {
                                        items.add(index, item)
                                    }
                                }
                            }
                    ),
                    ActionItemTouchHelperListener.SwipeAction(
                            Color.RED,
                            resources.getDrawable(android.R.drawable.ic_menu_delete),
                            { true },
                            { index ->
                                val item = items.removeAt(index)
                                snackbar("Item deleted.") {
                                    setAction("Undo") {
                                        items.add(index, item)
                                    }
                                }
                            }
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