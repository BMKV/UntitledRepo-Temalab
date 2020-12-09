package bme.aut.untitledtemalab.backend.api.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A minimal information on a User
 */
class User(
        /**
         * The ID of the User
         * @return userId
         */
        @field:JsonProperty("user-id") var userId: Long,

        /**
         * The email address of the User
         * @return email
         */
        @field:JsonProperty("email") var email: String,

        /**
         * The rating of the User
         * minimum: 1
         * maximum: 5
         * @return rating$
         */
        @field:JsonProperty("rating") var rating: Float? = null
)
