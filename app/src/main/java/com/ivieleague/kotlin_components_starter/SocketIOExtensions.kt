package com.lightningkite.kotlincomponents.socketio

import android.os.Handler
import android.os.Looper
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.lightningkite.kotlincomponents.async.Async
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by jivie on 1/14/16.
 */

inline fun Socket.on(event:String, crossinline action:()->Unit){
    on(event, { Async.handler.post{ action() } })
}

inline fun <reified A> Socket.on(event:String, crossinline action:(A)->Unit){
    on(event, { args ->
        if(args[0] !is A) throw IllegalArgumentException()
        Async.handler.post{ action(args[0] as A)}
    })
}

inline fun <reified A, reified B> Socket.on(event:String, crossinline action:(A, B)->Unit){
    on(event, { args ->
        if(args[0] !is A) throw IllegalArgumentException()
        if(args[1] !is B) throw IllegalArgumentException()
        Async.handler.post{ action(args[0] as A, args[1] as B)}
    })
}

inline fun <reified A, reified B, reified C> Socket.on(event:String, crossinline action:(A, B, C)->Unit){
    on(event, { args ->
        if(args[0] !is A) throw IllegalArgumentException()
        if(args[1] !is B) throw IllegalArgumentException()
        if(args[2] !is C) throw IllegalArgumentException()
        Async.handler.post{ action(args[0] as A, args[1] as B, args[2] as C)}
    })
}

inline fun <reified A, reified B, reified C, reified D> Socket.on(event:String, crossinline action:(A, B, C, D)->Unit){
    on(event, { args ->
        if(args[0] !is A) throw IllegalArgumentException()
        if(args[1] !is B) throw IllegalArgumentException()
        if(args[2] !is C) throw IllegalArgumentException()
        if(args[3] !is D) throw IllegalArgumentException()
        Async.handler.post{ action(args[0] as A, args[1] as B, args[2] as C, args[3] as D)}
    })
}

inline fun Socket.onConnect(crossinline action:()->Unit) = on(Socket.EVENT_CONNECT, action)
inline fun Socket.onConnectError(crossinline action:(error:Any)->Unit) = on<Any>(Socket.EVENT_CONNECT_ERROR, action)
inline fun Socket.onDisconnect(crossinline action:()->Unit) = on(Socket.EVENT_DISCONNECT, action)
inline fun Socket.onReconnect(crossinline action:(attempts:Int)->Unit) = on(Socket.EVENT_RECONNECT, action)
inline fun Socket.onError(crossinline action:(error:JSONObject)->Unit) = on(Socket.EVENT_ERROR, action)
/*
var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var bodyParser = require('body-parser');
app.use(bodyParser.json());

app.get('/', function(req, res) {
    res.send('<h1>Hello World</h1>');
});

http.listen(3000, function() {
    console.log('listening on *:3000');
});

io.on('connection', function(socket){
    socket.on('ping', function(msg){
        console.log('reflecting: ' + msg);
        io.emit('some event', { for: 'everyone' });
        console.log('reflected');
    });
});
*/