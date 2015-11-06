package com.ivieleague.kotlin_components_starter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack

import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity

class MainActivity : VCActivity() {

    companion object {
        val stack: VCStack = VCStack()

        init {
            stack.push(StartVC())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        attach(stack)
    }
}
