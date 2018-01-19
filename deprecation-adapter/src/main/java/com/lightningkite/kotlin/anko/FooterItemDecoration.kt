package com.lightningkite.kotlin.anko

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Puts a footer on a RecyclerView.
 * Created by jivie on 5/5/16.
 */
@Deprecated("Doesn't work if list is empty.")
class FooterItemDecoration(val myView: View) : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        myView.measure(
                View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        myView.layout(parent.left, 0, parent.right, myView.measuredHeight);
        for (i in 0..parent.childCount - 1) {
            val view = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(view) == parent.adapter.itemCount - 1) {
                c.save();
                val height = myView.measuredHeight;
                val top = view.top - height;
                c.translate(0f, top.toFloat());
                myView.draw(c);
                c.restore();
                break;
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) == parent.adapter.itemCount - 1) {
            myView.measure(
                    View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            outRect.set(0, 0, 0, myView.measuredHeight);
        } else {
            outRect.setEmpty();
        }
    }
}