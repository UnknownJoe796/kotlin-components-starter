package com.ivieleague.kotlin_components_starter

import android.graphics.Color
import android.view.View
import com.lightningkite.kotlin.anko.activity.ActivityAccess
import com.lightningkite.kotlin.anko.activity.ViewGenerator
import com.lightningkite.kotlin.anko.activity.anko
import com.lightningkite.kotlin.anko.async.UIThread
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.observable.adapter.listAdapter
import com.lightningkite.kotlin.anko.observable.progressLayout
import com.lightningkite.kotlin.anko.verticalRecyclerView
import com.lightningkite.kotlin.async.Async
import com.lightningkite.kotlin.async.invokeOn
import com.lightningkite.kotlin.async.thenOn
import com.lightningkite.kotlin.observable.list.observableListOf
import com.lightningkite.kotlin.observable.property.bind
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * Demonstrates loading and displaying a list from the network.
 * Created by josep on 11/10/2016.
 */
class NetworkListVC : ViewGenerator {

    override fun toString(): String {
        return "Network List Example"
    }

    val posts = observableListOf<Post>()

    init {
        ExampleAPI.getPosts().thenOn(UIThread) {
            if (it.isSuccessful()) {
                posts.replace(it.result!!)
            } else {
                posts.add(Post(title = "Loading error", body = it.errorString ?: "Unknown error"))
            }
        }.invokeOn(Async)
    }

    override fun invoke(access: ActivityAccess): View = access.anko {
        verticalLayout {

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
}