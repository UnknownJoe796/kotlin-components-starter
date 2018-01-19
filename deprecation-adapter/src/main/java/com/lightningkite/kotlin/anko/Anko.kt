@file:JvmName("Deprecated")
@file:JvmMultifileClass

package com.lightningkite.kotlin.anko

import android.support.v7.widget.RecyclerView
import android.view.ViewManager
import android.widget.EditText
import android.widget.ListView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoContextImpl
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.textResource
import java.text.NumberFormat

/**
 * Created by joseph on 1/19/18.
 */


@Deprecated("Old type of adapter")
@Suppress("NOTHING_TO_INLINE") inline fun ViewManager.adapterLinearLayout() = adapterLinearLayout {}

@Deprecated("Old type of adapter")
inline fun ViewManager.adapterLinearLayout(stretchMode: Boolean = false, init: AdapterLinearLayout.() -> Unit): AdapterLinearLayout {
    return ankoView({ AdapterLinearLayout(it, stretchMode) }, 0, init)
}

@Deprecated("Doesn't work if list is empty.")
inline fun RecyclerView.footer(
        crossinline makeView: AnkoContext<Unit>.() -> Unit
) {
    addItemDecoration(FooterItemDecoration(AnkoContextImpl(context, Unit, false).apply { makeView() }.view))
}


/**
 * Puts a header on a RecyclerView.
 * Created by jivie on 5/5/16.
 */
@Deprecated("Doesn't work if list is empty.")
inline fun RecyclerView.header(
        crossinline makeView: AnkoContext<Unit>.() -> Unit
) {
    addItemDecoration(HeaderItemDecoration(AnkoContextImpl(context, Unit, false).apply { makeView() }.view))
}


@Suppress("NOTHING_TO_INLINE") inline fun ViewManager.horizontalListView() = horizontalListView {}
inline fun ViewManager.horizontalListView(init: HorizontalListView.() -> Unit): HorizontalListView {
    return ankoView({ HorizontalListView(it, null) }, 0, init)
}

@Deprecated("Doesn't belong in general library.")
fun EditText.autoComma(format: NumberFormat) {
    textChanger {
        val resultString = format.format(it.after.filter {
            it.isDigit()
                    || it == NumericalString.decimalChar
                    || it == NumericalString.negativeChar
        }.toDoubleOrNull() ?: 0.0)
        val insertionPoint = NumericalString.transformPosition(it.after, resultString, it.insertionPoint + it.replacement.length).coerceIn(0, resultString.length)
        resultString to insertionPoint..insertionPoint
    }
}

inline fun ViewManager.progressButton(init: ProgressButton.() -> Unit): ProgressButton {
    return ankoView({ ProgressButton(it) }, 0, init)
}

inline fun ViewManager.progressButton(text: String, init: ProgressButton.() -> Unit): ProgressButton {
    return ankoView({ ProgressButton(it) }, 0, {
        button.text = text
        init()
    })
}

inline fun ViewManager.progressButton(textResource: Int, init: ProgressButton.() -> Unit): ProgressButton {
    return ankoView({ ProgressButton(it) }, 0, {
        button.textResource = textResource
        init()
    })
}

fun ListView.scrollStatus(): ScrollStatus {
    val listener = ScrollStatus()
    setOnScrollListener(listener)
    return listener
}