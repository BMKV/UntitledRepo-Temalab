package hu.bme.aut.untitledtemalab.network

import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.data.PackageSize
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

    suspend fun getAvailableJobsBySize(size: PackageSize): List<JobData>{
        return freelancerApi.getAvailableJobsBySize(size.getBackendValueName()).await()
    }
}