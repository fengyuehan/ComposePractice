package github.leavesc.compose_chat.proxy.logic

import com.tencent.imsdk.v2.*
import github.leavesc.compose_chat.base.model.*
import github.leavesc.compose_chat.proxy.coroutineScope.ChatCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


internal interface Converters {

    companion object {

        private val chatCoroutineScope: CoroutineScope = ChatCoroutineScope()

    }

    val coroutineScope: CoroutineScope
        get() = chatCoroutineScope

    fun convertPersonProfile(timUserFullInfo: V2TIMUserFullInfo): PersonProfile {
        return PersonProfile(
            userId = timUserFullInfo.userID ?: "",
            nickname = timUserFullInfo.nickName ?: "",
            remark = timUserFullInfo.nickName ?: "",
            faceUrl = timUserFullInfo.faceUrl ?: "",
            signature = timUserFullInfo.selfSignature ?: ""
        )
    }

    fun convertFriendProfile(v2TIMFriendInfo: V2TIMFriendInfo): PersonProfile {
        return PersonProfile(
            userId = v2TIMFriendInfo.userID ?: "",
            nickname = v2TIMFriendInfo.userProfile?.nickName ?: "",
            remark = v2TIMFriendInfo.friendRemark ?: "",
            faceUrl = v2TIMFriendInfo.userProfile?.faceUrl ?: "",
            signature = v2TIMFriendInfo.userProfile?.selfSignature ?: ""
        )
    }

    fun convertFriendProfile(v2TIMFriendInfo: V2TIMFriendInfoResult): PersonProfile {
        return convertFriendProfile(v2TIMFriendInfo.friendInfo).copy(
            isFriend = v2TIMFriendInfo.relation == V2TIMFriendCheckResult.V2TIM_FRIEND_RELATION_TYPE_BOTH_WAY ||
                    v2TIMFriendInfo.relation == V2TIMFriendCheckResult.V2TIM_FRIEND_RELATION_TYPE_IN_MY_FRIEND_LIST
        )
    }

    fun convertGroupMember(
        memberFullInfo: V2TIMGroupMemberInfo
    ): GroupMemberProfile {
        val fullInfo = memberFullInfo as? V2TIMGroupMemberFullInfo
        val role = fullInfo?.role ?: -11111
        return GroupMemberProfile(
            userId = memberFullInfo.userID ?: "",
            faceUrl = memberFullInfo.faceUrl ?: "",
            nickname = memberFullInfo.nickName ?: "",
            remark = memberFullInfo.friendRemark ?: "",
            signature = "",
            role = convertRole(role),
            isOwner = role == V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_ROLE_OWNER,
            joinTime = (memberFullInfo as? V2TIMGroupMemberFullInfo)?.joinTime ?: 0,
        )
    }

    private fun convertRole(roleType: Int): String {
        return when (roleType) {
            V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_ROLE_MEMBER -> {
                "群成员"
            }
            V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_ROLE_ADMIN -> {
                "群管理员"
            }
            V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_ROLE_OWNER -> {
                "群主"
            }
            else -> {
                "unknown"
            }
        }
    }

    suspend fun deleteConversation(id: String): ActionResult {
        return suspendCancellableCoroutine { continuation ->
            V2TIMManager.getConversationManager().deleteConversation(
                id, object : V2TIMCallback {
                    override fun onSuccess() {
                        continuation.resume(ActionResult.Success)
                    }

                    override fun onError(code: Int, desc: String?) {
                        continuation.resume(ActionResult.Failed(desc ?: ""))
                    }
                })
        }
    }

    suspend fun deleteC2CConversation(userId: String): ActionResult {
        return deleteConversation(String.format("c2c_%s", userId))
    }

    suspend fun deleteGroupConversation(groupId: String): ActionResult {
        return deleteConversation(String.format("group_%s", groupId))
    }

    fun getConversationId(conversation: Conversation): String {
        return when (conversation) {
            is Conversation.C2CConversation -> {
                String.format("c2c_%s", conversation.id)
            }
            is Conversation.GroupConversation -> {
                String.format("group_%s", conversation.id)
            }
        }
    }

    fun convertMessage(messageList: List<V2TIMMessage>?): List<Message> {
        return messageList?.mapNotNull { convertMessage(it) } ?: emptyList()
    }

    fun convertMessage(timMessage: V2TIMMessage?): Message? {
        val groupId = timMessage?.groupID
        val userId = timMessage?.userID
        if (groupId.isNullOrBlank() && userId.isNullOrBlank()) {
            return null
        }
        return when (timMessage.elemType) {
            V2TIMMessage.V2TIM_ELEM_TYPE_TEXT -> {
                val senderProfile = PersonProfile(
                    userId = timMessage.sender,
                    faceUrl = timMessage.faceUrl ?: "",
                    nickname = timMessage.nickName ?: "",
                    remark = timMessage.friendRemark ?: "",
                    signature = ""
                )
                if (timMessage.isSelf) {
                    TextMessage.SelfTextMessage(
                        msgId = timMessage.msgID ?: "",
                        msg = timMessage.textElem?.text ?: "",
                        state = convertMessageState(timMessage.status),
                        timestamp = timMessage.timestamp,
                        sender = senderProfile
                    )
                } else {
                    TextMessage.FriendTextMessage(
                        msgId = timMessage.msgID ?: "",
                        msg = timMessage.textElem?.text ?: "",
                        timestamp = timMessage.timestamp,
                        sender = senderProfile
                    )
                }
            }
            else -> {
                null
            }
        }?.apply {
            tag = timMessage
        }
    }

    private fun convertMessageState(state: Int): MessageState {
        return when (state) {
            V2TIMMessage.V2TIM_MSG_STATUS_SENDING -> {
                MessageState.Sending
            }
            V2TIMMessage.V2TIM_MSG_STATUS_SEND_SUCC -> {
                MessageState.Completed
            }
            V2TIMMessage.V2TIM_MSG_STATUS_SEND_FAIL -> {
                MessageState.SendFailed
            }
            else -> {
                MessageState.Completed
            }
        }
    }

}