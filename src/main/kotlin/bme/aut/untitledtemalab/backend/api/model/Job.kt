package bme.aut.untitledtemalab.backend.api.model

import bme.aut.untitledtemalab.backend.database.model.Jobs
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import java.time.OffsetDateTime

/**
 * Detailed information on a Job
 */
class Job(dbJob: Jobs?) {


    /**
     * The ID of the Job
     * @return jobId
     */
    @JsonProperty("job-id")
    var jobId: Long? = null

    @JsonProperty("name")
    var name: String? = null

    /**
     * The ID of the sender User
     * @return jobId
     */
    @JsonProperty("sender-id")
    var senderId: Long? = null


    /**
     * The current status of the Job
     */
    enum class StatusEnum(private val value: String) {
        pending("pending"),
        accepted("accepted"),
        pickedUp("pickedUp"),
        delivered("delivered"),
        expired("expired");

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
    @JsonProperty("status")
    var status: StatusEnum? = null

    /**
     * The sender rating of the delivery
     * minimum: 1
     * maximum: 5
     * @return senderRating
     */
    @JsonProperty("senderRating")
    var senderRating: Int? = null

    /**
     * The size of the package
     */
    enum class SizeEnum(private val value: String) {
        small("small"),
        medium("medium"),
        large("large");

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
    @JsonProperty("size")
    var size: SizeEnum? = null

    /**
     * The payment for the delivery
     * @return payment
     */
    @JsonProperty("payment")
    var payment: Int? = null

    /**
     * The date the sender issued the Job
     * @return jobIssuedDate
     */
    @JsonProperty("jobIssuedDate")
    var jobIssuedDate: OffsetDateTime? = null

    /**
     * The deadline of the Job
     * @return deadline
     */
    @JsonProperty("deadline")
    var deadline: OffsetDateTime? = null

    /**
     * The day the package was delivered
     * @return deliveryDate
     */
    @JsonProperty("deliveryDate")
    var deliveryDate: OffsetDateTime? = null

    /**
     * Get deliveryRoute
     * @return deliveryRoute
     */
    @JsonProperty("deliveryRoute")
    var deliveryRoute: Route? = null

    init {
        if (dbJob != null) {
            this.jobId = dbJob.id
            this.name = dbJob.name
            this.senderId = dbJob.sender.id
            this.status = StatusEnum.fromValue(dbJob.status.toString())
            this.size = SizeEnum.fromValue(dbJob.size.toString())
            this.deadline = OffsetDateTime.parse(dbJob.deadline)
            this.jobIssuedDate = OffsetDateTime.parse(dbJob.jobIssuedDate)
            this.payment = dbJob.payment
            this.deliveryRoute = Route(dbJob.deliveryRoute)
            dbJob.senderRating?.let { this.senderRating = it }
            dbJob.deliveryDate?.let { this.deliveryDate = OffsetDateTime.parse(it) }
        }
    }

    companion object {
        fun convertDbJobListToApiJobList(dbJobsList: List<Jobs>): List<Job> {
            val result = mutableListOf<Job>()
            if (dbJobsList.isEmpty())
                return result
            for (j: Jobs in dbJobsList)
                result.add(Job(j))
            return result
        }
    }


}