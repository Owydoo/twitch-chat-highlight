package com.entreprisecorp.messagereact.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.entreprisecorp.messagereact.ChatMessage
import com.entreprisecorp.messagereact.R
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.launch
import java.net.URISyntaxException

class MainActivity : AppCompatActivity() {

    var socket: Socket? = null

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            socket = IO.socket("http://192.168.86.140:3000")
        } catch (e: URISyntaxException) {
        }

        socket?.connect()
        socket?.emit("messageTel", "oueeeee telephone")

        getMessages()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun getMessages() {
        socket?.on("displayChat") {
            Log.d("chat", it[0].toString() + " " + it[1].toString())
            listMessage += ChatMessage(it[0].toString(),it[1].toString())
        }
    }


    companion object {
        val listMessage = mutableListOf<ChatMessage>()
    }
}