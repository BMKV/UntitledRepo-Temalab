package hu.bme.aut.untitledtemalab.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    private const val SERVICE_URL = "https://untitled-repo-backend.herokuapp.com/api/v1"

    private val freelancerApi : FreelancerAPI

    init{
        val retrofit: Retrofit = Retrofit.Builder().run {
            baseUrl(SERVICE_URL)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
        freelancerApi = retrofit.create(FreelancerAPI::class.java)
    }

    private suspend fun <T> runCallInBackground(
        call: Call<T>,
        onSuccess: (T) -> Unit,
        onFailure: (Throwable) -> Unit
        ) = withContext(Dispatchers.IO){
        try{
            call.execute().body()!!.let{
                withContext(Dispatchers.Main){
                    onSuccess(it)
                }
            }
        }
        catch(exception: Exception){
            //TODO: the used TAG might be needed to be changed in the future for better consistency
            //  with other log tags
            Log.d("Freelancer", exception.stackTraceToString())
            withContext(Dispatchers.Main){
                onFailure(exception)
            }
        }
    }

}