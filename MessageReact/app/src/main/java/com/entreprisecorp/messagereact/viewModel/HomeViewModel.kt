package com.entreprisecorp.messagereact.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entreprisecorp.messagereact.ChatMessage
import io.socket.client.Socket

class HomeViewModel : ViewModel() {

    val messages: MutableLiveData<ArrayList<ChatMessage>> =
        MutableLiveData<ArrayList<ChatMessage>>()

    val displayedMessage: MutableLiveData<ChatMessage?> = MutableLiveData<ChatMessage?>()

    private lateinit var socket: Socket

    fun initSocketListener(socket: Socket) {
        this.socket = socket
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
            messageList += ChatMessage(it[0].toString(), it[1].toString())
            messages.postValue(messageList)
        }
    }

    fun hideMessage() {
        socket.emit("hideChat")
    }

    private fun listenerDisplayChat() {
        socket.on("displayChat") {
            displayedMessage.postValue(ChatMessage(it[0].toString(), it[1].toString()))
        }
    }

    private fun listenerHideMessage() {
        socket.on("hideChat") {
            displayedMessage.postValue(null)
        }
    }
}