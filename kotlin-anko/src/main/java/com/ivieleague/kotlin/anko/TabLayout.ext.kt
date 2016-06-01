package com.ivieleague.kotlin.anko

import android.support.design.widget.TabLayout
import org.jetbrains.anko.sp

/**
 * Created by jivie on 4/28/16.
 */
inline fun TabLayout.setTabTextSize(sp: Float) {
    //Give the middle finger to the designer of TabLayout for me.
    //We're going to reflect our way into this mess.
    val field = TabLayout::class.java.getDeclaredField("mTabTextSize")
    field.isAccessible = true
    field.set(this, sp(sp))
}