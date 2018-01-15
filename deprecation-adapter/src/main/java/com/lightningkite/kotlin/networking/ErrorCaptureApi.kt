package com.lightningkite.kotlin.networking

import com.lightningkite.kotlin.anko.async.UIThread
import com.lightningkite.kotlin.lambda.invokeAll
import java.util.*

/**
 * Used for capturing errors
 * Created by joseph on 11/11/16.
 */
@Deprecated("Just do it yourself.")
interface ErrorCaptureApi {

    val onError: ArrayList<(TypedResponse<*>) -> Unit>

    fun <T> (() -> TypedResponse<T>).captureError(): () -> TypedResponse<T> {
        return {
            val response = this.invoke()
            if (!response.isSuccessful()) {
                UIThread.execute {
                    onError.invokeAll(response)
                }
            }
            response
        }
    }
}