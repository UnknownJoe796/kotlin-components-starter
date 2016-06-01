package com.ivieleague.kotlin.anko.networking.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ivieleague.kotlin.networking.NetStream

/**
 * Represents a response from the network.
 * It can only be read from once, as it really just holds a stream.  Don't screw it up.
 * Created by jivie on 9/23/15.
 */

fun NetStream.bitmap(options: BitmapFactory.Options = BitmapFactory.Options()): Bitmap? {
    val opts = BitmapFactory.Options()
    try {
        return readStream {
            BitmapFactory.decodeStream(it, null, opts)
        }
    } catch(e: Exception) {
        e.printStackTrace()
        return null
    }
}

fun NetStream.bitmapSized(minBytes: Long): Bitmap? {
    val opts = BitmapFactory.Options().apply {
        inSampleSize = (length / minBytes).toInt()
    }
    try {
        return readStream {
            BitmapFactory.decodeStream(it, null, opts)
        }
    } catch(e: Exception) {
        e.printStackTrace()
        return null
    }
}
