package com.ivieleague.kotlin_components_starter

import com.lightningkite.kotlin.anko.viewcontrollers.ViewController
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.VCActivity

class MainActivity : VCActivity() {

    companion object {
        val main = MainVC()
    }

    override val viewController: ViewController get() = main
}
