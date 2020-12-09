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
}