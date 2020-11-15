package hu.bme.aut.untitledtemalab.network

import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.data.UserData
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    private const val SERVICE_URL = "https://untitled-repo-backend.herokuapp.com/api/v1/"

    private val freelancerApi : FreelancerAPI

    init{
        val retrofit: Retrofit = Retrofit.Builder().run {
            baseUrl(SERVICE_URL)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
        freelancerApi = retrofit.create(FreelancerAPI::class.java)
    }

    suspend fun getSentHistoryByUserId(userId: Int): List<JobData>{
        return freelancerApi.getUsersSentJobs(userId).await()
    }

    suspend fun getDeliveredHistoryByUserId(userId: Int): List<JobData>{
        return freelancerApi.getUsersDeliveredJobs(userId).await()
    }

    suspend fun getUsersPostedJobsByStatus(userId: Int, status: String): List<JobData>{
        return freelancerApi.getUsersPostedJobsByStatus(userId, status).await()
    }

    suspend fun getUsersAcceptedJobsByStatus(userId: Int, status: String): List<JobData>{
        return freelancerApi.getUsersAcceptedJobsByStatus(userId, status).await()
    }
    
    suspend fun getUserProfileById(userId: Int): UserData {
        return freelancerApi.getUserProfileById(userId).await()
    }
}