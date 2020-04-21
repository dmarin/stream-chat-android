package io.getstream.chat.android.livedata.usecase

import io.getstream.chat.android.livedata.Call2
import io.getstream.chat.android.livedata.CallImpl2
import io.getstream.chat.android.livedata.ChatDomain
import java.security.InvalidParameterException

class StopTyping(var domain: ChatDomain) {
    operator fun invoke (cid: String): Call2<Boolean> {
        if (cid.isEmpty()) {
            throw InvalidParameterException("cid cant be empty")
        }

        val channelRepo = domain.channel(cid)

        var runnable = suspend {

            channelRepo.stopTyping()
        }
        return CallImpl2<Boolean>(runnable, channelRepo.scope)
    }
}