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
 * Information on the User&#x27;s cargo
 */
//@ApiModel(description = "Information on the User's cargo")
@Validated
@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-10-25T11:28:44.269Z[GMT]")
class UserCargo {
    /**
     * The actual size of the cargo
     * @return cargoFreeSize
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The actual size of the cargo")
    @JsonProperty("cargoFreeSize")
    var cargoFreeSize: Int? = null

    /**
     * The maximal size of the cargo
     * @return cargoMaxSize
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The maximal size of the cargo")
    @JsonProperty("cargoMaxSize")
    var cargoMaxSize: Int? = null

    fun cargoFreeSize(cargoFreeSize: Int?): UserCargo {
        this.cargoFreeSize = cargoFreeSize
        return this
    }

    fun cargoMaxSize(cargoMaxSize: Int?): UserCargo {
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
        val userCargo = o as UserCargo
        return cargoFreeSize == userCargo.cargoFreeSize &&
                cargoMaxSize == userCargo.cargoMaxSize
    }

    override fun hashCode(): Int {
        return Objects.hash(cargoFreeSize, cargoMaxSize)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class UserCargo {\n")
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
