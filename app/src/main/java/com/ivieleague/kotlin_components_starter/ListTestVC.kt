package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import com.ivieleague.kotlin.anko.adapter.swipeToDismiss
import com.ivieleague.kotlin.anko.horizontalDivider
import com.ivieleague.kotlin.anko.observable.adapter.standardAdapter
import com.ivieleague.kotlin.anko.observable.bindString
import com.ivieleague.kotlin.anko.selectableItemBackgroundResource
import com.ivieleague.kotlin.anko.stickyHeaders
import com.ivieleague.kotlin.anko.verticalRecyclerView
import com.ivieleague.kotlin.anko.viewcontrollers.AnkoViewController
import com.ivieleague.kotlin.anko.viewcontrollers.containers.VCStack
import com.ivieleague.kotlin.anko.viewcontrollers.implementations.VCActivity
import com.ivieleague.kotlin.observable.list.ObservableListWrapper
import com.ivieleague.kotlin.text.toFloatMaybe
import org.jetbrains.anko.*

/**
 * Created by jivie on 2/10/16.
 */
class ListTestVC(val stack: VCStack) : AnkoViewController() {

    val items = ObservableListWrapper(arrayListOf<String>("A", "B", "C"))

    override fun getTitle(resources: Resources): String = "List Test"

    override fun createView(ui: AnkoContext<VCActivity>): View = ui.verticalLayout {
        gravity = Gravity.CENTER

        verticalRecyclerView() {

            val adap = standardAdapter(items) { obs ->
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