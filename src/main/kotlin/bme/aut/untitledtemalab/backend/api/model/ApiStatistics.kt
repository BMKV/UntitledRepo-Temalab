package bme.aut.untitledtemalab.backend.api.model

import com.fasterxml.jackson.annotation.JsonProperty

class ApiStatistics {
    @JsonProperty("allUsers")
    var allUsersNum: Long = 0

    @JsonProperty("allSenders")
    var allSendersNum: Long = 0

    @JsonProperty("allDeliverers")
    var allDeliverersNum: Long = 0

    @JsonProperty("allJobs")
    var allJobsNum: Long = 0

    @JsonProperty("allJobsPending")
    var allPendingJobsNum: Long = 0

    @JsonProperty("allAccepted")
    var allAcceptedJobsNum: Long = 0

    @JsonProperty("allJobsPickedUp")
    var allPickedUpJobsNum: Long = 0

    @JsonProperty("allJobsDelivered")
    var allDeliveredJobsNum: Long = 0

    @JsonProperty("allJobsExpired")
    var allExpiredJobsNum: Long = 0

    override fun equals(other: Any?): Boolean {
        other as ApiStatistics
        return other.allUsersNum == allUsersNum &&
                other.allSendersNum == allSendersNum &&
                other.allDeliverersNum == allDeliverersNum &&
                other.allJobsNum == allJobsNum &&
                other.allPendingJobsNum == allPendingJobsNum &&
                other.allAcceptedJobsNum == allAcceptedJobsNum &&
                other.allPickedUpJobsNum == allPickedUpJobsNum &&
                other.allDeliveredJobsNum == allDeliveredJobsNum &&
                other.allExpiredJobsNum == allExpiredJobsNum

    }

    override fun hashCode(): Int {
        var result = allUsersNum.hashCode()
        result = 31 * result + allSendersNum.hashCode()
        result = 31 * result + allDeliverersNum.hashCode()
        result = 31 * result + allJobsNum.hashCode()
        result = 31 * result + allPendingJobsNum.hashCode()
        result = 31 * result + allAcceptedJobsNum.hashCode()
        result = 31 * result + allPickedUpJobsNum.hashCode()
        result = 31 * result + allDeliveredJobsNum.hashCode()
        result = 31 * result + allExpiredJobsNum.hashCode()
        return result
    }
}