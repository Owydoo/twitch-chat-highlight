package com.entreprisecorp.data.impl

import com.entreprisecorp.data.ChatMessage
import com.entreprisecorp.data.model.MessageDataSource
import io.socket.client.Socket
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

enum class Event(val event: String) {
    ON_CONNECT("connected"),
    SEND_CHANNEL_NAME("sendChannelName"),
    SEND_MESSAGE_TO_LIVE("sendMessageToLive"),
    COLLECT_MESSAGE("sendChat"),
    HIDE_CHAT("hideChat"),
    ON_DISPLAY_CHAT("displayChat"),
    ON_HIDE_CHAT("hideChat"),
}

class SocketMessageDataSource(
    private val socket: Socket
) : MessageDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onConnect(): Flow<Boolean> = callbackFlow {
        socket.on(Event.ON_CONNECT.event) {
            this.trySend(true)
        }
        awaitClose()
    }

    override fun sendChannelName(channelName: String) {
        socket.emit(Event.SEND_CHANNEL_NAME.event, channelName)
    }

    override fun sendChatToStream(chatMessage: ChatMessage) {
        socket.emit(Event.SEND_MESSAGE_TO_LIVE.event, chatMessage.username, chatMessage.message)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onReceiveMessage(): Flow<ChatMessage> = callbackFlow {
        socket.on(Event.COLLECT_MESSAGE.event) {
            val receivedMessage = ChatMessage(it[0].toString(), it[1].toString())
            trySend(receivedMessage)
        }
        awaitClose()
    }

    override fun hideMessage() {
        socket.emit(Event.HIDE_CHAT.event)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onDisplayChat(): Flow<ChatMessage> = callbackFlow {
        socket.on(Event.ON_DISPLAY_CHAT.event) {
            val receivedMessage = ChatMessage(it[0].toString(), it[1].toString())
            trySend(receivedMessage)
        }
        awaitClose()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onHideChat(): Flow<Boolean> = callbackFlow {
        socket.on(Event.ON_HIDE_CHAT.event) {
            trySend(true)
        }
        awaitClose()
    }
}