package com.entreprisecorp.data.model

import com.entreprisecorp.data.ChatMessage
import kotlinx.coroutines.flow.Flow

interface MessageDataSource {
    fun onConnect(): Flow<Boolean>
    fun sendChannelName(channelName: String)
    fun sendChatToStream(chatMessage: ChatMessage)
    fun onReceiveMessage(): Flow<ChatMessage>
    fun hideMessage()
    fun onDisplayChat(): Flow<ChatMessage>
    fun onHideChat(): Flow<Boolean>
}