package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.graphics.Bitmap
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.lightningkite.kotlincomponents.animation.makeHeightAnimator
import com.lightningkite.kotlincomponents.animation.transitionView
import com.lightningkite.kotlincomponents.async.doAsync
import com.lightningkite.kotlincomponents.image.getImageFromGallery
import com.lightningkite.kotlincomponents.linearLayout
import com.lightningkite.kotlincomponents.logging.logE
import com.lightningkite.kotlincomponents.networking.Networking
import com.lightningkite.kotlincomponents.observable.KObservable
import com.lightningkite.kotlincomponents.observable.bind
import com.lightningkite.kotlincomponents.observable.bindString
import com.lightningkite.kotlincomponents.random
import com.lightningkite.kotlincomponents.ui.inputDialog
import com.lightningkite.kotlincomponents.ui.progressButton
import com.lightningkite.kotlincomponents.viewcontroller.MainViewController
import com.lightningkite.kotlincomponents.viewcontroller.StandardViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import org.jetbrains.anko.*

/**
 * A ViewController with various tests on it.
 * Created by josep on 11/6/2015.
 */
class NetTestVC(val main: MainViewController, val stack: VCStack) : StandardViewController() {

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

        main.actionMenu?.menu?.item("delete", android.R.drawable.ic_delete) {
            setOnMenuItemClickListener {
                println("delete")
                image = null
                true
            }
        }

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
                val url = "http://lorempixel.com/${(200..600).random()}/${(200..600).random()}"
                doAsync({ Networking.syncGet(url).bitmap() }) {
                    image = it
                    running = false
                    loading = false
                }
                /*
                * Networking.do{ get(url).bitmap() }.later{
                * }
                * */
            }
        }
        transitionView {

            val update = makeHeightAnimator(300, 0f)

            textView("Loading...") {
                gravity = Gravity.CENTER
            }.tag("loading")

            textView("No image.") {
                gravity = Gravity.CENTER
            }.tag("none")

            imageView() {
                scaleType = ImageView.ScaleType.CENTER_CROP
                bind(imageObs) {
                    if (it == null) {
                        imageBitmap = null
                        update(null)
                        return@bind
                    }
                    imageBitmap = it
                    update(null)
                }
            }.tag("image")

            bind(loadingObs) {
                if (it) {
                    animate("loading")
                }
            }
            bind(imageObs) {
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