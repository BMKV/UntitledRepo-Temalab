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
 * Data for email change
 */
//@ApiModel(description = "Data for email change")
@Validated
@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-10-25T11:28:44.269Z[GMT]")
class UserUpdateEmail : UserUpdate {
    /**
     * The ID of the User
     * @return userId
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The ID of the User")
    @JsonProperty("user-id")
    var userId: Long? = null

    /**
     * The password of the User
     * @return password
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The password of the User")
    @JsonProperty("password")
    var password: String? = null

    /**
     * The new email address of the User
     * @return newEmail
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The new email address of the User")
    @JsonProperty("new-email")
    var newEmail: String? = null

    fun userId(userId: Long?): UserUpdateEmail {
        this.userId = userId
        return this
    }

    fun password(password: String?): UserUpdateEmail {
        this.password = password
        return this
    }

    fun newEmail(newEmail: String?): UserUpdateEmail {
        this.newEmail = newEmail
        return this
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val userUpdateEmail = o as UserUpdateEmail
        return userId == userUpdateEmail.userId &&
                password == userUpdateEmail.password &&
                newEmail == userUpdateEmail.newEmail
    }

    override fun hashCode(): Int {
        return Objects.hash(userId, password, newEmail)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class UserUpdateEmail {\n")
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n")
        sb.append("    password: ").append(toIndentedString(password)).append("\n")
        sb.append("    newEmail: ").append(toIndentedString(newEmail)).append("\n")
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
