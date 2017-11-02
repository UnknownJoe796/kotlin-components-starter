package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.graphics.Color
import android.view.View
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.observable.adapter.listAdapter
import com.lightningkite.kotlin.anko.observable.progressLayout
import com.lightningkite.kotlin.anko.verticalRecyclerView
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.async.invokeAsync
import com.lightningkite.kotlin.observable.list.observableListOf
import com.lightningkite.kotlin.observable.property.bind
import com.lightningkite.kotlin_components_starter.styleDefault
import com.lightningkite.kotlin_components_starter.styleInverted
import com.lightningkite.kotlin_components_starter.styleInvertedTitle
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * Created by josep on 11/10/2016.
 */
class NetworkListVC : AnkoViewController() {

    override fun getTitle(resources: Resources): String {
        return "Network List Example"
    }

    val posts = observableListOf<Post>()

    init {
        ExampleAPI.getPosts().invokeAsync {
            if (it.isSuccessful()) {
                posts.replace(it.result!!)
            } else {
                posts.add(Post(title = "Loading error", body = it.errorString ?: "Unknown error"))
            }
        }
    }

    override fun createView(ui: AnkoContext<VCContext>): View = ui.verticalLayout {

        textView("This data is from https://jsonplaceholder.typicode.com/.") {
            styleDefault()
        }.lparams(matchParent, wrapContent) { margin = dip(8) }

        progressLayout { runningObs ->

            lifecycle.bind(posts.onUpdate) { posts ->
                runningObs.value = posts.isEmpty()
            }

            verticalRecyclerView {
                adapter = listAdapter(posts) { itemObs ->
                    cardView {
                        backgroundColor = Color.WHITE

                        verticalLayout {
                            padding = dip(8)

                            textView {
                                styleInvertedTitle()
                                lifecycle.bind(itemObs) {
                                    text = it.title
                                }
                            }.lparams(matchParent, wrapContent) { margin = dip(8) }

                            textView {
                                styleInverted()
                                lifecycle.bind(itemObs) {
                                    text = it.body
                                }
                            }.lparams(matchParent, wrapContent) { margin = dip(8) }

                        }
                    }.lparams(matchParent, wrapContent) { margin = dip(8) }
                }
            }
        }.lparams(matchParent, 0, 1f)
    }
}