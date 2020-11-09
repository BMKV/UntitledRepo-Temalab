package bme.aut.untitledtemalab.backend.api.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A minimal information on a User
 */
class User(id: Long, email: String, rating: Float) {
    /**
     * The ID of the User
     * @return userId
     */
    @JsonProperty("user-id")
    var userId: Long? = id

    /**
     * The email address of the User
     * @return email
     */
    @JsonProperty("email")
    var email: String? = email

    /**
     * The rating of the User
     * minimum: 1
     * maximum: 5
     * @return rating
     */
    @JsonProperty("rating")
    var rating: Float? = rating
}
