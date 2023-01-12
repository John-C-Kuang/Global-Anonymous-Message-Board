package server

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.SQLTimeoutException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun main() {

    // Connect to DB
    val connection = connectToDB()

    // Spin up the server and start to listen
    // Generate client proxy upon connection request


    // Playground

//    service.sendText("123455")
//    val zoneId = ZoneId.of("America/New_York")
//    val afterTime = LocalDateTime.parse("2023-01-12 15:10:37.570",
//        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")).atZone(zoneId)
//    val result = service.recvPrevMessages(ZonedDateTime.from(afterTime))
//    println(result.message)
//    for (i in result.payload!!) {
//        println(i)
//    }


}

/**
 * Connect the server to the SQLite database.
 */
@Throws(IllegalStateException::class)
fun connectToDB(): Connection {
    try {
        val path = "jdbc:sqlite:../GAMA.db"
        return DriverManager.getConnection(path)
    } catch(e: Exception) {
        when (e) {
            is SQLTimeoutException -> throw IllegalStateException(e.message)
            is SQLException -> throw IllegalStateException(e.message)
            else -> throw IllegalStateException("An unknown exception happened! ${e.message}")
        }
    }
}