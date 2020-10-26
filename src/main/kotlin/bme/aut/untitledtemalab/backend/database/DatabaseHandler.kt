package bme.aut.untitledtemalab.backend.database

import java.net.URISyntaxException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

class DatabaseHandler {


    @Throws(URISyntaxException::class, SQLException::class)
    private fun getConnection(): Connection? {
        val dbUrl = System.getenv("JDBC_DATABASE_URL")
        return DriverManager.getConnection(dbUrl)
    }

    fun executeSQL(query: String) {
        var conn: Connection? = null
        var stmt: Statement? = null
        try {
            conn = getConnection()
            if (conn == null)
                throw Exception("Can't connect to database!")

            stmt = conn.createStatement()

            val rs = stmt.executeQuery(query)

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                val id = rs.getInt("id")
                val title = rs.getInt("title")
                val tags = rs.getString("tags")
                val newsDate = rs.getString("newsDate")
                val author = rs.getString("author")
                val newsData = rs.getString("newsData")

            }
            rs.close()
        } catch (se: SQLException) {
            //Handle errors for JDBC
            se.printStackTrace()
        } catch (e: java.lang.Exception) {
            //Handle errors for Class.forName
            e.printStackTrace()
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) conn!!.close()
            } catch (se: SQLException) {
            } // do nothing
            try {
                conn?.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            } //end finally try
        } //end try
    }

}