package com.ivieleague.kotlin.anko

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

/**
 * Extension functions for Context
 * Created by jivie on 6/1/16.
 */


fun Context.getActivity(): Activity? {
    if (this is Activity) {
        return this
    } else if (this is ContextWrapper) {
        return baseContext.getActivity()
    } else {
        return null
    }
}