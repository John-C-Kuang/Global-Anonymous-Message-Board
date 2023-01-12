package server

import common.BasicService
import common.ServerFAILResponse
import common.ServerOKResponse
import common.ServerResponse
import java.lang.IllegalArgumentException
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Represents a version of the messaging app that supports the basic functionalities.
 */
class BasicServiceImpl(private val connection: Connection): BasicService {

    override fun sendText(data: String): ServerResponse {
        return try {
            if (data.length > 128 || data.isEmpty()) throw IllegalArgumentException("Bad input data")
            val preparedStatement = prepareSendQuery(data)
            preparedStatement.executeUpdate()
            ServerOKResponse(
                content = "successfully sent text",
                payload = null
            )
        } catch (e: Exception) {
            val msg = if (e is IllegalArgumentException) {
                e.message!!
            } else {
                failMessage("send text")
            }
            ServerFAILResponse(
                content = msg,
                payload = null
            )
        }
    }

    private fun prepareSendQuery(data: String): PreparedStatement {
        val query = "INSERT INTO MESSAGE(text, time) VALUES(?, ?)"
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setString(1, data)
        preparedStatement.setString(2, formatTime(ZonedDateTime.now()))
        return preparedStatement
    }

    override fun recvPrevMessages(afterTime: ZonedDateTime): ServerResponse {
        return try {
            val query = "SELECT * FROM MESSAGE WHERE ? > time ORDER BY time DESC LIMIT $MESSAGE_FETCH_LIMIT"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setString(1, formatTime(afterTime))
            val parsedResult = parseDBValue(preparedStatement.executeQuery())
            ServerOKResponse(
                content = "successfully received text",
                payload = parsedResult
            )
        } catch (e: Exception) {
            ServerFAILResponse(
                content = failMessage("fetch earlier messages"),
                payload = null
            )
        }
    }

    private fun parseDBValue(resultSet: ResultSet): List<MessageModel> {
        val parsedData = mutableListOf<MessageModel>()
        while(resultSet.next()) {
            parsedData.add(
                MessageModel(
                    text = resultSet.getString("text"),
                    time = LocalDateTime.parse(resultSet.getString("time"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                        .atZone(ZONE_ID)
                )
            )
        }
        return parsedData
    }

    override fun login(username: String, password: String): ServerResponse {
        return try {
            val query = "SELECT * FROM USER WHERE name = ? AND password = ?"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setString(1, username)
            preparedStatement.setString(2, password)
            val result = preparedStatement.executeQuery()
            if (result.fetchSize == 0) throw IllegalArgumentException("Incorrect login credentials")
            if (result.fetchSize > 1) throw IllegalArgumentException("An unknown error has occured")
            ServerOKResponse(
                content = "successfully logged in",
                payload = null
            )
        } catch (e: Exception) {
            // 1. Already logged in
            // 2. Incorrect credentials
            ServerFAILResponse(
                content = failMessage("something"),
                payload = null
            )
        }
    }

    override fun signup(username: String, password: String): ServerResponse {
        return try {
            val checkQuery = "SELECT * FROM USER WHERE name = ?"
            val checkPreparedStatemenet = connection.prepareStatement(checkQuery)
            checkPreparedStatemenet.setString(1, username)
            val checkResult = checkPreparedStatemenet.executeQuery()
            if (checkResult.fetchSize != 0) throw IllegalArgumentException("Username already used")

            val requestQuery = "INSERT INTO USER(name, password) VALUES(?, ?)"
            val requestPreparedStatement = connection.prepareStatement(requestQuery)
            requestPreparedStatement.setString(1, username)
            requestPreparedStatement.setString(1, username)
            requestPreparedStatement.executeQuery()

            ServerOKResponse(
                content = "Account successfully created",
                payload = null
            )
        } catch (e: Exception) {
            ServerFAILResponse(
                content = failMessage("something"),
                payload = null
            )
        }
    }

    private fun failMessage(functionality: String): String {
        return "Failed to perform $functionality. Please try again."
    }

    companion object {

        val ZONE_ID: ZoneId = ZoneId.of("America/New_York")
        const val MESSAGE_FETCH_LIMIT = 20
        fun formatTime(time: ZonedDateTime): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            return time.format(formatter)
        }
    }


}