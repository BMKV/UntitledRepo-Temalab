package hu.bme.aut.untitledtemalab.network

import android.util.Log
import hu.bme.aut.untitledtemalab.data.*
import hu.bme.aut.untitledtemalab.network.response.LoginResponse
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    private const val SERVICE_URL = "https://untitled-repo-backend.herokuapp.com"

    private val freelancerApi: FreelancerAPI

    init {
        val retrofit: Retrofit = Retrofit.Builder().run {
            baseUrl(SERVICE_URL)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
        freelancerApi = retrofit.create(FreelancerAPI::class.java)
    }

    suspend fun getSentHistoryByUserId(userId: Long): List<JobData> {
        return freelancerApi.getUsersSentJobs(userId).await()
    }

    suspend fun getDeliveredHistoryByUserId(userId: Long): List<JobData> {
        return freelancerApi.getUsersDeliveredJobs(userId).await()
    }

    suspend fun getUsersPostedJobsByStatus(userId: Long, status: String): List<JobData> {
        return freelancerApi.getUsersPostedJobsByStatus(userId, status).await()
    }

    suspend fun getUsersAcceptedJobsByStatus(userId: Long, status: String): List<JobData> {
        return freelancerApi.getUsersAcceptedJobsByStatus(userId, status).await()
    }

    suspend fun getUserProfileById(userId: Long): UserData {
        return freelancerApi.getUserProfileById(userId).await()
    }

    suspend fun getJobById(jobId: Long): JobData {
        return freelancerApi.getJobById(jobId).await()
    }

    suspend fun getAvailableJobsBySize(size: PackageSize): List<JobData> {
        return freelancerApi.getAvailableJobsBySize(size.getBackendValueName()).await()
    }

    suspend fun getUsersCargoInformation(userId: Long): CargoData {
        Log.i("Freelancer", userId.toString())
        return freelancerApi.getUsersCargoInformation(userId).await()
    }

    suspend fun getUserRoleAdminStatistics(userId: Long): UserRoleStatisticsData {
        return freelancerApi.getUserRoleAdminStatistics(userId).await()
    }

    suspend fun getJobStatusAdminStatistics(userId: Long): JobStatusStatisticsData {
        return freelancerApi.getJobStatusAdminStatistics(userId).await()
    }

    suspend fun acceptJobById(jobId: Long, userId: Long): String {
        return freelancerApi.acceptJobById(jobId, userId).await()
    }

    suspend fun rateJobById(jobId: Long, userId: Long, rating: Long): String {
        return freelancerApi.rateJobById(jobId, userId, rating).await()
    }

    suspend fun pickUpJobById(jobId: Long, userId: Long): String {
        return freelancerApi.pickUpJobById(jobId, userId).await()
    }

    suspend fun deliverJobById(jobId: Long, userId: Long): String {
        return freelancerApi.deliverJobById(jobId, userId).await()
    }

    suspend fun cancelJobById(jobId: Long, userId: Long): String {
        return  freelancerApi.cancelJobById(jobId, userId).await()
    }

    suspend fun postNewJob(userId: Long, newJobData: JobRegistrationData) {
        freelancerApi.postNewJob(userId, newJobData).await()
    }

    fun loginUser(
        email: String,
        password: String,
        invalidFormatMessage: String,
        invalidDataMessage: String,
        networkErrorMessage: String,
        serverErrorMessage: String
    ): LoginResponse {
        return try {
            freelancerApi.loginUser(email, password).execute().let { response ->
                when (response.code()) {
                    400 -> LoginResponse(null, IllegalStateException(invalidFormatMessage))
                    404 -> LoginResponse(null, IllegalStateException(invalidDataMessage))
                    200 -> LoginResponse(response.body()!!.toLong(), null)
                    else -> LoginResponse(null, IllegalStateException(serverErrorMessage))
                }
            }
        } catch (exception: Exception) {
            LoginResponse(null, IllegalStateException(networkErrorMessage))
        }
    }

    fun registerUser(
        registrationData: UserRegistrationData,
        invalidInputMessage: String,
        usedEmailMessage: String,
        incorrectEmailFormatMessage: String,
        serverErrorMessage: String,
        networkErrorMessage: String,
        successfulRegistrationMessage: String
    ): String {
        return try {
            freelancerApi.registerNewUser(registrationData).execute().let { response ->
                when (response.code()) {
                    201 -> successfulRegistrationMessage
                    405 -> invalidInputMessage
                    409 -> usedEmailMessage
                    422 -> incorrectEmailFormatMessage
                    else -> serverErrorMessage
                }
            }
        } catch (exception: Exception) {
            return networkErrorMessage
        }
    }
}