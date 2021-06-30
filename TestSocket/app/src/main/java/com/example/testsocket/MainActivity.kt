package com.example.testsocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testsocket.databinding.CardChatItemBinding
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class MainActivity : AppCompatActivity() {
    var socket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            socket = IO.socket("http://192.168.86.140:3000")
        } catch (e: URISyntaxException) {
        }

        socket?.connect()
        socket?.emit("messageTel", "oueeeee telephone")
    }
}