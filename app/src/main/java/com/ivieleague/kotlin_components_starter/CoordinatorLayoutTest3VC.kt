package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.observable.adapter.listAdapter
import com.lightningkite.kotlin.anko.observable.bindAny
import com.lightningkite.kotlin.anko.observable.collapsedRatio
import com.lightningkite.kotlin.anko.selectableItemBackgroundResource
import com.lightningkite.kotlin.anko.verticalRecyclerView
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.containers.VCStack
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.VCActivity
import com.lightningkite.kotlin.observable.list.ObservableListWrapper
import com.lightningkite.kotlin.observable.property.bind
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout

/**
 * Created by joseph on 11/7/16.
 */
class CoordinatorLayoutTest3VC(val main: MainVC, val stack: VCStack) : AnkoViewController() {

    override fun getTitle(resources: Resources): String {
        return "Coordinator Layout Test 3"
    }

    val junk = ObservableListWrapper((0..100).map { Math.random() }.toMutableList())

    override fun createView(ui: AnkoContext<VCActivity>): View = ui.coordinatorLayout {
        appBarLayout {
            collapsingToolbarLayout {

                imageView(R.mipmap.test_image) {
                    setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)
                    scaleType = ImageView.ScaleType.CENTER_CROP

                    layoutParams = CollapsingToolbarLayout.LayoutParams(matchParent, wrapContent).apply {
                        collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                    }
                }

                textView("Pinned Title") {
                    gravity = Gravity.CENTER
                    textSize = 18f
                    textColor = Color.WHITE

                    lifecycle.bind(this@appBarLayout.collapsedRatio) {
                        println(it)
                        textSize = 28f - 8f * it
                    }

                    layoutParams = CollapsingToolbarLayout.LayoutParams(matchParent, dip(40)).apply {
                        collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                    }
                }

                minimumHeight = dip(40)

            }.lparams(matchParent, wrapContent) { scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED }

            textView("Constant Bar (to be a tab bar)") {
                padding = dip(8)
                textSize = 18f
                textColor = Color.WHITE
                backgroundColor = Color.GRAY
            }.lparams(matchParent, wrapContent) {
                scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
            }
        }.lparams(matchParent, wrapContent)

        verticalRecyclerView() {
            adapter = listAdapter(junk) { itemObs ->
                textView() {
                    backgroundResource = selectableItemBackgroundResource
                    padding = dip(8)
                    gravity = Gravity.CENTER
                    textColor = Color.WHITE
                    textSize = 18f
                    bindAny(itemObs)
                }.lparams(matchParent, wrapContent)
            }
        }.lparams(matchParent, matchParent) {
            behavior = AppBarLayout.ScrollingViewBehavior()
        }
    }
}