package com.lightningkite.kotlin.observable.property

import java.io.Closeable

/**
 * An observable observing an observable.
 * Created by jivie on 4/5/16.
 */
@Deprecated("Use ObservablePropertySubObservable instead.")
class ObservableObservableProperty<T>(initialObservable: ObservableProperty<T>) : BaseObservableProperty<T>(), Closeable {
    val myListener: (T) -> Unit = {
        super.update()
    }


    init {
        initialObservable.add(myListener)
    }

    var observable: ObservableProperty<T> = initialObservable
        set(value) {
            field.remove(myListener)
            field = value
            field.add(myListener)
            super.update()
        }

    override fun close() {
        observable.remove(myListener)
    }

    override var value: T
        get() = observable.value
        set(value) {
            val obs = observable
            if (obs is MutableObservableProperty) {
                obs.value = value
            } else {
                throw IllegalAccessException()
            }
        }
}