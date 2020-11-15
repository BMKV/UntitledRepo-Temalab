package hu.bme.aut.untitledtemalab.network

import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.data.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FreelancerAPI {

    @GET("/user/{user-id}/jobs/sent")
    fun getUsersSentJobs(
        @Path("user-id") userId: Int
    ): Call<MutableList<JobData>>

    @GET("/user/{user-id}/jobs/delivered")
    fun getUsersDeliveredJobs(
        @Path("user-id") userId: Int
    ): Call<MutableList<JobData>>

    @GET("/user/{user-id}/jobs/sent")
    fun getUsersPostedJobsByStatus(
        @Path("user-id") userId: Int,
        @Query("status") status: String
    ): Call<MutableList<JobData>>

    @GET("/user/{user-id}/jobs/delivered")
    fun getUsersAcceptedJobsByStatus(
        @Path("user-id") userId: Int,
        @Query("status") status: String
    ): Call<MutableList<JobData>>

    @GET("/user/profile")
    fun getUserProfileById(
        @Query("user-id") userId: Int
    ): Call<UserData>

    @GET("/jobs")
    fun getAvailableJobsBySize(
        @Query("size") backendSizeType: String
    ): Call<List<JobData>>
}