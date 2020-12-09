package bme.aut.untitledtemalab.backend.api.model

import bme.aut.untitledtemalab.backend.api.security.encoder
import bme.aut.untitledtemalab.backend.database.model.Users
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data for changing deliverer status
 */
class UserUpdateCanDeliver : UserUpdate {
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
     * The new value for the canDeliver variable
     * @return canDeliver
     */
    @JsonProperty("can-deliver")
    var isCanDeliver: Boolean? = null

    override fun validatePassword(password: String): Boolean {
        return encoder().matches(this.password, password)
    }

    override fun updateUser(dbUser: Users): Users {
        if (isCanDeliver != null && password == dbUser.password) {
            dbUser.canDeliver = isCanDeliver as Boolean
        }
        return dbUser
    }
}