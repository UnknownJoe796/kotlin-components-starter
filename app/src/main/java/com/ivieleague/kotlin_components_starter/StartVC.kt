package com.ivieleague.kotlin_components_starter

import android.graphics.Bitmap
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.lightningkite.kotlincomponents.animation.transitionView
import com.lightningkite.kotlincomponents.databinding.Bond
import com.lightningkite.kotlincomponents.image.getImageFromGallery
import com.lightningkite.kotlincomponents.logging.logD
import com.lightningkite.kotlincomponents.logging.logE
import com.lightningkite.kotlincomponents.networking.Networking
import com.lightningkite.kotlincomponents.ui.inputDialog
import com.lightningkite.kotlincomponents.ui.progressButton
import com.lightningkite.kotlincomponents.vertical
import com.lightningkite.kotlincomponents.viewcontroller.AutocleanViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import com.lightningkite.kotlincomponents.viewcontroller.implementations.dialog
import com.lightningkite.kotlincomponents.viewcontroller.linearLayout
import org.jetbrains.anko.*

/**
 * Created by josep on 11/6/2015.
 */
class StartVC(val stack: VCStack) : AutocleanViewController() {

    val imageBond: Bond<Bitmap?> = listener(object : Bond<Bitmap?>(null) {
        override fun set(v: Bitmap?) {
            this.myValue?.recycle()
            super.set(v)
        }
    })
    var image: Bitmap? by imageBond

    val loadingBond: Bond<Boolean> = listener(Bond(false))
    var loading: Boolean by loadingBond

    override fun make(activity: VCActivity): View {

        return linearLayout(activity) {
            orientation = LinearLayout.VERTICAL
            setGravity(Gravity.CENTER)

            textView("Hello World") {
                gravity = Gravity.CENTER
            }

            button("Get an Image") {
                onClick {
                    activity.getImageFromGallery(1000) {
                        image = it
                    }
                }
            }

            progressButton("Enter an image URL") {
                onClick {
                    activity.inputDialog("Enter an image URL", "URL") { url ->
                        if (url != null) {
                            running = true
                            loading = true
                            Networking.get(url) {
                                running = false
                                loading = false
                                if (it.isSuccessful) {
                                    image = it.bitmap()
                                } else {
                                    image = null
                                    logE(url)
                                    logE(it.code)
                                    logE(it.string())
                                }
                            }
                        }
                    }
                }
            }

            progressButton("Lorem Pixel") {
                onClick {
                    running = true
                    loading = true
                    Networking.get("http://lorempixel.com/300/300") {
                        running = false
                        loading = false
                        if (it.isSuccessful) {
                            image = it.bitmap()
                        } else {
                            image = null
                        }
                    }
                }
            }
            transitionView {

                textView("Loading...") {
                    gravity = Gravity.CENTER
                }.tag("loading")

                textView("No image.") {
                    gravity = Gravity.CENTER
                }.tag("none")

                imageView() {
                    imageBond.bind {
                        if (it == null) return@bind
                        imageBitmap = it
                    }
                }.tag("image")
                loadingBond.bind {
                    if (it) {
                        animate("loading")
                    }
                }
                imageBond.bind {
                    logD(it)
                    if (it == null) {
                        animate("none")
                    } else {
                        animate("image")
                    }
                }
            }

            button("I wanna do something else") {
                onClick {
                    stack.push(AnotherVC(stack))
                }
            }
        }
    }
}