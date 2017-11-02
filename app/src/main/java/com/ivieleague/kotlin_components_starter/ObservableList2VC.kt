package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.adapter.swipeToDismiss
import com.lightningkite.kotlin.anko.horizontalDivider
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.observable.adapter.listAdapter
import com.lightningkite.kotlin.anko.selectableItemBackgroundResource
import com.lightningkite.kotlin.anko.verticalRecyclerView
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.observable.list.ObservableListWrapper
import com.lightningkite.kotlin.observable.list.sorting
import com.lightningkite.kotlin.observable.property.bind
import com.lightningkite.kotlin.range.random
import org.jetbrains.anko.*

/**
 * Created by jivie on 2/10/16.
 */
class ObservableList2VC() : AnkoViewController() {

    val items = ObservableListWrapper((190 downTo 1).map { it.toString() }.toMutableList())
    val sorted = items.sorting { a, b -> a < b }

    override fun getTitle(resources: Resources): String = "List Test"

    override fun createView(ui: AnkoContext<VCContext>): View = ui.verticalLayout {
        gravity = Gravity.CENTER

        verticalRecyclerView {

            adapter = listAdapter(sorted) { obs ->
                textView {
                    lifecycle.bind(obs) {
                        text = it
                    }
//                    newLifecycle.bind(obs){
//                        text = it
//                    }
//                    obs += { text = it }
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

            swipeToDismiss {
                items.removeAt(it)
            }

            horizontalDivider(ColorDrawable(Color.LTGRAY))

        }.lparams(matchParent, 0, 1f)

        button("Add") {
            setOnClickListener { it: View? ->
                items.add(Math.random().times(190).plus(1).toInt().toString() + " N")
            }
        }.lparams(matchParent, wrapContent)

        button("Update Random") {
            setOnClickListener { it: View? ->
                val index = items.indices.random()
                items[index] = items[index] + " M"
            }
        }.lparams(matchParent, wrapContent)

    }
}