package hu.bme.aut.untitledtemalab.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private const val baseUrl = "https://untitled-repo-backend.herokuapp.com/api/v1"

    val freelancerAPI: FreelancerAPI

    init {
        val retrofit: Retrofit = Retrofit.Builder().run {
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
        freelancerAPI = retrofit.create(FreelancerAPI::class.java)
    }

}