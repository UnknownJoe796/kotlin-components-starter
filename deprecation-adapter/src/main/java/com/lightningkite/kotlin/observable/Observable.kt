@file:JvmName("Deprecated")

package com.lightningkite.kotlin.observable

import com.lightningkite.kotlin.lifecycle.LifecycleConnectable
import com.lightningkite.kotlin.observable.property.*

@Deprecated("Just manipulate the observable instead.")
inline fun <A, T> LifecycleConnectable.bindSub(observable: ObservableProperty<A>, crossinline mapper: (A) -> ObservableProperty<T>, noinline action: (T) -> Unit) {
    val obs = ObservableObservableProperty(mapper(observable.value))
    bind(observable) {
        obs.observable = mapper(it)
    }
    bind(obs, action)
}

@Deprecated("Use 'transform' instead.", ReplaceWith(
        "transform(mapper, reverseMapper)",
        "com.lightningkite.kotlin.observable.transform"
))
inline fun <S, T> MutableObservableProperty<S>.mapObservable(noinline mapper: (S) -> T, noinline reverseMapper: (T) -> S): MutableObservablePropertyMapped<S, T> {
    return MutableObservablePropertyMapped(this, mapper, reverseMapper)
}

@Deprecated("Use 'transform' instead.", ReplaceWith(
        "transform(mapper, reverseMapper)",
        "com.lightningkite.kotlin.observable.transform"
))
inline fun <S, T> ObservableProperty<S>.mapReadOnly(noinline mapper: (S) -> T): ObservablePropertyMapped<S, T> {
    return ObservablePropertyMapped(this, mapper)
}