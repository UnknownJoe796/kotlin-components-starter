package com.ivieleague.kotlin.anko

import android.content.res.Resources
import android.graphics.drawable.Drawable

/**
 * Created by jivie on 5/4/16.
 */
fun Resources.getColorCompat(resources: Int): Int {
    return runIfNewerThan(23, { getColor(resources, null) }, { getColor(resources) })
}

fun Resources.getDrawableCompat(resources: Int): Drawable {
    return runIfNewerThan(23, { getDrawable(resources, null) }, { getDrawable(resources) })
}