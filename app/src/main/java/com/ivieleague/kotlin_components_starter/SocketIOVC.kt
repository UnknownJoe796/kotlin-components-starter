package com.ivieleague.kotlin_components_starter

import android.content.res.Resources
import android.view.View
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.lightningkite.kotlincomponents.observable.KObservable
import com.lightningkite.kotlincomponents.observable.bindString
import com.lightningkite.kotlincomponents.verticalLayout
import com.lightningkite.kotlincomponents.viewcontroller.ViewController
import com.lightningkite.kotlincomponents.viewcontroller.containers.VCStack
import com.lightningkite.kotlincomponents.viewcontroller.implementations.VCActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textView

/**
 * A ViewController that allows the user to test out SocketIO
 * Created by jivie on 1/14/16.
 */
class SocketIOVC(val stack: VCStack) : ViewController {

    override fun getTitle(resources: Resources): String = "SocketIO"

    val statusKObservable: KObservable<String> = KObservable("Not created.")
    var status: String by statusKObservable

    val messagesKObservable: KObservable<String> = KObservable("")
    var messages: String by messagesKObservable

    var socket: Socket? = null

    override fun make(activity: VCActivity): View {
        return verticalLayout(activity) {
            button("Start Socket IO") {
                onClick {
                    startSocket()
                }
            }
            button("Send Test Data") {
                onClick {
                    socket?.emit("ping", "This is a test message.")
                }
            }
            textView("Status:")
            textView { bindString(statusKObservable) }
            button("Stop Socket IO") {
                onClick {
                    stopSocket()
                }
            }
            button("Back") {
                onClick {
                    stack.pop()
                }
            }
            textView("Messages:")
            textView { bindString(messagesKObservable) }
        }
    }

    override fun unmake(view: View) {
        stopSocket()
        super.unmake(view)
    }

    fun startSocket() {
        if (socket == null) {
            try {
                socket = IO.socket("http://10.0.2.2:3000");
                status = "Socket created."
                socket!!.onConnect {
                    status = "Socket connected."
                }
                socket!!.onConnectError {
                    status = "Socket connection error: " + it.toString()
                    println(it)
                }
                socket!!.on<Any>("ping") {
                    messages += it.toString() + "\n"
                }
                socket!!.connect()
            } catch(e: Exception) {
                e.printStackTrace()
                status = "Creation failed."
            }
        }
    }

    fun stopSocket() {
        if (socket != null) {
            status = "Socket disconnected."
            socket!!.off()
            socket!!.disconnect()
            socket = null
        }
    }
}