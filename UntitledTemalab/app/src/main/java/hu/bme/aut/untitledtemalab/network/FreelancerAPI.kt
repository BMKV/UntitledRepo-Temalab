package hu.bme.aut.untitledtemalab.network

import hu.bme.aut.untitledtemalab.data.JobData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FreelancerAPI {

    @GET("/user/{user-id}/jobs/sent")
    fun getUsersSentJobs(
        @Path("user-id") userId: Int
    ): Call<MutableList<JobData>>

    @GET("/user/{user-id}/jobs/delivered")
    fun getUsersDeliveredJobs(
        @Path("user-id") userId: Int
    ): Call<MutableList<JobData>>
}