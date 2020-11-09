package bme.aut.untitledtemalab.backend.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.validation.annotation.Validated
import javax.annotation.Generated


/**
 * Response of the backend
 */
class ApiResponse {
    /**
     * Get code
     * @return code
     */
    @JsonProperty("code")
    var code: Int? = null

    /**
     * Get type
     * @return type
     */
    @JsonProperty("type")
    var type: String? = null

    /**
     * Get message
     * @return message
     */
    @JsonProperty("message")
    var message: String? = null
}
