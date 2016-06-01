package com.ivieleague.kotlin.anko

import android.os.Build

/**
 * Various functions for Android devices only.
 * Created by jivie on 6/1/16.
 */
inline fun runIfNewerThan(version: Int, action: () -> Unit) {
    if (Build.VERSION.SDK_INT >= version) action()
}

inline fun <T> runIfNewerThan(version: Int, action: () -> T, other: () -> T): T {
    return if (Build.VERSION.SDK_INT >= version) action() else other()
}