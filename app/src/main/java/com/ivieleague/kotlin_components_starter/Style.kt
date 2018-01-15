package com.ivieleague.kotlin_components_starter

import android.graphics.Color
import android.graphics.Typeface
import android.widget.TextView
import org.jetbrains.anko.textColor

/**
 * We usually use a file like this to define styling.
 *
 * Created by jivie on 7/13/16.
 */

fun TextView.styleDefault(){
    textSize = 14f
    textColor = Color.WHITE
}


fun TextView.styleInvertedTitle() {
    textSize = 18f
    textColor = Color.BLACK
    setTypeface(null, Typeface.BOLD)
}

fun TextView.styleInverted() {
    textSize = 14f
    textColor = Color.BLACK
}
