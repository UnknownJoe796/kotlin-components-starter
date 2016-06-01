package com.ivieleague.kotlin_components_starter

import android.os.Bundle
import com.ivieleague.kotlin.anko.viewcontrollers.containers.VCSwapper
import com.ivieleague.kotlin.anko.viewcontrollers.implementations.VCActivity

class MainActivity : VCActivity() {

    companion object {
        val stack = VCSwapper(MainVC())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attach(stack)
    }
}
