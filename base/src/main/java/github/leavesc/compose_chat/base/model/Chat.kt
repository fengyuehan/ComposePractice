package github.leavesc.compose_chat.base.model


sealed class Chat(val type: Int, val id: String) {

    companion object {

        fun find(type: Int, id: String): Chat {
            when (type) {
                1 -> {
                    return C2C(id)
                }
                2 -> {
                    return Group(id)
                }
            }
            throw IllegalArgumentException()
        }

    }

    class C2C(id: String) : Chat(1, id)

    class Group(id: String) : Chat(2, id)

}