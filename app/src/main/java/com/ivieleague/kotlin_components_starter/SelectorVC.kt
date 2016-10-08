package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.observable.adapter.listAdapter
import com.lightningkite.kotlin.anko.selectableItemBackgroundResource
import com.lightningkite.kotlin.anko.verticalRecyclerView
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.ViewController
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.VCActivity
import com.lightningkite.kotlin.observable.property.bind
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
            adapter = listAdapter(demos) { itemObs ->
                textView() {
                    minimumHeight = dip(48)
                    padding = dip(16)
                    backgroundResource = selectableItemBackgroundResource
                    lifecycle.bind(itemObs) {
                        text = it.first
                    }
                    onClick {
                        main.stack.push(itemObs.value.second())
                    }
                }.lparams(matchParent, wrapContent)
            }
        }
    }
}