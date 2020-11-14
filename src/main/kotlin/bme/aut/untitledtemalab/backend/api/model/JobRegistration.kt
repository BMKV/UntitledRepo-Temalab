package bme.aut.untitledtemalab.backend.api.model

import bme.aut.untitledtemalab.backend.database.model.Jobs
import bme.aut.untitledtemalab.backend.database.model.PackageSize
import bme.aut.untitledtemalab.backend.database.model.Status
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import java.time.OffsetDateTime


/**
 * Data on the new User
 */
class JobRegistration {
    fun isValid(): Boolean {
        return size != null && payment != null && jobIssuedDate != null && deadline != null && startLocation != null && destination != null
    }

    fun updateDbJob(job: Jobs): Boolean {
        if (job.status == Status.expired || job.status == Status.pending) {
            var changed: Int = 0
            var value: Any? = changeIfNotEquals(job.size, PackageSize.fromValue(size.toString()))

            if (value != null) {
                job.size = value as PackageSize
                changed++
            }
            value = changeIfNotEquals(job.payment, payment)
            if (value != null) {
                job.payment = value as Int
                changed++
            }
            value = changeIfNotEquals(job.jobIssuedDate, jobIssuedDate)
            if (value != null) {
                job.jobIssuedDate = value as String
                changed++
            }
            value = changeIfNotEquals(job.jobIssuedDate, jobIssuedDate)
            if (value != null) {
                job.deadline = value as String
                changed++
            }
            value = changeIfNotEquals(job.deliveryRoute.startLocation, startLocation)
            if (value != null) {
                job.deliveryRoute.startLocation = value as String
                changed++
            }
            value = changeIfNotEquals(job.deliveryRoute.destination, deadline)
            if (value != null) {
                job.deliveryRoute.destination = value as String
                changed++
            }
            return 0 < changed
        }
        return false
    }

    private fun changeIfNotEquals(old: Any?, new: Any?): Any? {
        return if (old == null || new == null)
            null
        else if (old == new)
            null
        else return new
    }

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
                    if (b.value.toString() == text) {
                        return b
                    }
                }
                return null
            }
        }
    }

    @JsonProperty("size")
    var size: SizeEnum? = null

    /**
     * The payment for the delivery
     * @return payment
     */
    @JsonProperty("payment")
    var payment: Int? = null

    @JsonProperty("jobIssuedDate")
    var jobIssuedDate: OffsetDateTime? = null

    @JsonProperty("deadline")
    var deadline: OffsetDateTime? = null

    /**
     * The start location of the Route
     * @return startLocation
     */
    @JsonProperty("startLocation")
    var startLocation: String? = null

    /**
     * The destination of the Route
     * @return destination
     */
    @JsonProperty("destination")
    var destination: String? = null

}

