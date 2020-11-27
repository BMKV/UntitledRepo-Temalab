package hu.bme.aut.untitledtemalab.network

import hu.bme.aut.untitledtemalab.data.*
import retrofit2.Call
import retrofit2.http.*

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

    @GET("/api/v1/jobs/post/{job-id}")
    fun getJobById(
        @Path("job-id") jobId: Long
    ): Call<JobData>


    //Note: ezeknek nincs return értéke, de lehet, hogy kellene legyen?
    //Internet azt mondja, hogy Call<void>?
    @POST("/api/v1/jobs/accept/{job-id}")
    fun acceptJobById(
        @Path("job-id") jobId: Long,
        @Query("user-id") userId: Long
    )

    @PUT("/api/v1/jobs/rate/{job-id}")
    fun rateJobById(
        @Path("job-id") jobId: Long,
        @Query("user-id") userId: Long,
        @Query("rating") rating: Long
    )

    @PUT("/api/v1/jobs/pickup/{job-id}")
    fun pickUpJobById(
        @Path("job-id") jobId: Long,
        @Query("user-id") userId: Long
    )

    @PUT("/api/v1/jobs/deliver/{job-id}")
    fun deliverJobById(
        @Path("job-id") jobId: Long,
        @Query("user-id") userId: Long
    )

    @DELETE("/api/v1/jobs/accept/{job-id}")
    fun cancelJobById(
        @Path("job-id") jobId: Long,
        @Query("user-id") userId: Long
    )

    @POST("/api/v1/jobs")
    fun postNewJob(
        @Query("user-id") userId: Long,
        @Body newJob: JobRegistrationData
    )

    @GET("/api/v1/admin/statistics")
    fun getUserRoleAdminStatistics(
        @Query("user-id") userId: Long
    ): Call<UserRoleStatisticsData>

    @GET("/api/v1/admin/statistics")
    fun getJobStatusAdminStatistics(
        @Query("user-id") userId: Long
    ): Call<JobStatusStatisticsData>
}