package com.ivieleague.kotlin_components_starter

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.lightningkite.kotlincomponents.adapter.lightningAdapter
import com.lightningkite.kotlincomponents.observable.KObservable
import com.lightningkite.kotlincomponents.observable.bindString
import com.lightningkite.kotlincomponents.selectableItemBackgroundResource
import com.lightningkite.kotlincomponents.ui.swipeToDismiss
import com.lightningkite.kotlincomponents.viewcontroller.StandardViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import com.lightningkite.kotlincomponents.viewcontroller.verticalLayout
import org.jetbrains.anko.*

/**
 * Created by jivie on 2/10/16.
 */
class ListTestVC(val stack: VCStack) : StandardViewController() {

    val itemsObs = KObservable(arrayListOf<String>())
    var items by itemsObs

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

        listView() {

            val myAdapter = lightningAdapter(itemsObs) {
                TextView(activity).apply {
                    bindString(it)
                    gravity = Gravity.CENTER
                    textSize = 18f
                    textColor = Color.BLACK
                    minimumHeight = dip(40)
                    backgroundResource = selectableItemBackgroundResource
                }
            }

            adapter = myAdapter

            swipeToDismiss({ true }, { items.removeAt(it); itemsObs.update() }, { myAdapter.notifyDataSetChanged() })

        }.lparams(matchParent, 0, 1f)
    }
}