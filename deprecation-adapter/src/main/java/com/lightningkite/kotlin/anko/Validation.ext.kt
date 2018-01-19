@file:JvmName("Deprecated")
@file:JvmMultifileClass

package com.lightningkite.kotlin.anko

import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.TextView

@Deprecated("Use the new validation system in the observable package.")
fun Collection<Validation>.issues(): List<ValidationIssue> = mapNotNull { it.getIssue() }

@Deprecated("Use the new validation system in the observable package.")
fun Collection<Validation>.firstIssue(): ValidationIssue? = mapNotNull { it.getIssue() }.firstOrNull()

@Deprecated("Use the new validation system in the observable package.")
fun Collection<Validation>.validOrSnackbar(snackView: View): Boolean {
    val issues = issues()
    if (issues.isEmpty()) return true

    for ((view, validator) in this) {
        if (view is TextInputLayout) {
            view.error = null
        }
        if (view is TextView) {
            view.error = null
        }
    }

    val unhandled = ArrayList<ValidationIssue>()
    for (issue in issues.asReversed()) {
        val view = issue.view
        if (view is TextInputLayout) {
            view.error = issue.message
        } else if (view is TextView) {
            view.error = issue.message
        } else {
            unhandled += issue
        }
    }

    val error = issues.first()
    error.view?.requestFocus()

    unhandled.firstOrNull()?.let {
        snackView.snackbar(it.message)
    }

    return false
}

@Deprecated("Use the new validation system in the observable package.")
fun MutableCollection<Validation>.quickAdd(view: View, errorResource: Int, check: () -> Boolean) {
    add(Validation(view, {
        if (!check()) {
            view.resources.getString(errorResource)
        } else {
            null
        }
    }))
}