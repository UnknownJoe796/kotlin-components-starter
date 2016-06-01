package com.ivieleague.kotlin.anko.observable

import android.view.View
import com.ivieleague.kotlin.lifecycle.LifecycleConnectable
import com.ivieleague.kotlin.lifecycle.LifecycleListener

/**
 * Various extension functions to support bonds.
 * Created by jivie on 7/22/15.
 */

object ViewLifecycleConnecter : LifecycleConnectable {

    var view: View? = null

    override fun connect(listener: LifecycleListener) {
        view?.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View?) {
                listener.onStart()
            }

            override fun onViewDetachedFromWindow(v: View?) {
                listener.onStop()
            }
        })
    }
}


/**
 * Gets a lifecycle object for events to connect with.
 * There is only one lifecycle object that is recycled, so the lifecycle returned expires when
 * another lifecycle is requested.
 */
val View.lifecycle: LifecycleConnectable
    get() {
        ViewLifecycleConnecter.view = this
        return ViewLifecycleConnecter
    }