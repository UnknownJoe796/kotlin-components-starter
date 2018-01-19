@file:JvmName("Deprecated")
@file:JvmMultifileClass

package com.lightningkite.kotlin.anko

import android.view.Menu
import android.view.MenuItem


@Deprecated("Menus not used often enough to be justified in the library.")
inline fun Menu.add(textRes: Int, iconRes: Int, setup: MenuItem.() -> Unit) =
        this.add(textRes).apply {
            setIcon(iconRes)
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }.apply(setup)


@Deprecated("Menus not used often enough to be justified in the library.")
inline fun Menu.add(textRes: Int, setup: MenuItem.() -> Unit) = this.add(textRes).apply(setup)

@Deprecated("Menus not used often enough to be justified in the library.")
inline fun Menu.add(text: CharSequence, setup: MenuItem.() -> Unit) = this.add(text).apply(setup)

@Deprecated("Menus not used often enough to be justified in the library.")
inline fun Menu.remove(child: MenuItem) {
    removeItem(child.itemId)
}
