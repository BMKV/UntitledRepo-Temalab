package bme.aut.untitledtemalab.backend.api.model

import bme.aut.untitledtemalab.backend.api.security.encoder
import bme.aut.untitledtemalab.backend.database.model.Users
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data for password change
 */
class UserUpdatePassword : UserUpdate {
    /**
     * The ID of the User
     * @return userId
     */
    @JsonProperty("user-id")
    var userId: Long? = null

    /**
     * The current password of the User
     * @return currentPassword
     */
    @JsonProperty("current-password")
    var currentPassword: String? = null

    /**
     * The new password of the User
     * @return newPassword
     */
    @JsonProperty("new-password")
    var newPassword: String? = null

    override fun validatePassword(password: String): Boolean {
        return encoder().matches(this.currentPassword, password)
    }

    override fun updateUser(dbUser: Users): Users {
        if (newPassword != null && !validatePassword(dbUser.password)) {
            dbUser.password = encoder().encode(newPassword)
        }
        return dbUser
    }
}