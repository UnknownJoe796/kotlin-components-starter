package com.ivieleague.kotlin.anko

import android.app.Activity
import org.jetbrains.anko.selector

/**
 * Various extension functions for activities.
 * Created by jivie on 4/12/16.
 */
inline fun Activity.selector(title: CharSequence?, pairs: List<Pair<CharSequence, () -> Unit>>) {
    selector(title, pairs.map { it.first }) {
        if (it >= 0) {
            pairs[it].second()
        }
    }
}

inline fun Activity.selector(title: CharSequence?, vararg pairs: Pair<CharSequence, () -> Unit>) {
    selector(title, pairs.map { it.first }) {
        if (it >= 0) {
            pairs[it].second()
        }
    }
}

inline fun Activity.selector(title: Int?, pairs: List<Pair<Int, () -> Unit>>) {
    val titleString = if (title == null) null else resources.getString(title)
    selector(titleString, pairs.map { resources.getString(it.first) }) {
        if (it >= 0) {
            pairs[it].second()
        }
    }
}

inline fun Activity.selector(title: Int?, vararg pairs: Pair<Int, () -> Unit>) {
    val titleString = if (title == null) null else resources.getString(title)
    selector(titleString, pairs.map { resources.getString(it.first) }) {
        if (it >= 0) {
            pairs[it].second()
        }
    }
}