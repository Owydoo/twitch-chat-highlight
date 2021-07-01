package com.entreprisecorp.messagereact.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.View
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

    lateinit var socket: Socket

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*try {
            socket = IO.socket("http://192.168.86.140:3000")
            Log.d("chat", "succeed")
            socket.connect()
            socket.emit("messageTel", "oueeeee telephone")
        } catch (e: URISyntaxException) {
            Log.d("chat", e.toString())
        }*/

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}