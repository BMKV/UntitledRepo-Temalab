package bme.aut.untitledtemalab.backend.api.model

import bme.aut.untitledtemalab.backend.database.model.Users
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data for cargo size change
 */
class UserUpdateCargoSize: UserUpdate {
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
     * The max cargo size of the User
     * @return newCargoMaxSize
     */
    @JsonProperty("new-cargo-size")
    var newCargoMaxSize: Int? = null

    override fun validatePassword(password: String): Boolean {
        return this.password == password
    }

    override fun updateUser(dbUser: Users): Users {
        TODO("Not yet implemented")
    }
}