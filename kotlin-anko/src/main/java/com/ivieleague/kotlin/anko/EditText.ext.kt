package com.ivieleague.kotlin.anko

import android.widget.EditText
import android.widget.TextView

/**
 * Extensions functions on EditText.
 * Created by jivie on 5/2/16.
 */

fun EditText.resetCursorColor() {
    try {
        val f = TextView::class.java.getDeclaredField("mCursorDrawableRes");
        f.isAccessible = true;
        f.set(this, 0);
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}