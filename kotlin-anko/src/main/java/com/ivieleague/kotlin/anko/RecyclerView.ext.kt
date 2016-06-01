package com.ivieleague.kotlin.anko

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewManager
import com.lightningkite.kotlincomponents.R
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.layoutInflater

/**
 * Created by joseph on 3/3/2016.
 */

inline fun ViewManager.verticalRecyclerView() = verticalRecyclerView {}

inline fun ViewManager.verticalRecyclerView(init: RecyclerView.() -> Unit) = ankoView({
    val view = it.layoutInflater.inflate(R.layout.vertical_recycler_view, null) as RecyclerView
    view.apply {
        layoutManager = LinearLayoutManager(it).apply {
            this.orientation = LinearLayoutManager.VERTICAL
        }
    }
}, init)

inline fun ViewManager.horizontalRecyclerView() = horizontalRecyclerView() {}
inline fun ViewManager.horizontalRecyclerView(init: RecyclerView.() -> Unit) = ankoView({
    val view = it.layoutInflater.inflate(R.layout.horizontal_recycler_view, null) as RecyclerView
    view.apply {
        layoutManager = LinearLayoutManager(it).apply {
            this.orientation = LinearLayoutManager.HORIZONTAL
        }
    }
}, init)

inline fun ViewManager.verticalGridRecyclerView(spanCount: Int) = verticalGridRecyclerView(spanCount) {}
inline fun ViewManager.verticalGridRecyclerView(spanCount: Int, init: RecyclerView.() -> Unit) = ankoView({
    val view = it.layoutInflater.inflate(R.layout.vertical_recycler_view, null) as RecyclerView
    view.apply {
        layoutManager = GridLayoutManager(it, spanCount).apply {
            this.orientation = GridLayoutManager.VERTICAL
        }
    }
}, init)

inline fun ViewManager.horizontalGridRecyclerView(spanCount: Int) = horizontalGridRecyclerView(spanCount) {}
inline fun ViewManager.horizontalGridRecyclerView(spanCount: Int, init: RecyclerView.() -> Unit) = ankoView({
    val view = it.layoutInflater.inflate(R.layout.horizontal_recycler_view, null) as RecyclerView
    view.apply {
        layoutManager = GridLayoutManager(it, spanCount).apply {
            this.orientation = GridLayoutManager.HORIZONTAL
        }
    }
}, init)

inline fun RecyclerView.horizontalDivider(drawable: Drawable) {
    addItemDecoration(object : RecyclerView.ItemDecoration() {

        val dividerSize = drawable.intrinsicHeight.coerceAtLeast(1)

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val left = parent.paddingLeft;
            val right = parent.width - parent.paddingRight;

            val childCount = parent.childCount;
            for (i in 0..childCount - 1) {
                val child = parent.getChildAt(i);

                drawable.alpha = (child.alpha * 255).toInt()

                val params = child.layoutParams as RecyclerView.LayoutParams;

                val top = child.visualTop - dividerSize;

                drawable.setBounds(left, top, right, top + dividerSize);
                drawable.draw(c);

                val bottom = child.visualBottom;

                drawable.setBounds(left, bottom, right, bottom + dividerSize);
                drawable.draw(c);
            }
        }

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.set(0, 0, 0, dividerSize);
        }
    })
}