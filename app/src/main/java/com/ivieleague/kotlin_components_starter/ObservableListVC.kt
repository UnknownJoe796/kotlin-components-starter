package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.*
import com.lightningkite.kotlin.anko.adapter.swipeToDismiss
import com.lightningkite.kotlin.anko.observable.adapter.listAdapter
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.observable.list.ObservableListWrapper
import com.lightningkite.kotlin.observable.property.bind
import com.lightningkite.kotlin.text.toFloatMaybe
import org.jetbrains.anko.*

/**
 * Created by jivie on 2/10/16.
 */
class ObservableListVC() : AnkoViewController() {

    val items = ObservableListWrapper(arrayListOf<String>("A", "B", "C"))

    override fun getTitle(resources: Resources): String = "List Test"

    override fun createView(ui: AnkoContext<VCContext>): View = ui.verticalLayout {
        gravity = Gravity.CENTER

        verticalRecyclerView {

            adapter = listAdapter(items) { obs ->
                textView {
                    lifecycle.bind(obs) {
                        text = it + " (position: ${obs.position})"
                    }
                    gravity = Gravity.CENTER
                    textSize = 18f
                    minimumHeight = dip(40)
                    backgroundResource = selectableItemBackgroundResource
                    setOnLongClickListener {
                        items.removeAt(obs.position)
                        true
                    }
                }.lparams(matchParent, wrapContent)
            }

            stickyHeaders(items, {
                when (it.toFloatMaybe()) {
                    null -> "Not a Number"
                    in 0..5 -> "Low"
                    else -> "High"
                }
            }, {
                textView {
                    text = it
                    backgroundColor = resources.getColorCompat(R.color.colorPrimary)
                    padding = dip(8)
                }
            })

            swipeToDismiss {
                items.removeAt(it)
            }

            horizontalDivider(ColorDrawable(Color.LTGRAY))

        }.lparams(matchParent, 0, 1f)

        button("Add") {
            setOnClickListener { it: View? ->
                items.add(Math.random().times(10).plus(1).toInt().toString())
            }
        }.lparams(matchParent, wrapContent)

    }
}