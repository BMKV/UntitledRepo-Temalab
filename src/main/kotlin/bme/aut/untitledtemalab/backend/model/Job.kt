package bme.aut.untitledtemalab.backend.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
//import com.jayway.jsonpath.internal.function.numeric.Max
//import com.jayway.jsonpath.internal.function.numeric.Min
import org.springframework.util.RouteMatcher.Route
import java.time.OffsetDateTime
import java.util.*
import javax.annotation.Generated


/**
 * Detailed information on a Job
 */
//@ApiModel(description = "Detailed information on a Job")
//@Validated
@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-10-25T11:28:44.269Z[GMT]")
class Job {
    /**
     * The ID of the Job
     * @return jobId
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The ID of the Job")
    @JsonProperty("job-id")
    var jobId: Long? = null

    /**
     * The current status of the Job
     */
    enum class StatusEnum(private val value: String) {
        PENDING("pending"), ACCEPTED("accepted"), PICKEDUP("pickedUp"), DELIVERED("delivered");

        @JsonValue
        override fun toString(): String {
            return value
        }

        companion object {
            @JsonCreator
            fun fromValue(text: String): StatusEnum? {
                for (b in values()) {
                    if (b.value == text) {
                        return b
                    }
                }
                return null
            }
        }
    }

    /**
     * The current status of the Job
     * @return status
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The current status of the Job")
    @JsonProperty("status")
    var status: StatusEnum? = null

    /**
     * The sender rating of the delivery
     * minimum: 1
     * maximum: 5
     * @return senderRating
     */
    //@get:Max(5)
    //@get:Min(1)
    //@get:ApiModelProperty(value = "The sender rating of the delivery")
    @JsonProperty("senderRating")
    var senderRating: Int? = null

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
            fun fromValue(text: String): SizeEnum? {
                for (b in values()) {
                    if (b.value == text) {
                        return b
                    }
                }
                return null
            }
        }
    }

    /**
     * The size of the package
     * @return size
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The size of the package")
    @JsonProperty("size")
    var size: SizeEnum? = null

    /**
     * The payment for the delivery
     * @return payment
     */
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The payment for the delivery")
    @JsonProperty("payment")
    var payment: Int? = null

    /**
     * The date the sender issued the Job
     * @return jobIssuedDate
     */
    //@get:Valid
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The date the sender issued the Job")
    @JsonProperty("jobIssuedDate")
    var jobIssuedDate: OffsetDateTime? = null

    /**
     * The deadline of the Job
     * @return deadline
     */
    //@get:Valid
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "The deadline of the Job")
    @JsonProperty("deadline")
    var deadline: OffsetDateTime? = null

    /**
     * The day the package was delivered
     * @return deliveryDate
     */
    //@get:Valid
    //@get:ApiModelProperty(value = "The day the package was delivered")
    @JsonProperty("deliveryDate")
    var deliveryDate: OffsetDateTime? = null

    /**
     * Get deliveryRoute
     * @return deliveryRoute
     */
    //@get:Valid
    //@get:NotNull
    //@get:ApiModelProperty(required = true, value = "")
    @JsonProperty("deliveryRoute")
    var deliveryRoute: Route? = null

    fun jobId(jobId: Long?): Job {
        this.jobId = jobId
        return this
    }

    fun status(status: StatusEnum?): Job {
        this.status = status
        return this
    }

    fun senderRating(senderRating: Int?): Job {
        this.senderRating = senderRating
        return this
    }

    fun size(size: SizeEnum?): Job {
        this.size = size
        return this
    }

    fun payment(payment: Int?): Job {
        this.payment = payment
        return this
    }

    fun jobIssuedDate(jobIssuedDate: OffsetDateTime?): Job {
        this.jobIssuedDate = jobIssuedDate
        return this
    }

    fun deadline(deadline: OffsetDateTime?): Job {
        this.deadline = deadline
        return this
    }

    fun deliveryDate(deliveryDate: OffsetDateTime?): Job {
        this.deliveryDate = deliveryDate
        return this
    }

    fun deliveryRoute(deliveryRoute: Route?): Job {
        this.deliveryRoute = deliveryRoute
        return this
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val job = o as Job
        return Objects.equals(jobId, job.jobId) &&
                Objects.equals(status, job.status) &&
                Objects.equals(senderRating, job.senderRating) &&
                Objects.equals(size, job.size) &&
                Objects.equals(payment, job.payment) &&
                Objects.equals(jobIssuedDate, job.jobIssuedDate) &&
                Objects.equals(deadline, job.deadline) &&
                Objects.equals(deliveryDate, job.deliveryDate) &&
                Objects.equals(deliveryRoute, job.deliveryRoute)
    }

    override fun hashCode(): Int {
        return Objects.hash(jobId, status, senderRating, size, payment, jobIssuedDate, deadline, deliveryDate, deliveryRoute)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class Job {\n")
        sb.append("    jobId: ").append(toIndentedString(jobId)).append("\n")
        sb.append("    status: ").append(toIndentedString(status)).append("\n")
        sb.append("    senderRating: ").append(toIndentedString(senderRating)).append("\n")
        sb.append("    size: ").append(toIndentedString(size)).append("\n")
        sb.append("    payment: ").append(toIndentedString(payment)).append("\n")
        sb.append("    jobIssuedDate: ").append(toIndentedString(jobIssuedDate)).append("\n")
        sb.append("    deadline: ").append(toIndentedString(deadline)).append("\n")
        sb.append("    deliveryDate: ").append(toIndentedString(deliveryDate)).append("\n")
        sb.append("    deliveryRoute: ").append(toIndentedString(deliveryRoute)).append("\n")
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