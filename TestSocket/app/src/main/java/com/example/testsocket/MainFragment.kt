package com.example.testsocket

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.testsocket.databinding.FragmentMainBinding
import com.example.testsocket.fastitems.cardChatItem
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch
import java.net.URISyntaxException

class MainFragment : Fragment(R.layout.fragment_main) {

    lateinit var binding: FragmentMainBinding
    val fastAdapter = GenericFastItemAdapter()
    val items = ArrayList<GenericItem>()
    var socket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            socket = IO.socket("http://192.168.86.140:3000")
        } catch (e: URISyntaxException) {
        }

        socket?.connect()
        socket?.emit("messageTel", "oueeeee telephone")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.recyclerView.adapter = fastAdapter
        fillRecyclerWithChat()
    }

    fun fillRecyclerWithChat() {

        socket?.on("displayChat") {

            Log.d("chat", it[0].toString() + " " + it[1].toString())
            items += cardChatItem {
                username = it[0].toString()
                message = it[1].toString()
            }
            lifecycleScope.launch {
                Log.d("chat", "refreshScreen")
                fastAdapter.setNewList(items)
            }
        }
    }
}