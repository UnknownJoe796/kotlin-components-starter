package com.lightningkite.kotlin.anko.full

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.lightningkite.kotlin.anko.async.cancelling
import com.lightningkite.kotlin.anko.image.getBitmapFromUri
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.networking.image.lambdaBitmapExif
import com.lightningkite.kotlin.async.invokeAsync
import com.lightningkite.kotlin.observable.property.MutableObservableProperty
import com.lightningkite.kotlin.observable.property.ObservableProperty
import com.lightningkite.kotlin.observable.property.StandardObservableProperty
import com.lightningkite.kotlin.observable.property.bind
import okhttp3.Request
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.imageResource


@Deprecated("Use the image loader version instead.")
fun ImageView.bindUri(
        uriObservable: ObservableProperty<String?>,
        noImageResource: Int? = null,
        brokenImageResource: Int? = null,
        imageMinBytes: Long,
        requestBuilder: Request.Builder = Request.Builder(),
        loadingObs: MutableObservableProperty<Boolean> = StandardObservableProperty(false),
        onLoadComplete: (state: Int) -> Unit = {}
) {
    var lastUri: String? = "nomatch"
    lifecycle.bind(uriObservable) { uri ->
        if (lastUri == uri) return@bind
        lastUri = uri

        if (uri == null || uri.isEmpty()) {
            //set to default image
            if (noImageResource != null) {
                imageResource = noImageResource
            }
            onLoadComplete(0)
        } else {
            val uriObj = Uri.parse(uri)
            if (uriObj.scheme.contains("http")) {
                loadingObs.value = (true)
                post {
                    requestBuilder.url(uri).lambdaBitmapExif(context, imageMinBytes).cancelling(this).invokeAsync {
                        if (it == null) return@invokeAsync
                        loadingObs.value = (false)
                        if (it.result == null) {
                            //set to default image or broken image
                            if (brokenImageResource != null) {
                                imageResource = brokenImageResource
                            }
                            Log.e("ImageView.ext", "Error: " + it.errorString)
                            onLoadComplete(-1)
                        } else {
                            imageBitmap = it.result
                            onLoadComplete(1)
                        }
                    }
                }
            } else {
                try {
                    imageBitmap = context.getBitmapFromUri(Uri.parse(uri), 2048, 2048)!!
                    onLoadComplete(1)
                } catch (e: Exception) {
                    if (brokenImageResource != null) {
                        imageResource = brokenImageResource
                    }
                    Log.e("ImageView.ext", "Error: " + e.message)
                    e.printStackTrace()
                    onLoadComplete(-1)
                }
            }
        }
    }
}

@Deprecated("Use the image loader version instead.")
fun ImageView.bindUri(
        uriObservable: ObservableProperty<String?>,
        noImageResource: Int? = null,
        brokenImageResource: Int? = null,
        imageMaxWidth: Int = 2048,
        imageMaxHeight: Int = 2048,
        requestBuilder: Request.Builder = Request.Builder(),
        loadingObs: MutableObservableProperty<Boolean> = StandardObservableProperty(false),
        onLoadComplete: (state: Int) -> Unit = {}
) {
    var lastUri: String? = "nomatch"
    lifecycle.bind(uriObservable) { uri ->
        if (lastUri == uri) return@bind
        lastUri = uri

        if (uri == null || uri.isEmpty()) {
            //set to default image
            if (noImageResource != null) {
                imageResource = noImageResource
            }
            onLoadComplete(0)
        } else {
            val uriObj = Uri.parse(uri)
            if (uriObj.scheme?.contains("http") == true) {
                loadingObs.value = (true)
                post {
                    requestBuilder.url(uri).lambdaBitmapExif(context, imageMaxWidth, imageMaxHeight).cancelling(this).invokeAsync {
                        if (it == null) {
                            return@invokeAsync
                        }
                        loadingObs.value = (false)
                        if (it.result == null) {
                            //set to default image or broken image
                            if (brokenImageResource != null) {
                                imageResource = brokenImageResource
                            }
                            Log.e("ImageView.ext", "Error: " + it.errorString)
                            onLoadComplete(-1)
                        } else {
                            imageBitmap = it.result
                            onLoadComplete(1)
                        }
                    }
                }
            } else {
                try {
                    imageBitmap = context.getBitmapFromUri(Uri.parse(uri), imageMaxWidth, imageMaxHeight)!!
                    onLoadComplete(1)
                } catch (e: Exception) {
                    if (brokenImageResource != null) {
                        imageResource = brokenImageResource
                    }
                    Log.e("ImageView.ext", "Error: " + e.message)
                    e.printStackTrace()
                    onLoadComplete(-1)
                }
            }
        }
    }
}

@Deprecated("Use the image loader version instead.")
fun ImageView.bindUri(
        uriObservable: ObservableProperty<String?>,
        cache: MutableMap<String, Bitmap>,
        noImageResource: Int? = null,
        brokenImageResource: Int? = null,
        imageMinBytes: Long,
        requestBuilder: Request.Builder = Request.Builder(),
        loadingObs: MutableObservableProperty<Boolean> = StandardObservableProperty(false),
        onLoadComplete: (state: Int) -> Unit = {}
) {
    var lastUri: String? = "nomatch"
    lifecycle.bind(uriObservable) { uri ->
        if (lastUri == uri) return@bind
        lastUri = uri

        if (uri == null || uri.isEmpty()) {
            //set to default image
            if (noImageResource != null) {
                imageResource = noImageResource
            }
            onLoadComplete(0)
        } else if (cache.containsKey(uri)) {
            imageBitmap = cache[uri]!!
            onLoadComplete(1)
        } else {
            val uriObj = Uri.parse(uri)
            if (uriObj.scheme.contains("http")) {
                loadingObs.value = (true)
                post {
                    requestBuilder.url(uri).lambdaBitmapExif(context, imageMinBytes).cancelling(this).invokeAsync {
                        if (it == null) return@invokeAsync
                        loadingObs.value = (false)
                        if (it.result == null) {
                            //set to default image or broken image
                            if (brokenImageResource != null) {
                                imageResource = brokenImageResource
                            }
                            Log.e("ImageView.ext", "Error: " + it.errorString)
                            onLoadComplete(-1)
                        } else {
                            cache.put(uri, it.result!!)
                            imageBitmap = it.result
                            onLoadComplete(1)
                        }
                    }
                }
            } else {
                try {
                    imageBitmap = context.getBitmapFromUri(Uri.parse(uri), 2048, 2048)!!
                    onLoadComplete(1)
                } catch (e: Exception) {
                    if (brokenImageResource != null) {
                        imageResource = brokenImageResource
                    }
                    onLoadComplete(-1)
                    Log.e("ImageView.ext", "Error: " + e.message)
                    e.printStackTrace()
                }
            }
        }
    }
}

@Deprecated("Use the image loader version instead.")
fun ImageView.bindUri(
        uriObservable: ObservableProperty<String?>,
        cache: MutableMap<String, Bitmap>,
        noImageResource: Int? = null,
        brokenImageResource: Int? = null,
        imageMaxWidth: Int = 2048,
        imageMaxHeight: Int = 2048,
        requestBuilder: Request.Builder = Request.Builder(),
        loadingObs: MutableObservableProperty<Boolean> = StandardObservableProperty(false),
        onLoadComplete: (state: Int) -> Unit = {}
) {
    var lastUri: String? = "nomatch"
    lifecycle.bind(uriObservable) { uri ->
        if (lastUri == uri) return@bind
        lastUri = uri

        if (uri == null || uri.isEmpty()) {
            //set to default image
            if (noImageResource != null) {
                imageResource = noImageResource
            }
            onLoadComplete(0)
        } else if (cache.containsKey(uri)) {
            imageBitmap = cache[uri]!!
            onLoadComplete(1)
        } else {
            val uriObj = Uri.parse(uri)
            if (uriObj.scheme.contains("http")) {
                loadingObs.value = (true)
                post {
                    requestBuilder.url(uri).lambdaBitmapExif(context, imageMaxWidth, imageMaxHeight).cancelling(this).invokeAsync {
                        if (it == null) return@invokeAsync
                        loadingObs.value = (false)
                        if (it.result == null) {
                            //set to default image or broken image
                            if (brokenImageResource != null) {
                                imageResource = brokenImageResource
                            }
                            Log.e("ImageView.ext", "Error: " + it.errorString)
                            onLoadComplete(-1)
                        } else {
                            cache.put(uri, it.result!!)
                            imageBitmap = it.result
                            onLoadComplete(1)
                        }
                    }
                }
            } else {
                try {
                    imageBitmap = context.getBitmapFromUri(Uri.parse(uri), 2048, 2048)!!
                    onLoadComplete(1)
                } catch (e: Exception) {
                    if (brokenImageResource != null) {
                        imageResource = brokenImageResource
                    }
                    Log.e("ImageView.ext", "Error: " + e.message)
                    onLoadComplete(-1)
                }
            }
        }
    }
}