package server

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.SQLTimeoutException

fun main() {

    // Connect to DB
    val connection = connectToDB()

    // Spin up the server



}

/**
 * Connect the server to the SQLite database.
 */
@Throws(IllegalStateException::class)
fun connectToDB(): Connection {
    try {
        val path = "jdbc:sqlite:../GAMA.db"
        return  DriverManager.getConnection(path)
    } catch(e: Exception) {
        when (e) {
            is SQLTimeoutException -> throw IllegalStateException(e.message)
            is SQLException -> throw IllegalStateException(e.message)
            else -> throw IllegalStateException("An unknown exception happened! ${e.message}")
        }
    }
}