package com.ivieleague.kotlin.anko

import android.graphics.drawable.Drawable

/**
 * Various helper functions
 * Created by jivie on 2/9/16.
 */

inline fun Drawable.setBoundsCentered(centerX: Float, centerY: Float) = setBoundsCentered(centerX.toInt(), centerY.toInt())

inline fun Drawable.setBoundsCentered(centerX: Int, centerY: Int) {
    val left = centerX - minimumWidth / 2
    val top = centerY - minimumHeight / 2
    setBounds(left, top, left + minimumWidth, top + minimumHeight)
}