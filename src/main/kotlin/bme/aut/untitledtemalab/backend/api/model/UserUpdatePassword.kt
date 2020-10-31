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
 * Data for password change
 */
//@ApiModel(description = "Data for password change")
@Validated
@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-10-25T11:28:44.269Z[GMT]")
class UserUpdatePassword : UserUpdate {
    /**
     * The ID of the User
     * @return userId
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The ID of the User")
    @JsonProperty("user-id")
    var userId: Long? = null

    /**
     * The current password of the User
     * @return currentPassword
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The current password of the User")
    @JsonProperty("current-password")
    var currentPassword: String? = null

    /**
     * The new password of the User
     * @return newPassword
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The new password of the User")
    @JsonProperty("new-password")
    var newPassword: String? = null

    fun userId(userId: Long?): UserUpdatePassword {
        this.userId = userId
        return this
    }

    fun currentPassword(currentPassword: String?): UserUpdatePassword {
        this.currentPassword = currentPassword
        return this
    }

    fun newPassword(newPassword: String?): UserUpdatePassword {
        this.newPassword = newPassword
        return this
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val userUpdatePassword = o as UserUpdatePassword
        return userId == userUpdatePassword.userId &&
                currentPassword == userUpdatePassword.currentPassword &&
                newPassword == userUpdatePassword.newPassword
    }

    override fun hashCode(): Int {
        return Objects.hash(userId, currentPassword, newPassword)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class UserUpdatePassword {\n")
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n")
        sb.append("    currentPassword: ").append(toIndentedString(currentPassword)).append("\n")
        sb.append("    newPassword: ").append(toIndentedString(newPassword)).append("\n")
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