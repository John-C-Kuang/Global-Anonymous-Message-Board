package common


import java.time.ZonedDateTime

interface BasicService {

    /**
     * Send text to the server. This method only accepts JSON String. Any different JSONPrimitive input will result in a FAIL response.
     * The data argument must be non-empty and at most 128 characters long.
     * If the text is successfully processed by the server, this method returns an OK ServerResponse.
     * Otherwise, this method responds with a FAIL ServerResponse.
     * This method will always return FAIL if the user is not logged in.
     * @param data text to be sent
     * @return a ServerResponse
     */
    fun sendText(data: String): ServerResponse

    /**
     * Receive previous messages from the server. Fetch the most recent 10 messages since the given message id.
     * This method will always return FAIL if the user is not logged in.
     * @param afterMsgId fetch the message after this message id
     * @return a ServerResponse containing the messages
     */
    fun recvPrevMessages(afterMsgId: ZonedDateTime): ServerResponse

    /**
     * Allow a user to login using the correct username and password. Both arguments must be JSON String.
     * password is hashed by SHA256.
     * @param username the username
     * @param password the password associated with the username
     * @return a ServerResponse representing the result of this action.
     */
    fun login(username: String, password: String): ServerResponse

    /**
     * Allow a user to sign up with the provided username and password.
     * username must be unique globally.
     */
    fun signup(username: String, password: String): ServerResponse
}