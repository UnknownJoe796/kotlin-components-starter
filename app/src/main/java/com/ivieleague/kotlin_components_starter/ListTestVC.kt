package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlincomponents.adapter.adapter
import com.lightningkite.kotlincomponents.adapter.swipeToDismiss
import com.lightningkite.kotlincomponents.observable.KObservableList
import com.lightningkite.kotlincomponents.observable.bindString
import com.lightningkite.kotlincomponents.selectableItemBackgroundResource
import com.lightningkite.kotlincomponents.toFloatMaybe
import com.lightningkite.kotlincomponents.ui.horizontalDivider
import com.lightningkite.kotlincomponents.ui.stickyHeaders
import com.lightningkite.kotlincomponents.ui.verticalRecyclerView
import com.lightningkite.kotlincomponents.viewcontroller.AnkoViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import org.jetbrains.anko.*

/**
 * Created by jivie on 2/10/16.
 */
class ListTestVC(val stack: VCStack) : AnkoViewController() {

    val items = KObservableList(arrayListOf<String>("A", "B", "C"))

    override fun getTitle(resources: Resources): String = "List Test"

    override fun createView(ui: AnkoContext<VCActivity>): View = ui.verticalLayout {
        gravity = Gravity.CENTER

        verticalRecyclerView() {

            val adap = adapter(items) { obs ->
                textView() {
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

            stickyHeaders(adap.list, {
                when (it.toFloatMaybe()) {
                    null -> "Not a Number"
                    in 0f..(.5f) -> "Low"
                    else -> "High"
                }
            }, {
                textView {
                    text = it
                    backgroundColor = resources.getColor(R.color.colorPrimary)
                    padding = dip(8)
                }
            })

            swipeToDismiss {
                items.removeAt(it)
            }

            horizontalDivider(ColorDrawable(Color.LTGRAY))

        }.lparams(matchParent, 0, 1f)

        button("Add") {
            onClick {
                items.add(Math.random().toString())
            }
        }.lparams(matchParent, wrapContent)

    }
}