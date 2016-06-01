package com.ivieleague.kotlin.anko

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import org.jetbrains.anko.findOptional

/**
 * Created by josep on 3/3/2016.
 */


fun View.snackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_LONG, init: Snackbar.() -> Unit = {}) {
    val snack = Snackbar.make(this, text, duration)
    snack.init()
    snack.show()
}

fun View.snackbar(text: Int, duration: Int = Snackbar.LENGTH_LONG, init: Snackbar.() -> Unit = {}) {
    val snack = Snackbar.make(this, text, duration)
    snack.init()
    snack.show()
}

fun Snackbar.onDismissed(lambda: (event: Int) -> Unit) {
    setCallback(object : Snackbar.Callback() {
        override fun onDismissed(snackbar: Snackbar?, event: Int) {
            lambda(event)
        }
    })
}

fun Context.snackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_LONG, init: Snackbar.() -> Unit = {})
        = getActivity()?.findOptional<View>(android.R.id.content)?.snackbar(text, duration, init)

fun Context.snackbar(text: Int, duration: Int = Snackbar.LENGTH_LONG, init: Snackbar.() -> Unit = {})
        = getActivity()?.findOptional<View>(android.R.id.content)?.snackbar(text, duration, init)

fun android.support.design.widget.Snackbar.callback(init: _Snackbar_Callback.() -> Unit) {
    val callback = _Snackbar_Callback()
    callback.init()
    setCallback(callback)
}

class _Snackbar_Callback : android.support.design.widget.Snackbar.Callback() {

    private var _onShown: ((Snackbar?) -> Unit)? = null
    private var _onDismissed: ((Snackbar?, Int) -> Unit)? = null

    override fun onShown(snackbar: Snackbar?) {
        _onShown?.invoke(snackbar)
    }

    fun onSnackbarShown(listener: (Snackbar?) -> Unit) {
        _onShown = listener
    }

    override fun onDismissed(snackbar: Snackbar?, event: Int) {
        _onDismissed?.invoke(snackbar, event)
    }

    fun onSnackbarDismissed(listener: (Snackbar?, Int) -> Unit) {
        _onDismissed = listener
    }
}
