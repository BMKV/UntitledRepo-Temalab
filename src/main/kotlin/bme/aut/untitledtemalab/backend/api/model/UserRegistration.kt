package bme.aut.untitledtemalab.backend.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.regex.Pattern

/**
 * Data on the new User
 */
class UserRegistration {
    /**
     * The email address of the new User
     * @return email
     */
    @JsonProperty("email")
    var email: String? = null

    /**
     * The password of the new User
     * @return password
     */
    @JsonProperty("password")
    var password: String? = null

    /**
     * The value determines if the new User can accept      Jobs
     * @return canDeliver
     */
    @JsonProperty("canDeliver")
    var canDeliver: Boolean? = null

    fun isValidBody(): Boolean {
        return !(email == null || password == null || canDeliver == null)
    }

    fun isValidEmailFormat(): Boolean {
        return if (email != null)
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email.toString()).matches()
        else false
    }
}