package server

import com.google.gson.Gson
import java.time.ZonedDateTime

data class MessageModel(
    val text: String,
    val time: ZonedDateTime
) {

    /**
     * Serialize this message to its JSON representation.
     */
    fun serialize(): String {
        return Gson().toJson(this)
    }
}
