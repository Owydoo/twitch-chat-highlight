package com.entreprisecorp.data.impl

import com.entreprisecorp.data.ChatMessage
import com.entreprisecorp.data.model.MessageDataSource
import com.entreprisecorp.data.model.MessageRepository
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class MessageRepositoryImpl(
    ip: String,
    private val channelName: String
) : MessageRepository {

    private val socket: Socket by lazy {
        IO.socket(ip)
    }

    private val messageDataSource: MessageDataSource by lazy {
        SocketMessageDataSource(socket)
    }

    init {
        socket.connect()
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun onConnect(): Flow<Boolean> = flow {
        messageDataSource.onConnect().collect {
            this.emit(true)
            messageDataSource.sendChannelName(channelName)
        }

    }

    override fun sendChatToStream(chatMessage: ChatMessage) {
        messageDataSource.sendChatToStream(chatMessage)
    }

    override fun hideMessage() {
        messageDataSource.hideMessage()
    }

    override fun onReceiveMessage(): Flow<ChatMessage> = messageDataSource.onReceiveMessage()


    override fun onDisplayChat(): Flow<ChatMessage> = messageDataSource.onDisplayChat()

    override fun onHideChat(): Flow<Boolean> = messageDataSource.onHideChat()
}

