package bme.aut.untitledtemalab.backend.database

import bme.aut.untitledtemalab.backend.database.model.Jobs
import bme.aut.untitledtemalab.backend.database.model.Routes
import bme.aut.untitledtemalab.backend.database.model.Users
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import java.net.URISyntaxException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.*
import kotlin.random.Random

object DatabaseHandler {
    fun generateUID(): Long {
        return 1000000000 + Random.nextLong(8999999999)
    }

    fun validateUID(userId: Long): Boolean {
        return (userId in 1000000000..9999999999)
    }

    @PersistenceContext
    var em: EntityManager? = null

    fun createUser(user: Users)  {
        em?.persist(user)
    }

    fun updateUser(user: Users)  {
        em?.merge(user)
    }

    fun removeUser(user: Users)  {
        em?.remove(user)
    }

    fun createRoute(route: Routes)  {
        em?.persist(route)
    }

    fun updateRoute(route: Routes)  {
        em?.merge(route)
    }

    fun createJob(job: Jobs)  {
        em?.persist(job)
    }

    fun removeJob(job: Jobs) {
        em?.remove(job)
    }

    fun updateJob(job: Jobs)  {
        em?.merge(job)
    }

    fun existsByEmail(email: String?): Boolean {
        val cb: CriteriaBuilder = em!!.criteriaBuilder
        val query = cb.createQuery(Users::class.java)

        val user = query.from(Users::class.java)

        val emailPath: Path<String> = user.get("email")

        query.select(user)
                .where(cb.like(emailPath, email))

        val possibleUserList = em!!.createQuery(query).resultList

        return possibleUserList.isNotEmpty()
    }

    @Deprecated("Old JDBC simple code")
    @Throws(URISyntaxException::class, SQLException::class)
    private fun getConnection(): Connection? {
        val dbUrl = System.getenv("JDBC_DATABASE_URL")
        return DriverManager.getConnection(dbUrl)
    }

    @Deprecated("Old JDBC simple code")
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