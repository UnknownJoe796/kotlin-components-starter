package com.lightningkite.kotlin_components_starter

import android.graphics.Color
import android.widget.TextView
import org.jetbrains.anko.dip
import org.jetbrains.anko.padding
import org.jetbrains.anko.textColor

/**
 * We usually use a file like this to define styling.
 *
 * Created by jivie on 7/13/16.
 */

fun TextView.styleDefault(){
    textSize = 14f
    textColor = Color.WHITE
    padding = dip(4)
}
