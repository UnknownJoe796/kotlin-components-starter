package com.ivieleague.kotlin_components_starter

import android.os.Bundle
import com.lightningkite.kotlin.anko.activity.AccessibleActivity

/**
 * The main activity is mostly empty when using [ViewController]s.
 */
class MainActivity : AccessibleActivity() {

    companion object {
        var main = MainScreen()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(main.invoke(this))
    }
}
