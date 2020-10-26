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
 * Data on the new User
 */
//@ApiModel(description = "Data on the new User")
@Validated
@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-10-25T11:28:44.269Z[GMT]")
class UserRegistration {
    /**
     * The email address of the new User
     * @return email
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The email address of the new User")
    @JsonProperty("email")
    var email: String? = null

    /**
     * The password of the new User
     * @return password
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The password of the new User")
    @JsonProperty("password")
    var password: String? = null

    /**
     * The value determines if the new User can accept      Jobs
     * @return canDeliver
     */
    //@get:ApiModelProperty(value = "The value determines if the new User can accept      Jobs")
    @JsonProperty("canDeliver")
    var isCanDeliver: Boolean? = null

    fun email(email: String?): UserRegistration {
        this.email = email
        return this
    }

    fun password(password: String?): UserRegistration {
        this.password = password
        return this
    }

    fun canDeliver(canDeliver: Boolean?): UserRegistration {
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
        val userRegistration = o as UserRegistration
        return email == userRegistration.email &&
                password == userRegistration.password &&
                isCanDeliver == userRegistration.isCanDeliver
    }

    override fun hashCode(): Int {
        return Objects.hash(email, password, isCanDeliver)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class UserRegistration {\n")
        sb.append("    email: ").append(toIndentedString(email)).append("\n")
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