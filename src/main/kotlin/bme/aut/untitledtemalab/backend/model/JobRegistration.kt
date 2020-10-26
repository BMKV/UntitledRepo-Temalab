package bme.aut.untitledtemalab.backend.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
import org.springframework.validation.annotation.Validated
//import org.threeten.bp.OffsetDateTime
import java.util.*
import javax.annotation.Generated
//import javax.validation.Valid
//import javax.validation.constraints.*

//package io.swagger.model
//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
//import org.threeten.bp.OffsetDateTime
import java.time.OffsetDateTime
//import javax.validation.Valid
//import javax.validation.constraints.*

/**
 * Data on the new User
 */
//@ApiModel(description = "Data on the new User")
@Validated
@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-10-25T11:28:44.269Z[GMT]")
class JobRegistration {
    /**
     * The size of the package
     */
    enum class SizeEnum(private val value: String) {
        SMALL("small"), MEDIUM("medium"), LARGE("large");

        @JsonValue
        override fun toString(): String {
            return value
        }

        companion object {
            @JsonCreator
            fun fromValue(text: String): bme.aut.untitledtemalab.backend.model.JobRegistration.SizeEnum? {
                for (b in bme.aut.untitledtemalab.backend.model.JobRegistration.SizeEnum.values()) {
                    if (b.value.toString() == text) {
                        return b
                    }
                }
                return null
            }
        }
    }

    @JsonProperty("size")
    private var size: bme.aut.untitledtemalab.backend.model.JobRegistration.SizeEnum? = null

    /**
     * The payment for the delivery
     * @return payment
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The payment for the delivery")
    @JsonProperty("payment")
    var payment: Int? = null

    @JsonProperty("jobIssuedDate")
    private var jobIssuedDate: OffsetDateTime? = null

    @JsonProperty("deadline")
    private var deadline: OffsetDateTime? = null

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

    fun size(size: bme.aut.untitledtemalab.backend.model.JobRegistration.SizeEnum?): bme.aut.untitledtemalab.backend.model.JobRegistration {
        this.size = size
        return this
    }

    /**
     * The size of the package
     * @return size
     */
    //@ApiModelProperty(required = true, value = "The size of the package")
    //@NotNull
    fun getSize(): bme.aut.untitledtemalab.backend.model.JobRegistration.SizeEnum? {
        return size
    }

    fun setSize(size: bme.aut.untitledtemalab.backend.model.JobRegistration.SizeEnum?) {
        this.size = size
    }

    fun payment(payment: Int?): bme.aut.untitledtemalab.backend.model.JobRegistration {
        this.payment = payment
        return this
    }

    fun jobIssuedDate(jobIssuedDate: OffsetDateTime?): bme.aut.untitledtemalab.backend.model.JobRegistration {
        this.jobIssuedDate = jobIssuedDate
        return this
    }

    /**
     * The date the sender issued the Job
     * @return jobIssuedDate
     */
    //@ApiModelProperty(required = true, value = "The date the sender issued the Job")
    //@NotNull
    //@Valid
    fun getJobIssuedDate(): OffsetDateTime? {
        return jobIssuedDate
    }

    fun setJobIssuedDate(jobIssuedDate: OffsetDateTime?) {
        this.jobIssuedDate = jobIssuedDate
    }

    fun deadline(deadline: OffsetDateTime?): bme.aut.untitledtemalab.backend.model.JobRegistration {
        this.deadline = deadline
        return this
    }

    /**
     * The deadline of the Job
     * @return deadline
     */
    //@ApiModelProperty(required = true, value = "The deadline of the Job")
    //@NotNull
    //@Valid
    fun getDeadline(): OffsetDateTime? {
        return deadline
    }

    fun setDeadline(deadline: OffsetDateTime?) {
        this.deadline = deadline
    }

    fun startLocation(startLocation: String?): bme.aut.untitledtemalab.backend.model.JobRegistration {
        this.startLocation = startLocation
        return this
    }

    fun destination(destination: String?): bme.aut.untitledtemalab.backend.model.JobRegistration {
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
        val jobRegistration: bme.aut.untitledtemalab.backend.model.JobRegistration = o as bme.aut.untitledtemalab.backend.model.JobRegistration
        return size == jobRegistration.size &&
                payment == jobRegistration.payment &&
                jobIssuedDate == jobRegistration.jobIssuedDate &&
                deadline == jobRegistration.deadline &&
                startLocation == jobRegistration.startLocation &&
                destination == jobRegistration.destination
    }

    override fun hashCode(): Int {
        return Objects.hash(size, payment, jobIssuedDate, deadline, startLocation, destination)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class JobRegistration {\n")
        sb.append("    size: ").append(toIndentedString(size)).append("\n")
        sb.append("    payment: ").append(toIndentedString(payment)).append("\n")
        sb.append("    jobIssuedDate: ").append(toIndentedString(jobIssuedDate)).append("\n")
        sb.append("    deadline: ").append(toIndentedString(deadline)).append("\n")
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