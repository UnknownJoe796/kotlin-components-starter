@file:JvmName("Deprecated")
@file:JvmMultifileClass

package com.lightningkite.kotlin.anko.animation

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.view.View

@Deprecated("Not used often enough to warrant inclusion.")
fun View.animateHighlight(milliseconds: Long, color: Int, millisecondsTransition: Int = 200) {
    assert(milliseconds > millisecondsTransition * 2) { "The time shown must be at least twice as much as the transition time" }
    val originalBackground = background
    val transition = TransitionDrawable(arrayOf(originalBackground ?: ColorDrawable(0x0), ColorDrawable(color)))
    transition.isCrossFadeEnabled = false
    background = transition
    transition.startTransition(millisecondsTransition)
    postDelayed({
        transition.reverseTransition(millisecondsTransition)
        postDelayed({
            background = originalBackground
        }, millisecondsTransition.toLong())
    }, milliseconds - millisecondsTransition)
}