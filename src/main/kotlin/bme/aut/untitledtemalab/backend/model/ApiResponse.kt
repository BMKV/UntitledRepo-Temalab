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
 * Response of the backend
 */
//@ApiModel(description = "Response of the backend")
@Validated
@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-10-25T11:28:44.269Z[GMT]")
class ApiResponse {
    /**
     * Get code
     * @return code
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "")
    @JsonProperty("code")
    var code: Int? = null

    /**
     * Get type
     * @return type
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "")
    @JsonProperty("type")
    var type: String? = null

    /**
     * Get message
     * @return message
     */
    //@get:ApiModelProperty(value = "")
    @JsonProperty("message")
    var message: String? = null

    fun code(code: Int?): ApiResponse {
        this.code = code
        return this
    }

    fun type(type: String?): ApiResponse {
        this.type = type
        return this
    }

    fun message(message: String?): ApiResponse {
        this.message = message
        return this
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val _apiResponse = o as ApiResponse
        return code == _apiResponse.code &&
                type == _apiResponse.type &&
                message == _apiResponse.message
    }

    override fun hashCode(): Int {
        return Objects.hash(code, type, message)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class ModelApiResponse {\n")
        sb.append("    code: ").append(toIndentedString(code)).append("\n")
        sb.append("    type: ").append(toIndentedString(type)).append("\n")
        sb.append("    message: ").append(toIndentedString(message)).append("\n")
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
