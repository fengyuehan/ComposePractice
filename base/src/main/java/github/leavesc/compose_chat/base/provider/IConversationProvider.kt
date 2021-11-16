package github.leavesc.compose_chat.base.provider

import github.leavesc.compose_chat.base.model.ActionResult
import github.leavesc.compose_chat.base.model.Conversation
import kotlinx.coroutines.flow.StateFlow


interface IConversationProvider {

    val conversationList: StateFlow<List<Conversation>>

    val totalUnreadCount: StateFlow<Long>

    fun getConversationList()

    suspend fun pinConversation(conversation: Conversation, pin: Boolean): ActionResult

    suspend fun deleteConversation(conversation: Conversation): ActionResult

}