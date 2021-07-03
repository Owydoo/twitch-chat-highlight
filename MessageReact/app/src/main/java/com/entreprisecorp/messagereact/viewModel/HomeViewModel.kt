package com.entreprisecorp.messagereact.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.entreprisecorp.messagereact.ChatMessage
import com.entreprisecorp.messagereact.ReactMessage
import com.entreprisecorp.messagereact.main.MainActivity
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.launch
import java.net.URISyntaxException

class HomeViewModel(application: ReactMessage) : ViewModel() {

    val messages: MutableLiveData<ArrayList<ChatMessage>> =
        MutableLiveData<ArrayList<ChatMessage>>()

    val displayedMessage: MutableLiveData<ChatMessage?> = MutableLiveData<ChatMessage?>()

    private val socket = application.socket

    init {
        collectChat()
        listenerHideMessage()
        displayedMessage.value = null
        listenerDisplayChat()
    }

    fun sendChatToStream(chatMessage: ChatMessage) {
        socket.emit("sendMessageToLive", chatMessage.username, chatMessage.message)
    }

    private fun collectChat() {
        val messageList = ArrayList<ChatMessage>()
        socket.on("sendChat") {
            Log.d("chat", it[0].toString() + " " + it[1].toString())
            messageList += ChatMessage(it[0].toString(), it[1].toString())
            viewModelScope.launch {
                messages.value = messageList
            }
        }
    }

    fun hideMessage() {
        socket.emit("hideChat")
    }

    private fun listenerDisplayChat() {
        socket.on("displayChat") {
            viewModelScope.launch {
                displayedMessage.value = ChatMessage(it[0].toString(), it[1].toString())
            }
        }
    }

    private fun listenerHideMessage() {
        socket.on("hideChat") {
            viewModelScope.launch {
                displayedMessage.value = null
            }
        }
    }
}

class HomeViewModelFactory(
    private val application: ReactMessage
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(
            application
        ) as T
    }
}