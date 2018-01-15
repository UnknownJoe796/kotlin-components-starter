package com.ivieleague.kotlin_components_starter

import com.lightningkite.kotlin.anko.viewcontrollers.ViewController
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.VCActivity

/**
 * The main activity is mostly empty when using [ViewController]s.
 */
class MainActivity : VCActivity() {

    companion object {
        var main = MainVC()
    }

    override val viewController: ViewController get() = main
}
