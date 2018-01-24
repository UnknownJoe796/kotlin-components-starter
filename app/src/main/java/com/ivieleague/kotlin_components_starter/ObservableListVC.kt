package com.ivieleague.kotlin_components_starter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.*
import com.lightningkite.kotlin.anko.activity.ActivityAccess
import com.lightningkite.kotlin.anko.activity.ViewGenerator
import com.lightningkite.kotlin.anko.activity.anko
import com.lightningkite.kotlin.anko.adapter.swipeToDismiss
import com.lightningkite.kotlin.anko.observable.adapter.listAdapter
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.observable.list.ObservableListWrapper
import com.lightningkite.kotlin.observable.property.bind
import org.jetbrains.anko.*

/**
 * A [AnkoViewController] used for demonstrating observable lists.
 * Created by jivie on 2/10/16.
 */
class ObservableListVC() : ViewGenerator {

    val items = ObservableListWrapper(arrayListOf<String>("A", "B", "C"))

    override fun toString(): String = "List Test"

    override fun invoke(access: ActivityAccess): View = access.anko {
        verticalLayout {
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
                    when (it.toFloatOrNull()) {
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
}