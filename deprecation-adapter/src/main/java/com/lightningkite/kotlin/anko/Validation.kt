package com.lightningkite.kotlin.anko

import android.view.View

/**
 * Created by joseph on 8/1/16.
 *
 * Can be used on forms to easily do validation.
 *
 * Make a collection of these and use the extension functions below.
 */
@Deprecated("Use the new validation system in the observable package.")
class Validation(
        val view: View? = null,
        val validator: () -> String? = { null }
) {
    fun getIssue(): ValidationIssue? {
        return ValidationIssue(view, validator() ?: return null)
    }

    operator fun component1(): View? = view
    operator fun component2(): View? = view
}