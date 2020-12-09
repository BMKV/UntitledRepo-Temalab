package bme.aut.untitledtemalab.backend.api.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Detailed information on a User
 */
class UserProfile(

        /**
         * The ID of the User
         * @return userId
         */
        @field:JsonProperty("user-id")
        var userId: Long? = null,

        /**
         * The email address of the User
         * @return email
         */
        @field:JsonProperty("email")
        var email: String? = null,

        /**
         * The rating of the User
         * minimum: 1
         * maximum: 5
         * @return rating
         */
        @field:JsonProperty("rating")
        var rating: Float? = null,

        /**
         * The boolean value which says if the User can deliver or not
         * @return canDeliver
         */
        @field:JsonProperty("canDeliver")
        var canDeliver: Boolean? = null,

        @field:JsonProperty("isAdmin")
        var isAdmin: Boolean? = null,

        /**
         * The actual size of the cargo
         * @return cargoFreeSize
         */
        @field:JsonProperty("cargoFreeSize")
        var cargoFreeSize: Int? = null,

        /**
         * The maximal size of the cargo
         * @return cargoMaxSize
         */
        @field:JsonProperty("cargoMaxSize")
        var cargoMaxSize: Int? = null,
) {
    override fun equals(other: Any?): Boolean {
        other as UserProfile
        return other.userId == userId &&
                other.email == email &&
                other.rating == rating &&
                other.canDeliver == canDeliver &&
                other.isAdmin == isAdmin &&
                other.cargoFreeSize == cargoFreeSize &&
                other.cargoMaxSize == cargoMaxSize
    }

    override fun hashCode(): Int {
        var result = userId?.hashCode() ?: 0
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (rating?.hashCode() ?: 0)
        result = 31 * result + (canDeliver?.hashCode() ?: 0)
        result = 31 * result + (isAdmin?.hashCode() ?: 0)
        result = 31 * result + (cargoFreeSize ?: 0)
        result = 31 * result + (cargoMaxSize ?: 0)
        return result
    }
}