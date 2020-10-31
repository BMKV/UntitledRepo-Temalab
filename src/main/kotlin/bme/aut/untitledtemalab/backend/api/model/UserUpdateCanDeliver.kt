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
 * Data for changing deliverer status
 */
//@ApiModel(description = "Data for changing deliverer status")
@Validated
@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-10-25T11:28:44.269Z[GMT]")
class UserUpdateCanDeliver : UserUpdate {
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
     * The new value for the canDelivervariable
     * @return canDeliver
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The new value for the canDelivervariable")
    @JsonProperty("can-deliver")
    var isCanDeliver: Boolean? = null

    fun userId(userId: Long?): bme.aut.untitledtemalab.backend.api.model.UserUpdateCanDeliver {
        this.userId = userId
        return this
    }

    fun password(password: String?): bme.aut.untitledtemalab.backend.api.model.UserUpdateCanDeliver {
        this.password = password
        return this
    }

    fun canDeliver(canDeliver: Boolean?): bme.aut.untitledtemalab.backend.api.model.UserUpdateCanDeliver {
        isCanDeliver = canDeliver
        return this
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val userUpdateCanDeliver: bme.aut.untitledtemalab.backend.api.model.UserUpdateCanDeliver = o as bme.aut.untitledtemalab.backend.api.model.UserUpdateCanDeliver
        return userId == userUpdateCanDeliver.userId &&
                password == userUpdateCanDeliver.password &&
                isCanDeliver == userUpdateCanDeliver.isCanDeliver
    }

    override fun hashCode(): Int {
        return Objects.hash(userId, password, isCanDeliver)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class UserUpdateCanDeliver {\n")
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n")
        sb.append("    password: ").append(toIndentedString(password)).append("\n")
        sb.append("    canDeliver: ").append(toIndentedString(isCanDeliver)).append("\n")
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