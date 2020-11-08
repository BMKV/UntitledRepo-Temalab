package hu.bme.aut.untitledtemalab.network

import hu.bme.aut.untitledtemalab.data.JobData
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

    suspend fun getSentHistory(userId: Int): List<JobData>{
        return freelancerApi.getUsersSentJobs(userId).await()
    }

    suspend fun getDeliveredHistory(userId: Int): List<JobData>{
        return freelancerApi.getUsersDeliveredJobs(userId).await()
    }
}