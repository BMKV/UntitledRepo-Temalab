package hu.bme.aut.untitledtemalab.data

import com.google.gson.annotations.SerializedName

class JobStatusStatisticsData(
    @SerializedName("allJobs") val jobCount: Int,
    @SerializedName("allJobsPending") val pendingJobCount: Int,
    @SerializedName("allAccepted") val acceptedJobCount: Int,
    @SerializedName("allJobsPickedUp") val pickedUpJobCount: Int,
    @SerializedName("allJobsDelivered") val deliveredJobCount: Int,
    @SerializedName("allJobsExpired") val expiredJobCount: Int,
)