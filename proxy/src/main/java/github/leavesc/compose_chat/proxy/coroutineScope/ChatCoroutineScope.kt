package github.leavesc.compose_chat.proxy.coroutineScope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel


internal class ChatCoroutineScope : CoroutineScope {

    override val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate

    fun cancel() {
        coroutineContext.cancel()
    }

}