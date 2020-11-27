package hu.bme.aut.untitledtemalab.features.adminstatistics.jobstatus

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.network.response.JobStatusStatisticResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminJobStatusStatisticViewModel(application: Application, userId: Long) :
    AndroidViewModel(application) {

    val statisticsResponse = MutableLiveData<JobStatusStatisticResponse>()

    private val repository = AdminJobStatusStatisticRepository(userId)

    fun refreshStatistics(){
        Log.i("Freelancer", "Refresh requested!")
        loadStatistics()
    }

    private fun loadStatistics(){
        viewModelScope.launch(Dispatchers.IO) {
            statisticsResponse.postValue(
                repository.getJobStatusStatistics()
            )
        }
    }

}