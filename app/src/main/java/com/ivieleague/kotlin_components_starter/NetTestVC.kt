package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.graphics.Bitmap
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.ivieleague.kotlin.anko.animation.makeHeightAnimator
import com.ivieleague.kotlin.anko.animation.transitionView
import com.ivieleague.kotlin.anko.networking.image.bitmap
import com.ivieleague.kotlin.anko.observable.bindString
import com.ivieleague.kotlin.anko.progressButton
import com.ivieleague.kotlin.anko.viewcontrollers.AnkoViewController
import com.ivieleague.kotlin.anko.viewcontrollers.MainViewController
import com.ivieleague.kotlin.anko.viewcontrollers.containers.VCStack
import com.ivieleague.kotlin.anko.viewcontrollers.dialogs.inputDialog
import com.ivieleague.kotlin.anko.viewcontrollers.implementations.VCActivity
import com.ivieleague.kotlin.networking.NetMethod
import com.ivieleague.kotlin.networking.NetRequest
import com.ivieleague.kotlin.networking.Networking
import com.ivieleague.kotlin.networking.async
import com.ivieleague.kotlin.observable.property.StandardObservableProperty
import com.ivieleague.kotlin.observable.property.bind
import com.ivieleague.kotlin.range.random
import org.jetbrains.anko.*

/**
 * A ViewController with various tests on it.
 * Created by josep on 11/6/2015.
 */
class NetTestVC(val main: MainViewController, val stack: VCStack) : AnkoViewController() {

    override fun getTitle(resources: Resources): String = "Net Test"

    val imageObs: StandardObservableProperty<Bitmap?> = object : StandardObservableProperty<Bitmap?>(null) {
        override var value: Bitmap?
            get() = super.value
            set(value) {
                this.value?.recycle()
                super.value = value
            }
    }
    var image: Bitmap? by imageObs

    val loadingObs: StandardObservableProperty<Boolean> = StandardObservableProperty(false)
    var loading: Boolean by loadingObs

    val textObs = StandardObservableProperty("Start Text")
    var text by textObs
    override fun createView(ui: AnkoContext<VCActivity>): View = ui.linearLayout {
        orientation = LinearLayout.VERTICAL
        setGravity(Gravity.CENTER)

        main.actionMenu?.menu?.item(R.string.app_name, android.R.drawable.ic_delete) {
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

//        button("Get an Image") {
//            onClick {
//                ui.owner.getImageFromGallery(1000) {
//                    image = it
//                }
//            }
//        }

        progressButton("Enter an image URL") {
            onClick {
                ui.owner.inputDialog("Enter an image URL", "URL", "") { url ->
                    if (url != null) {
                        running = true
                        loading = true
                        Networking.async(NetRequest(NetMethod.GET, url)) {
                            running = false
                            loading = false
                            if (it.isSuccessful) {
                                image = it.bitmap()
                            } else {
                                image = null
                                Log.e("NetTestVC", url)
                                Log.e("NetTestVC", it.code.toString())
                                Log.e("NetTestVC", it.string())
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
                Networking.async(NetRequest(NetMethod.GET, "http://lorempixel.com/${(200..600).random()}/${(200..600).random()}")) {
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

            val update = makeHeightAnimator(300, 0f)

            textView("Loading...") {
                gravity = Gravity.CENTER
            }.tag("loading")

            textView("No image.") {
                gravity = Gravity.CENTER
            }.tag("none")

            imageView() {
                scaleType = ImageView.ScaleType.CENTER_CROP
                lifecycle.bind(imageObs) {
                    if (it == null) {
                        imageBitmap = null
                        update(null)
                        return@bind
                    }
                    imageBitmap = it
                    update(null)
                }
            }.tag("image")

            lifecycle.bind(loadingObs) {
                if (it) {
                    animate("loading")
                }
            }
            lifecycle.bind(imageObs) {
                if (it == null) {
                    animate("none")
                } else {
                    animate("image")
                }
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