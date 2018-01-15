package com.lightningkite.kotlin.anko

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

@Deprecated("Just make your own LayerDrawable.")
inline fun View.setBackground(vararg list: Drawable) {
    background = LayerDrawable(list)
}

@Deprecated("Just make your own LayerDrawable.")
inline fun View.setBackground(vararg list: Int) {
    background = LayerDrawable(Array(list.size) { resources.getDrawable(list[it]) })
}

@Deprecated("Just modify it normally.")
inline fun <T : View> T.lparamsMod(width: Int, height: Int): T {
    layoutParams.apply {
        this.width = width
        this.height = height
    }
    return this
}

@Deprecated("Just modify it normally.")
inline fun <T : View> T.lparamsMod(width: Int, height: Int, weight: Float): T {
    layoutParams.apply {
        if (this !is LinearLayout.LayoutParams) throw IllegalArgumentException("This only works on LinearLayout.LayoutParams")
        this.width = width
        this.height = height
        this.weight = weight
    }
    return this
}

@Deprecated("Just modify it normally.")
inline fun <T : View> T.lparamsMod(width: Int, height: Int, lambda: ViewGroup.MarginLayoutParams.() -> Unit): T {
    layoutParams.apply {
        if (this !is ViewGroup.MarginLayoutParams) throw IllegalArgumentException("This only works on ViewGroup.MarginLayoutParams")
        this.width = width
        this.height = height
        lambda()
    }
    return this
}

@Deprecated("Just modify it normally.")
inline fun <T : View> T.lparamsMod(lambda: ViewGroup.MarginLayoutParams.() -> Unit): T {
    layoutParams.apply {
        if (this !is ViewGroup.MarginLayoutParams) throw IllegalArgumentException("This only works on ViewGroup.MarginLayoutParams")
        lambda()
    }
    return this
}

@Deprecated("Just measure it normally.")
inline fun View.measureDesiredWidth(): Int {
    this.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec((parent as ViewGroup).height, View.MeasureSpec.AT_MOST)
    )
    return measuredHeight
}

@Deprecated("Just measure it normally.")
inline fun View.measureDesiredHeight(): Int {
    this.measure(
            View.MeasureSpec.makeMeasureSpec((parent as ViewGroup).width, View.MeasureSpec.AT_MOST),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    return measuredHeight
}