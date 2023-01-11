package common

import com.google.gson.JsonPrimitive
import java.util.*

/**
 * Represents a version of the messaging app that supports the basic functionalities.
 */
class SimpleGAMAServer: Server {

    override fun sendText(data: JsonPrimitive): ServerResponse {
        TODO("Not yet implemented")
    }

    override fun recvPrevMessages(afterMsgId: UUID): ServerResponse {
        TODO("Not yet implemented")
    }

    override fun login(username: JsonPrimitive, password: JsonPrimitive): ServerResponse {
        TODO("Not yet implemented")
    }

    override fun signup(username: JsonPrimitive, password: JsonPrimitive): ServerResponse {
        TODO("Not yet implemented")
    }

}