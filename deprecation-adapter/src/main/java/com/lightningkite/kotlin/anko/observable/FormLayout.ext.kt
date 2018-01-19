@file:JvmName("Deprecated")
@file:JvmMultifileClass

package com.lightningkite.kotlin.anko.observable

import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

@Deprecated("Instead of using a FormLayout, use standard layouts instead and then use a collection of Validations to validate.")
inline fun ViewManager.formLayout(init: FormLayout.() -> Unit) = ankoView(::FormLayout, 0, init)