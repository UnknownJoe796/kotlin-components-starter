package com.ivieleague.kotlin.anko

import android.app.Activity
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

/**
 * Created by jivie on 3/28/16.
 */
inline fun View.isAttachedToWindowCompat(): Boolean = ViewCompat.isAttachedToWindow(this)

inline fun View.setBackground(vararg list: Drawable) {
    background = LayerDrawable(list)
}

inline fun View.setBackground(vararg list: Int) {
    background = LayerDrawable(Array(list.size) { resources.getDrawable(list[it]) })
}

inline fun <T : View> T.lparamsMod(width: Int, height: Int): T {
    layoutParams.apply {
        this.width = width
        this.height = height
    }
    return this
}

inline fun <T : View> T.lparamsMod(width: Int, height: Int, weight: Float): T {
    layoutParams.apply {
        if (this !is LinearLayout.LayoutParams) throw IllegalArgumentException("This only works on LinearLayout.LayoutParams")
        this.width = width
        this.height = height
        this.weight = weight
    }
    return this
}

var View.elevationCompat: Float
    get() {
        runIfNewerThan(Build.VERSION_CODES.LOLLIPOP) {
            return elevation
        }
        return 0f
    }
    set(value) {
        runIfNewerThan(Build.VERSION_CODES.LOLLIPOP) {
            elevation = value
        }
    }

val View.visualTop: Int get() {
    val layoutParams = layoutParams
    return top - (
            if (layoutParams is ViewGroup.MarginLayoutParams)
                layoutParams.topMargin
            else
                0
            ) + (translationY + .5f).toInt()
};

val View.visualBottom: Int get() {
    val layoutParams = layoutParams
    return bottom + (
            if (layoutParams is ViewGroup.MarginLayoutParams)
                layoutParams.bottomMargin
            else
                0
            ) + (translationY + .5f).toInt()
};


fun View.getActivity(): Activity? {
    return context.getActivity()
}