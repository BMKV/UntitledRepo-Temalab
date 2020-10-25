package hu.bme.aut.untitledtemalab.features.jobhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.network.RetrofitService
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

/**
 * TODO documentation
 */
class HistoryRepository(private val userId: Int) {

    private val freelancerAPI = RetrofitService.freelancerAPI

    @Suppress("UNREACHABLE_CODE")   //It will be eventually fixed
    val sentHistory: LiveData<MutableList<JobData>>
    get(){
        val queriedData = MutableLiveData<MutableList<JobData>>()
        freelancerAPI.getUsersSentJobs(userId).enqueue(object : Callback<MutableList<JobData>>{
            override fun onResponse(call: Call<MutableList<JobData>>, response: Response<MutableList<JobData>>) {
                if(response.isSuccessful)
                    queriedData.value = response.body()
                //TODO if the response is not successful it should be handled properly
            }

            override fun onFailure(call: Call<MutableList<JobData>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return queriedData
    }

    val transportedHistory: LiveData<MutableList<JobData>>
    get(){
        val queriedData = MutableLiveData<MutableList<JobData>>()
        freelancerAPI.getUsersDeliveredJobs(userId).enqueue(object: Callback<MutableList<JobData>>{
            override fun onResponse(call: Call<MutableList<JobData>>, response: Response<MutableList<JobData>>) {
                if(response.isSuccessful)
                    queriedData.value = response.body()
                //TODO if the response is not successful it should be handled properly
            }

            override fun onFailure(call: Call<MutableList<JobData>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return queriedData
    }

}