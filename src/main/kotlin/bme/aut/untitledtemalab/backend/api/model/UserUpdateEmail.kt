package bme.aut.untitledtemalab.backend.api.model

import bme.aut.untitledtemalab.backend.database.model.Users
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data for email change
 */
class UserUpdateEmail : UserUpdate {
    /**
     * The ID of the User
     * @return userId
     */
    @JsonProperty("user-id")
    var userId: Long? = null

    /**
     * The password of the User
     * @return password
     */
    @JsonProperty("password")
    var password: String? = null

    /**
     * The new email address of the User
     * @return newEmail
     */
    @JsonProperty("new-email")
    var newEmail: String? = null

    override fun validatePassword(password: String): Boolean {
        return this.password == password
    }

    override fun updateUser(dbUser: Users): Users {
        if (newEmail != null && password == dbUser.password) {
            dbUser.emailAddress = newEmail as String
        }
        return dbUser
    }
}
