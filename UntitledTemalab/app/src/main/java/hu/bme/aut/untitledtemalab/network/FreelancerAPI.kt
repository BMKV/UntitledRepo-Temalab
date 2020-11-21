package hu.bme.aut.untitledtemalab.network

import hu.bme.aut.untitledtemalab.data.CargoData
import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.data.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FreelancerAPI {

    @GET("/api/v1/user/{user-id}/jobs/sent")
    fun getUsersSentJobs(
        @Path("user-id") userId: Long
    ): Call<MutableList<JobData>>

    @GET("/api/v1/user/{user-id}/jobs/delivered")
    fun getUsersDeliveredJobs(
        @Path("user-id") userId: Long
    ): Call<MutableList<JobData>>

    @GET("/api/v1/user/{user-id}/jobs/sent")
    fun getUsersPostedJobsByStatus(
        @Path("user-id") userId: Long,
        @Query("status") status: String
    ): Call<MutableList<JobData>>

    @GET("/api/v1/user/{user-id}/jobs/delivered")
    fun getUsersAcceptedJobsByStatus(
        @Path("user-id") userId: Long,
        @Query("status") status: String
    ): Call<MutableList<JobData>>

    @GET("/api/v1/user/profile")
    fun getUserProfileById(
        @Query("user-id") userId: Long
    ): Call<UserData>

    @GET("/api/v1/jobs")
    fun getAvailableJobsBySize(
        @Query("size") backendSizeType: String
    ): Call<List<JobData>>

    @GET("/api/v1/user/{user-id}/cargo")
    fun getUsersCargoInformation(
        @Path("user-id") userId: Long,
    ): Call<CargoData>

    //TODO ez valahol nem stimmel
    @GET("/api/v1/jobs/post/{job-id}")
    fun getJobById(
        @Path("job-id") jobId: Long
    ): Call<JobData>
}