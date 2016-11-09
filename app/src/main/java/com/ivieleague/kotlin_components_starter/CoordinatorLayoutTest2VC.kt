package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.lightningkite.kotlin.anko.observable.adapter.listAdapter
import com.lightningkite.kotlin.anko.observable.bindAny
import com.lightningkite.kotlin.anko.selectableItemBackgroundResource
import com.lightningkite.kotlin.anko.verticalRecyclerView
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.containers.VCStack
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.VCActivity
import com.lightningkite.kotlin.observable.list.ObservableListWrapper
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout

/**
 * Created by joseph on 11/7/16.
 */
class CoordinatorLayoutTest2VC(val main: MainVC, val stack: VCStack) : AnkoViewController() {

    override fun getTitle(resources: Resources): String {
        return "Coordinator Layout Test 2"
    }

    val TITLE_ID = 18923

    val junk = ObservableListWrapper((0..100).map { Math.random() }.toMutableList())

    override fun createView(ui: AnkoContext<VCActivity>): View = ui.coordinatorLayout {
        appBarLayout {
            collapsingToolbarLayout {

                verticalLayout {
                    backgroundColor = Color.parseColor("#8080C0")

                    imageView(R.mipmap.test_image) {
                        setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)
                        scaleType = ImageView.ScaleType.CENTER_CROP

                    }.lparams(matchParent, dip(100))

                    textView("Title (Big)") {
                        id = TITLE_ID
                        padding = dip(8)
                        textSize = 18f
                        textColor = Color.WHITE
                    }.lparams(matchParent, wrapContent)

                    textView("This is a lot of detail about this thing that is above me and may or may not be magical.") {
                        padding = dip(8)
                        textSize = 14f
                        textColor = Color.WHITE
                    }.lparams(matchParent, wrapContent)

                    layoutParams = CollapsingToolbarLayout.LayoutParams(matchParent, wrapContent).apply {
                        collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                        parallaxMultiplier = .1f
                    }
                }

            }.lparams { scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED }

            textView("Constant Bar (to be a tab bar)") {
                padding = dip(8)
                textSize = 18f
                textColor = Color.WHITE
                backgroundColor = Color.GRAY
            }.lparams(matchParent, wrapContent) {
                scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                topMargin = dip(32)
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


        verticalLayout {
            toolbar {
                title = "Title(small)"
            }.lparams(matchParent, wrapContent)
        }.lparams(matchParent, wrapContent) {
            anchorId = TITLE_ID
        }
    }
}