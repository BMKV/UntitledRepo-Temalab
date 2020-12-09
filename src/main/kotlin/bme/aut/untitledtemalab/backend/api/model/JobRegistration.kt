package bme.aut.untitledtemalab.backend.api.model

import bme.aut.untitledtemalab.backend.database.model.Jobs
import bme.aut.untitledtemalab.backend.database.model.PackageSize
import bme.aut.untitledtemalab.backend.database.model.Status
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import java.time.OffsetDateTime


/**
 * Data on the new User
 */
class JobRegistration {
    fun isValid(): Boolean {
        return size != null && payment != null && jobIssuedDate != null && deadline != null && startLocation != null && destination != null && name != null
    }

    fun updateDbJob(job: Jobs): Boolean {
        if (job.status == Status.expired || job.status == Status.pending) {
            var changed = 0
            var value: Any? = changeIfNotEquals(job.size, PackageSize.fromValue(size.toString()))

            if (size != null) {
                if (value != null) {
                    job.size = value as PackageSize
                    changed++
                }
            }
            if (name != null) {
                value = changeIfNotEquals(job.name, name)
                if (value != null) {
                    job.name = value as String
                    changed++
                }
            }
            if (payment != null) {
                value = changeIfNotEquals(job.payment, payment)
                if (value != null) {
                    job.payment = value as Int
                    changed++
                }
            }
            if (jobIssuedDate != null) {
                value = changeIfNotEquals(job.jobIssuedDate, jobIssuedDate.toString())
                if (value != null) {
                    job.jobIssuedDate = value.toString()
                    changed++
                }
            }
            if (deadline != null) {
                value = changeIfNotEquals(job.deadline, deadline.toString())
                if (value != null) {
                    job.deadline = value.toString()
                    changed++
                }
            }
            if (startLocation != null) {
                value = changeIfNotEquals(job.deliveryRoute.startLocation, startLocation)
                if (value != null) {
                    job.deliveryRoute.startLocation = value as String
                    changed++
                }
            }
            if (destination != null) {
                value = changeIfNotEquals(job.deliveryRoute.destination, destination)
                if (value != null) {
                    job.deliveryRoute.destination = value as String
                    changed++
                }
            }
            return 1 <= changed
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
        SMALL("small"),
        MEDIUM("medium"),
        LARGE("large");

        @JsonValue
        override fun toString(): String {
            return value
        }
    }

    @JsonProperty("size")
    var size: SizeEnum? = null

    @JsonProperty("name")
    var name: String? = null

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

