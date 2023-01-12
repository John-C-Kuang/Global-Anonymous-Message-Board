package common

import server.MessageModel


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
    val message: String,
    open val payload: List<MessageModel>? = null
)

/**
 * Represents the response from the server when data is successfully processed.
 */
class ServerOKResponse(val content: String, override val payload: List<MessageModel>?): ServerResponse(
    type = ResponseType.OK,
    message = content,
    payload = payload
)

/**
 * Represents the response from the server when data failed to processed.
 */
class ServerFAILResponse(val content: String, override val payload: List<MessageModel>?): ServerResponse(
    type = ResponseType.FAIL,
    message = content,
    payload = payload
)
