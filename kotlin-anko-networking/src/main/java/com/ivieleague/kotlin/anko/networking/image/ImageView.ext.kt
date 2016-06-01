package com.ivieleague.kotlin.anko.networking.image

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.ivieleague.kotlin.anko.image.getBitmapFromUri
import com.ivieleague.kotlin.anko.isAttachedToWindowCompat
import com.ivieleague.kotlin.async.doAsync
import com.ivieleague.kotlin.networking.NetMethod
import com.ivieleague.kotlin.networking.NetRequest
import com.ivieleague.kotlin.networking.Networking
import org.jetbrains.anko.imageBitmap
import java.io.File
import java.util.*

/**
 *
 * Created by josep on 3/11/2016.
 *
 */


val ImageView_previousBitmap: MutableMap<ImageView, Bitmap> = HashMap()
val ImageView_previousListener: MutableMap<ImageView, View.OnAttachStateChangeListener> = HashMap()

inline fun ImageView.imageStream(url: String, minBytes: Long? = null, crossinline onResult: (Boolean) -> Unit)
        = imageStream(NetRequest(NetMethod.GET, url), minBytes, onResult)

inline fun ImageView.imageStream(request: NetRequest, minBytes: Long? = null, crossinline onResult: (Boolean) -> Unit) {
    doAsync({
        val stream = Networking.stream(request)
        if (stream.isSuccessful) {
            if (minBytes != null) {
                stream.bitmapSized(minBytes)
            } else {
                stream.bitmap()
            }
        } else {
            null
        }
    }, {
        if (it == null) {
            onResult(false)
        } else {
            if (!isAttachedToWindowCompat()) {
                it.recycle()
                return@doAsync
            }

            imageBitmap = it
            val newListener = object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View?) {
                    setImageDrawable(null)
                    it.recycle()
                    ImageView_previousBitmap.remove(this@imageStream)
                    removeOnAttachStateChangeListener(this)
                }

                override fun onViewAttachedToWindow(v: View?) {
                }
            }

            ImageView_previousBitmap[this]?.recycle()
            ImageView_previousBitmap[this] = it
            if (ImageView_previousListener[this] != null) {
                removeOnAttachStateChangeListener(ImageView_previousListener[this])
            }
            ImageView_previousListener[this] = newListener
            addOnAttachStateChangeListener(ImageView_previousListener[this])

            onResult(true)
        }
    })
}

fun ImageView.imageStreamExif(context: Context, request: NetRequest, maxDimension: Int = Int.MAX_VALUE, onResult: (Boolean) -> Unit) {
    val tempFile = File.createTempFile("image", "jpg", context.cacheDir)
    doAsync({
        val stream = Networking.stream(request)
        if (stream.isSuccessful) {
            stream.download(tempFile)
            context.getBitmapFromUri(Uri.fromFile(tempFile), maxDimension)
        } else {
            null
        }
    }, {
        if (it == null) {
            onResult(false)
        } else {
            if (!isAttachedToWindowCompat()) {
                it.recycle()
                try {
                    tempFile.delete()
                } catch(e: Exception) {/*Squish*/
                }
                return@doAsync
            }

            imageBitmap = it
            val newListener = object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View?) {
                    setImageDrawable(null)
                    it.recycle()
                    ImageView_previousBitmap.remove(this@imageStreamExif)
                    removeOnAttachStateChangeListener(this)
                    try {
                        tempFile.delete()
                    } catch(e: Exception) {/*Squish*/
                    }
                }

                override fun onViewAttachedToWindow(v: View?) {
                }
            }

            ImageView_previousBitmap[this]?.recycle()
            ImageView_previousBitmap[this] = it
            if (ImageView_previousListener[this] != null) {
                removeOnAttachStateChangeListener(ImageView_previousListener[this])
                try {
                    tempFile.delete()
                } catch(e: Exception) {/*Squish*/
                }
            }
            ImageView_previousListener[this] = newListener
            addOnAttachStateChangeListener(ImageView_previousListener[this])

            onResult(true)
        }
    })
}

enum class ImageLoadState {
    LOADING,
    NEW_IMAGE_LOADED,
    EXISTING_LOADED
}