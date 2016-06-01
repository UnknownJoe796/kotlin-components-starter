package com.ivieleague.kotlin.anko

import android.view.Menu
import android.view.MenuItem

/**
 * Created by jivie on 3/15/16.
 */

inline fun Menu.add(textRes: Int, iconRes: Int, setup: MenuItem.() -> Unit) =
        this.add(textRes).apply {
            setIcon(iconRes)
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }.apply(setup)


inline fun Menu.add(textRes: Int, setup: MenuItem.() -> Unit) = this.add(textRes).apply(setup)
inline fun Menu.add(text: CharSequence, setup: MenuItem.() -> Unit) = this.add(text).apply(setup)
inline fun Menu.remove(child: MenuItem) {
    removeItem(child.itemId)
}
