package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlincomponents.adapter.adapter
import com.lightningkite.kotlincomponents.observable.bind
import com.lightningkite.kotlincomponents.selectableItemBackgroundResource
import com.lightningkite.kotlincomponents.ui.verticalRecyclerView
import com.lightningkite.kotlincomponents.viewcontroller.AnkoViewController
import com.lightningkite.kotlincomponents.viewcontroller.ViewController
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import org.jetbrains.anko.*
import java.util.*

/**
 * Created by jivie on 5/5/16.
 */
class SelectorVC(val main: MainVC) : AnkoViewController() {

    override fun getTitle(resources: Resources): String {
        return resources.getString(R.string.main_menu)
    }

    val demos: ArrayList<Pair<String, () -> ViewController>> = arrayListOf(
            "Lists and Adapters" to { ListTestVC(main.stack) },
            "Networking and more Demo" to { NetTestVC(main, main.stack) }
    )

    override fun createView(ui: AnkoContext<VCActivity>): View = ui.verticalLayout {

        textView(R.string.welcome_message) {
            minimumHeight = dip(48)
            padding = dip(16)
            gravity = Gravity.CENTER
        }

        verticalRecyclerView() {
            adapter(demos) { itemObs ->
                textView() {
                    minimumHeight = dip(48)
                    padding = dip(16)
                    backgroundResource = selectableItemBackgroundResource
                    bind(itemObs) {
                        text = it.first
                    }
                    onClick {
                        main.stack.push(itemObs.get().second())
                    }
                }
            }
        }
    }
}