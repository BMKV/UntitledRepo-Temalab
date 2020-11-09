package bme.aut.untitledtemalab.backend.api.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Detailed information on a User
 */
class UserProfile(id: Long, email: String, rating: Float, canDeliver: Boolean, freeSize: Int, maxSize: Int) {
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

    /**
     * The boolean value which says if the User can deliver or not
     * @return canDeliver
     */
    @JsonProperty("canDeliver")
    var canDeliver: Boolean? = canDeliver

    /**
     * The actual size of the cargo
     * @return cargoFreeSize
     */
    @JsonProperty("cargoFreeSize")
    var cargoFreeSize: Int? = freeSize

    /**
     * The maximal size of the cargo
     * @return cargoMaxSize
     */
    @JsonProperty("cargoMaxSize")
    var cargoMaxSize: Int? = maxSize
}