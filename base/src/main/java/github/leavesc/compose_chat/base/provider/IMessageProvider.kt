package github.leavesc.compose_chat.base.provider

import github.leavesc.compose_chat.base.model.Chat
import github.leavesc.compose_chat.base.model.LoadMessageResult
import github.leavesc.compose_chat.base.model.Message
import kotlinx.coroutines.channels.Channel


interface IMessageProvider {

    interface MessageListener {

        fun onReceiveMessage(message: Message)

    }

    fun startReceive(chat: Chat, messageListener: MessageListener)

    fun stopReceive(messageListener: MessageListener)

    suspend fun send(
        chat: Chat,
        text: String,
        channel: Channel<Message>
    )

    suspend fun getHistoryMessage(chat: Chat, lastMessage: Message?): LoadMessageResult

    fun markMessageAsRead(chat: Chat)

}