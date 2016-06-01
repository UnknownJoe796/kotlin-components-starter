package com.ivieleague.kotlin.anko

import android.graphics.Typeface
import android.widget.TextView
import java.util.*

/**
 * Extensions for TextView.
 * Created by josep on 3/3/2016.
 */


var TextView.textColorResource: Int
    get() = throw IllegalAccessException()
    set(value) {
        setTextColor(resources.getColor(value))
    }
var TextView.hintTextColorResource: Int
    get() = throw IllegalAccessException()
    set(value) {
        setHintTextColor(resources.getColor(value))
    }
var TextView.textColorsResource: Int
    get() = throw IllegalAccessException()
    set(value) {
        setTextColor(resources.getColorStateList(value))
    }
var TextView.hintTextColorsResource: Int
    get() = throw IllegalAccessException()
    set(value) {
        setHintTextColor(resources.getColorStateList(value))
    }

val fontCache: HashMap<String, Typeface> = HashMap()
fun TextView.setFont(fileName: String) {
    typeface = fontCache[fileName] ?: {
        val font = Typeface.createFromAsset(context.assets, fileName)
        fontCache[fileName] = font
        font
    }()
}

inline fun TextView.leftDrawable(resourceId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(resources.getDrawableCompat(resourceId), null, null, null)
}

inline fun TextView.rightDrawable(resourceId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(null, null, resources.getDrawableCompat(resourceId), null)
}

inline fun TextView.topDrawable(resourceId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(null, resources.getDrawableCompat(resourceId), null, null)
}

inline fun TextView.bottomDrawable(resourceId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, resources.getDrawableCompat(resourceId))
}