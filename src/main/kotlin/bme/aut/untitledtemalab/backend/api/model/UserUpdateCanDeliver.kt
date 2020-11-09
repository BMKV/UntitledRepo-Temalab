package bme.aut.untitledtemalab.backend.api.model

import bme.aut.untitledtemalab.backend.database.model.Users
import com.fasterxml.jackson.annotation.JsonProperty
//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
import javax.annotation.Generated
//import javax.validation.Valid
//import javax.validation.constraints.*


/**
 * Data for changing deliverer status
 */
class UserUpdateCanDeliver: UserUpdate {
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
        return this.password == password
    }

    override fun updateUser(dbUser: Users): Users {
        TODO("Not yet implemented")
    }
}