package hu.bme.aut.untitledtemalab.network

import android.util.Log
import hu.bme.aut.untitledtemalab.data.*
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    private const val SERVICE_URL = "https://untitled-repo-backend.herokuapp.com"

    private val freelancerApi : FreelancerAPI

    init{
        val retrofit: Retrofit = Retrofit.Builder().run {
            baseUrl(SERVICE_URL)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
        freelancerApi = retrofit.create(FreelancerAPI::class.java)
    }

    suspend fun getSentHistoryByUserId(userId: Long): List<JobData>{
        return freelancerApi.getUsersSentJobs(userId).await()
    }

    suspend fun getDeliveredHistoryByUserId(userId: Long): List<JobData>{
        return freelancerApi.getUsersDeliveredJobs(userId).await()
    }

    suspend fun getUsersPostedJobsByStatus(userId: Long, status: String): List<JobData>{
        return freelancerApi.getUsersPostedJobsByStatus(userId, status).await()
    }

    suspend fun getUsersAcceptedJobsByStatus(userId: Long, status: String): List<JobData>{
        return freelancerApi.getUsersAcceptedJobsByStatus(userId, status).await()
    }

    suspend fun getUserProfileById(userId: Long): UserData {
        return freelancerApi.getUserProfileById(userId).await()
    }

    suspend fun getJobById(jobId: Long): JobData {
        return freelancerApi.getJobById(jobId).await()
    }

    suspend fun getAvailableJobsBySize(size: PackageSize): List<JobData>{
        return freelancerApi.getAvailableJobsBySize(size.getBackendValueName()).await()
    }

    suspend fun getUsersCargoInformation(userId: Long): CargoData{
        Log.i("Freelancer", userId.toString())
        return freelancerApi.getUsersCargoInformation(userId).await()
    }

    suspend fun getUserRoleAdminStatistics(userId: Long): UserRoleStatisticsData{
        return freelancerApi.getUserRoleAdminStatistics(userId).await()
    }

    suspend fun getJobStatusAdminStatistics(userId: Long): JobStatusStatisticsData{
        return freelancerApi.getJobStatusAdminStatistics(userId).await()
    }

    fun acceptJobById(jobId: Long, userId: Long) {
        freelancerApi.acceptJobById(jobId, userId)
    }

    fun rateJobById(jobId: Long, userId: Long, rating: Long) {
        freelancerApi.rateJobById(jobId, userId, rating)
    }

    fun pickUpJobById(jobId: Long, userId: Long) {
        freelancerApi.pickUpJobById(jobId, userId)
    }

    fun deliverJobById(jobId: Long, userId: Long) {
        freelancerApi.deliverJobById(jobId, userId)
    }

    fun cancelJobById(jobId: Long, userId: Long) {
        freelancerApi.cancelJobById(jobId, userId)
    }

    fun postNewJob(userId: Long, newJobData: JobRegistrationData) {
        freelancerApi.postNewJob(userId, newJobData)
    }
}