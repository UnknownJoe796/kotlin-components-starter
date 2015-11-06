package com.ivieleague.kotlin_components_starter

import android.graphics.Bitmap
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.lightningkite.kotlincomponents.databinding.Bond
import com.lightningkite.kotlincomponents.image.getImageFromGallery
import com.lightningkite.kotlincomponents.logging.logE
import com.lightningkite.kotlincomponents.networking.Networking
import com.lightningkite.kotlincomponents.ui.inputDialog
import com.lightningkite.kotlincomponents.vertical
import com.lightningkite.kotlincomponents.viewcontroller.AutocleanViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import com.lightningkite.kotlincomponents.viewcontroller.implementations.dialog
import org.jetbrains.anko.*

/**
 * Created by josep on 11/6/2015.
 */
class StartVC(val stack: VCStack) : AutocleanViewController() {

    val imageBond: Bond<Bitmap?> = listener(Bond<Bitmap?>(null))
    var image: Bitmap? by imageBond

    override fun make(activity: VCActivity): View {

        return _LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setGravity(Gravity.CENTER)

            textView("Hello World") {
                setGravity(Gravity.CENTER)
            }

            button("Get an Image") {
                onClick {
                    activity.getImageFromGallery(1000) {
                        image = it
                    }
                }
            }

            button("Enter an image URL") {
                onClick {
                    activity.inputDialog("Enter an image URL", "URL") { url ->
                        if (url == null) return@inputDialog
                        Networking.get(url) {
                            if (it.isSuccessful) {
                                image = it.bitmap()
                            } else {
                                logE(url)
                                logE(it.code)
                                logE(it.string())
                            }
                        }
                    }
                }
            }

            imageView(R.mipmap.ic_launcher) {
                imageBond.bind {
                    if (it == null) return@bind
                    imageBitmap = it
                }
            }
        }
    }
}