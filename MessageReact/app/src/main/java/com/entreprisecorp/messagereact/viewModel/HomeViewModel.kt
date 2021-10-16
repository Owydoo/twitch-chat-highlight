package com.entreprisecorp.messagereact.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entreprisecorp.data.ChatMessage
import com.entreprisecorp.data.impl.MessageRepositoryImpl
import com.entreprisecorp.data.model.MessageRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var messageRepository: MessageRepository? = null

    private val _messages: MutableLiveData<ArrayList<ChatMessage>> = MutableLiveData()
    val messages: LiveData<ArrayList<ChatMessage>> = _messages

    private val _displayedMessage: MutableLiveData<ChatMessage?> = MutableLiveData()
    val displayedMessage: LiveData<ChatMessage?> = _displayedMessage

    private val _isConnected: MutableLiveData<Boolean> = MutableLiveData()
    val isConnected: LiveData<Boolean> = _isConnected

    fun initSocketRepository(ipAddress: String, channelName: String) {
        messageRepository?.disconnect()
        messageRepository = MessageRepositoryImpl(ipAddress, channelName)
        _displayedMessage.postValue(null)
        _messages.postValue(arrayListOf())
        collectChat()
        listenerDisplayChat()
        listenerHideChat()
        onConnect()
    }

    private fun onConnect() {
        viewModelScope.launch {
            messageRepository?.onConnect()?.collect {
                _isConnected.postValue(it)
            }
        }
    }

    fun sendChatToStream(chatMessage: ChatMessage) {
        messageRepository?.sendChatToStream(chatMessage)
    }

    private fun collectChat() {
        val messageList = ArrayList<ChatMessage>()
        viewModelScope.launch {
            messageRepository?.onReceiveMessage()?.collect {
                messageList += it
                _messages.postValue(messageList)
            }
        }
    }

    fun hideMessage() {
        messageRepository?.hideMessage()
    }

    private fun listenerDisplayChat() {
        viewModelScope.launch {
            messageRepository?.onDisplayChat()?.collect { message ->
                _displayedMessage.postValue(message)
            }
        }
    }

    private fun listenerHideChat() {
        viewModelScope.launch {
            messageRepository?.onHideChat()?.collect {
                _displayedMessage.postValue(null)
            }
        }
    }
}

