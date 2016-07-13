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
import com.ivieleague.kotlin.anko.observable.lifecycle
import com.ivieleague.kotlin.anko.progressButton
import com.ivieleague.kotlin.anko.viewcontrollers.AnkoViewController
import com.ivieleague.kotlin.anko.viewcontrollers.MainViewController
import com.ivieleague.kotlin.anko.viewcontrollers.containers.VCStack
import com.ivieleague.kotlin.anko.viewcontrollers.dialogs.inputDialog
import com.ivieleague.kotlin.anko.viewcontrollers.implementations.VCActivity
import com.ivieleague.kotlin.lifecycle.listen
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

    //Returns the title for the app bar.
    override fun getTitle(resources: Resources): String = "Net Test"

    /**
     * An observable property is just a holder for a value that reports whenever it is changed.  It
     * can be used as a property delegate.
     *
     * This one in particular references a bitmap and is overridden to recycle the old one.
     */
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


        //adds an item to the menu for the duration of this ViewController.
        main.actionMenu?.menu?.item(R.string.app_name, android.R.drawable.ic_delete) {
            setOnMenuItemClickListener {
                println("delete")
                image = null
                true
            }
        }

        textView("Hello World") {
            gravity = Gravity.CENTER
            styleDefault() //Styles this view.  See Style.kt for more information.
        }

        editText() {
            styleDefault()

            //This ensures that the value of the observable and the text inside this EditText are always the same
            bindString(textObs)
        }

        textView() {
            styleDefault()

            //This bind is read only.
            bindString(textObs)
        }

        textView() {
            styleDefault()

            //This is the more manual way of binding things to a view.  We bind an observable to a callback for the duration of the view's lifecycle.
            this.lifecycle.bind(textObs){
                text = it
            }
        }

        textView(){
            styleDefault()

            //To write that out even more, the full version looks like this:

            //Set the initial value of the text ot the observable's value
            text = textObs.value

            //An observable is a mutable collection of lambdas.  The *listen* function simply adds an entry at the beginning of the lifecycle and removes it and the end of the lifecycle.
            this.lifecycle.listen(textObs){
                text = it
            }
        }

        //A progress button is a layout that transitions between a button and progress depending on the value of [running].
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

        //A transition view is used to transition between multiple views, usually with a fade effect.
        transitionView {

            //This makes a lambda that can animate height.  It is up for refactoring though, so this may change soon.
            val animateHeight = makeHeightAnimator(300, 0f)

            textView("Loading...") {
                gravity = Gravity.CENTER
            }.tag("loading") //Tags the view as "loading".  Now if animate("loading") is called, the view animates to this text view.

            textView("No image.") {
                gravity = Gravity.CENTER
            }.tag("none")

            imageView() {
                scaleType = ImageView.ScaleType.CENTER_CROP

                //This can be read as:
                //Right now and whenever imageObs changes, do what is within the curly braces.
                lifecycle.bind(imageObs) {

                    if (it == null) {
                        imageBitmap = null
                        animateHeight(null)
                        return@bind
                    }
                    imageBitmap = it
                    animateHeight(null)
                }
            }.tag("image")

            //This can be read as:
            //Right now and whenever loadingObs changes, do what is within the curly braces.
            lifecycle.bind(loadingObs) {
                if (it) {
                    animate("loading")
                }
            }

            //This can be read as:
            //Right now and whenever imageObs changes, do what is within the curly braces.
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