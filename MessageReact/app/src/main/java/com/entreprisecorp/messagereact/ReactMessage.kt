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
            socket = IO.socket("http://89.87.13.28:3000")
            socket.connect()
        } catch (e: URISyntaxException) {
            Log.d("chat", e.toString())
        }
    }
}