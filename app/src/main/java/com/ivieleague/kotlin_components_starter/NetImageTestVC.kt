package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.animation.animateHeight
import com.lightningkite.kotlin.anko.animation.transitionView
import com.lightningkite.kotlin.anko.async.UIThread
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.networking.image.lambdaBitmapExif
import com.lightningkite.kotlin.anko.progressButton
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.anko.viewcontrollers.dialogs.inputDialog
import com.lightningkite.kotlin.async.Async
import com.lightningkite.kotlin.async.invokeOn
import com.lightningkite.kotlin.async.thenOn
import com.lightningkite.kotlin.observable.property.StandardObservableProperty
import com.lightningkite.kotlin.observable.property.bind
import com.lightningkite.kotlin.range.random
import okhttp3.Request
import org.jetbrains.anko.*

/**
 * A ViewController with various tests on it.
 * Created by josep on 11/6/2015.
 */
class NetImageTestVC() : AnkoViewController() {

    //Returns the title for the app bar.
    override fun getTitle(resources: Resources): String = "Net Test"

    //Creates and observable property, initialized to null.
    val imageUrlObs = StandardObservableProperty<String?>(null)

    override fun createView(ui: AnkoContext<VCContext>): View = ui.verticalLayout {
        gravity = Gravity.CENTER

        button("Enter an image URL") {
            setOnClickListener { it: View? ->
                ui.ctx.inputDialog("Enter an image URL", "URL", "") { url ->
                    imageUrlObs.value = url
                }
            }
        }

        progressButton("Lorem Pixel") {
            setOnClickListener {
                imageUrlObs.value = "http://lorempixel.com/${(200..600).random()}/${(200..600).random()}"
            }
        }

        //A transition view is used to transition between multiple views, usually with a fade effect.
        transitionView {

            textView("Loading...") {
                gravity = Gravity.CENTER
            }.tag("loading") //Tags the view as "loading".  Now if animate("loading") is called, the view animates to this text view.

            textView("No image.") {
                gravity = Gravity.CENTER
            }.tag("none")

            textView("Image load failed.") {
                gravity = Gravity.CENTER
            }.tag("fail")

            imageView {
                animateHeight() //This enables automatic height change animations.
                adjustViewBounds = true

                //This can be read as:
                //Right now and whenever imageUrlObs changes, do what is within the curly braces.
                lifecycle.bind(imageUrlObs) { url ->
                    println("imageUrlObs.value = $url")
                    if (url == null || url.isBlank()) {
                        this@transitionView.animate("none") //transitions us to the "none" view
                    } else {
                        this@transitionView.animate("loading") //transitions us to the "loading" view
                        //This is a request builder from OkHttp.
                        Request.Builder()
                                .url(url)
                                .get()
                                .lambdaBitmapExif(context) //this last one builds the request into a lambda, which can then be run using invoke()
                                .thenOn(UIThread) { response ->
                                    //this invokes the lambda on a separate thread, then runs this callback on the UI thread
                                    if (response.isSuccessful()) {
                                        imageBitmap = response.result
                                        this@transitionView.animate("image") //transitions us to the "image" view
                                    } else {
                                        println(response)
                                        response.exception?.printStackTrace()
                                        this@transitionView.animate("fail") //transitions us to the "fail" view
                                    }
                                }.invokeOn(Async)
                    }
                }
            }.tag("image")
        }
    }
}