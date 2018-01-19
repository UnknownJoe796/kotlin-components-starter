package com.lightningkite.kotlin.observable.property

import java.io.Closeable

/**
 * An observable observing an observable
 * Created by jivie on 4/5/16.
 */
@Deprecated("Use ObservablePropertySubObservable instead.")
class ObservableObservablePropertyOpt<T>(initialObservable: MutableObservableProperty<T>? = null) : BaseObservableProperty<T?>(), Closeable {
    val myListener: (T) -> Unit = {
        super.update()
    }

    var observable: ObservableProperty<T>? = null
        set(value) {
            field?.remove(myListener)
            field = value
            field?.add(myListener)
            super.update()
        }

    init {
        observable = initialObservable
    }

    override var value: T?
        get() = observable?.value
        set(value) {
            val obs = observable
            if (obs is MutableObservableProperty && value != null) {
                obs.value = value
            } else {
                throw IllegalAccessException()
            }
        }

    override fun close() {
        observable?.remove(myListener)
    }

}