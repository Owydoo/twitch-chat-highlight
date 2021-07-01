package com.entreprisecorp.messagereact

import android.app.Application
import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class ReactMessage : Application() {

    lateinit var socket: Socket

    override fun onCreate() {
        super.onCreate()
        try {
            socket = IO.socket("http://192.168.86.140:3000")
            Log.d("chat", "succeed")
            socket.connect()
            socket.emit("messageTel", "oueeeee telephone")
        } catch (e: URISyntaxException) {
            Log.d("chat", e.toString())
        }
    }
}