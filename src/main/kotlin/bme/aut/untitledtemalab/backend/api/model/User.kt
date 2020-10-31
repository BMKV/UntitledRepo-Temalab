package bme.aut.untitledtemalab.backend.api.model

import com.fasterxml.jackson.annotation.JsonProperty
//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
import org.springframework.validation.annotation.Validated
import java.util.*
import javax.annotation.Generated
//import javax.validation.Valid
//import javax.validation.constraints.*


/**
 * A minimal information on a User
 */
//@ApiModel(description = "A minimal information on a User")
@Validated
@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-10-25T11:28:44.269Z[GMT]")
class User {
    /**
     * The ID of the User
     * @return userId
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The ID of the User")
    @JsonProperty("user-id")
    var userId: Long? = null

    /**
     * The email address of the User
     * @return email
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The email address of the User")
    @JsonProperty("email")
    var email: String? = null

    /**
     * The rating of the User
     * minimum: 1
     * maximum: 5
     * @return rating
     */
    //@get:DecimalMax("5")
    //@get:DecimalMin("1")
    //@get:ApiModelProperty(value = "The rating of the User")
    @JsonProperty("rating")
    var rating: Float? = null

    fun userId(userId: Long?): User {
        this.userId = userId
        return this
    }

    fun email(email: String?): User {
        this.email = email
        return this
    }

    fun rating(rating: Float?): User {
        this.rating = rating
        return this
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val user = o as User
        return userId == user.userId &&
                email == user.email &&
                rating == user.rating
    }

    override fun hashCode(): Int {
        return Objects.hash(userId, email, rating)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class User {\n")
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n")
        sb.append("    email: ").append(toIndentedString(email)).append("\n")
        sb.append("    rating: ").append(toIndentedString(rating)).append("\n")
        sb.append("}")
        return sb.toString()
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private fun toIndentedString(o: Any?): String {
        return o?.toString()?.replace("\n", "\n    ") ?: "null"
    }
}
