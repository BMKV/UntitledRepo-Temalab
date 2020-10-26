package bme.aut.untitledtemalab.backend.model

import com.fasterxml.jackson.annotation.JsonProperty
//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
import org.springframework.validation.annotation.Validated
import java.util.*
import javax.annotation.Generated
//import javax.validation.Valid
//import javax.validation.constraints.*

//package io.swagger.model
//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
//import javax.validation.Valid
//import javax.validation.constraints.*

/**
 * A Route for a specific Job
 */
//@ApiModel(description = "A Route for a specific Job")
@Validated
@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-10-25T11:28:44.269Z[GMT]")
class Route {
    /**
     * The time the Route was completed
     * @return actualTime
     */
    //@get:ApiModelProperty(value = "The time the Route was completed")
    @JsonProperty("actualTime")
    var actualTime: Long? = null

    /**
     * The optimal time for the Route
     * @return optimalTime
     */
    //@get:ApiModelProperty(value = "The optimal time for the Route")
    @JsonProperty("optimalTime")
    var optimalTime: Long? = null

    /**
     * The start location of the Route
     * @return startLocation
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The start location of the Route")
    @JsonProperty("startLocation")
    var startLocation: String? = null

    /**
     * The destination of the Route
     * @return destination
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The destination of the Route")
    @JsonProperty("destination")
    var destination: String? = null

    fun actualTime(actualTime: Long?): bme.aut.untitledtemalab.backend.model.Route {
        this.actualTime = actualTime
        return this
    }

    fun optimalTime(optimalTime: Long?): bme.aut.untitledtemalab.backend.model.Route {
        this.optimalTime = optimalTime
        return this
    }

    fun startLocation(startLocation: String?): bme.aut.untitledtemalab.backend.model.Route {
        this.startLocation = startLocation
        return this
    }

    fun destination(destination: String?): bme.aut.untitledtemalab.backend.model.Route {
        this.destination = destination
        return this
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val route: bme.aut.untitledtemalab.backend.model.Route = o as bme.aut.untitledtemalab.backend.model.Route
        return actualTime == route.actualTime &&
                optimalTime == route.optimalTime &&
                startLocation == route.startLocation &&
                destination == route.destination
    }

    override fun hashCode(): Int {
        return Objects.hash(actualTime, optimalTime, startLocation, destination)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class Route {\n")
        sb.append("    actualTime: ").append(toIndentedString(actualTime)).append("\n")
        sb.append("    optimalTime: ").append(toIndentedString(optimalTime)).append("\n")
        sb.append("    startLocation: ").append(toIndentedString(startLocation)).append("\n")
        sb.append("    destination: ").append(toIndentedString(destination)).append("\n")
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