package bme.aut.untitledtemalab.backend.model

import com.fasterxml.jackson.annotation.JsonProperty
//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
import org.springframework.validation.annotation.Validated
import java.util.*
import javax.annotation.Generated
//import javax.validation.Valid
//import javax.validation.constraints.*


/**
 * Detailed information on a User
 */
//@ApiModel(description = "Detailed information on a User")
@Validated
@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-10-25T11:28:44.269Z[GMT]")
class UserProfile {
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

    /**
     * The boolean value which says if the User can deliver or not
     * @return canDeliver
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The boolean value which says if the User can deliver or not")
    @JsonProperty("canDeliver")
    var isCanDeliver: Boolean? = null

    /**
     * The actual size of the cargo
     * @return cargoFreeSize
     */
    //@get:ApiModelProperty(value = "The actual size of the cargo")
    @JsonProperty("cargoFreeSize")
    var cargoFreeSize: Int? = null

    /**
     * The maximal size of the cargo
     * @return cargoMaxSize
     */
    //@get:ApiModelProperty(value = "The maximal size of the cargo")
    @JsonProperty("cargoMaxSize")
    var cargoMaxSize: Int? = null

    fun userId(userId: Long?): UserProfile {
        this.userId = userId
        return this
    }

    fun email(email: String?): UserProfile {
        this.email = email
        return this
    }

    fun rating(rating: Float?): UserProfile {
        this.rating = rating
        return this
    }

    fun canDeliver(canDeliver: Boolean?): UserProfile {
        isCanDeliver = canDeliver
        return this
    }

    fun cargoFreeSize(cargoFreeSize: Int?): UserProfile {
        this.cargoFreeSize = cargoFreeSize
        return this
    }

    fun cargoMaxSize(cargoMaxSize: Int?): UserProfile {
        this.cargoMaxSize = cargoMaxSize
        return this
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val userProfile = o as UserProfile
        return userId == userProfile.userId &&
                email == userProfile.email &&
                rating == userProfile.rating &&
                isCanDeliver == userProfile.isCanDeliver &&
                cargoFreeSize == userProfile.cargoFreeSize &&
                cargoMaxSize == userProfile.cargoMaxSize
    }

    override fun hashCode(): Int {
        return Objects.hash(userId, email, rating, isCanDeliver, cargoFreeSize, cargoMaxSize)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class UserProfile {\n")
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n")
        sb.append("    email: ").append(toIndentedString(email)).append("\n")
        sb.append("    rating: ").append(toIndentedString(rating)).append("\n")
        sb.append("    canDeliver: ").append(toIndentedString(isCanDeliver)).append("\n")
        sb.append("    cargoFreeSize: ").append(toIndentedString(cargoFreeSize)).append("\n")
        sb.append("    cargoMaxSize: ").append(toIndentedString(cargoMaxSize)).append("\n")
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