package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.graphics.Bitmap
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.lightningkite.kotlincomponents.animation.animateHeightUpdate
import com.lightningkite.kotlincomponents.animation.transitionView
import com.lightningkite.kotlincomponents.image.getImageFromGallery
import com.lightningkite.kotlincomponents.logging.logD
import com.lightningkite.kotlincomponents.logging.logE
import com.lightningkite.kotlincomponents.networking.Networking
import com.lightningkite.kotlincomponents.observable.KObservable
import com.lightningkite.kotlincomponents.observable.bind
import com.lightningkite.kotlincomponents.observable.bindString
import com.lightningkite.kotlincomponents.random
import com.lightningkite.kotlincomponents.ui.inputDialog
import com.lightningkite.kotlincomponents.ui.progressButton
import com.lightningkite.kotlincomponents.viewcontroller.StandardViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import com.lightningkite.kotlincomponents.viewcontroller.linearLayout
import org.jetbrains.anko.*

/**
 * A ViewController with various tests on it.
 * Created by josep on 11/6/2015.
 */
class NetTestVC(val stack: VCStack) : StandardViewController() {

    override fun getTitle(resources: Resources): String = "Net Test"

    val imageObs: KObservable<Bitmap?> = object : KObservable<Bitmap?>(null) {
        override fun set(v: Bitmap?) {
            this.value?.recycle()
            super.set(v)
        }
    }
    var image: Bitmap? by imageObs

    val loadingObs: KObservable<Boolean> = KObservable(false)
    var loading: Boolean by loadingObs

    val textObs = KObservable("Start Text")
    var text by textObs

    override fun makeView(activity: VCActivity): View = linearLayout(activity) {
        orientation = LinearLayout.VERTICAL
        setGravity(Gravity.CENTER)

        textView("Hello World") {
            gravity = Gravity.CENTER
        }

        editText() {
            bindString(textObs)
        }

        textView() {
            bindString(textObs)
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
                Networking.get("http://lorempixel.com/${(200..600).random()}/${(200..600).random()}") {
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
                val update = animateHeightUpdate(300, 0f)
                scaleType = ImageView.ScaleType.CENTER_CROP
                bind(imageObs) {
                    if (it == null) return@bind
                    imageBitmap = it
                    update()
                }
            }.tag("image")
            bind(loadingObs) {
                if (it) {
                    animate("loading")
                }
            }
            bind(imageObs) {
                logD(it)
                if (it == null) {
                    animate("none")
                } else {
                    animate("image")
                }
            }
        }

        button("Test Socket IO") {
            onClick {
                stack.push(SocketIOVC(stack))
            }
        }

        button("Test List") {
            onClick {
                stack.push(ListTestVC(stack))
            }
        }

        button("I wanna do something else") {
            onClick {
                stack.push(AnotherVC(stack))
            }
        }
    }
}