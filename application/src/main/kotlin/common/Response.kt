package common

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive


/**
 * Represents the type of response.
 */
enum class ResponseType{
    OK, FAIL
}

/**
 * Response type of the server.
 * -
 * OK - The data is successfully processed by the server
 * FAIL - Some error happened in the server
 */
abstract class ServerResponse(
    val type: ResponseType,
    open val content: JsonElement
)

/**
 * Represents the response from the server when data is successfully processed.
 */
class ServerOKResponse(override val content: JsonElement): ServerResponse(
    type = ResponseType.OK,
    content = content
)

/**
 * Represents the response from the server when data failed to processed.
 */
class ServerFAILResponse(override val content: JsonElement): ServerResponse(
    type = ResponseType.FAIL,
    content = content
)
