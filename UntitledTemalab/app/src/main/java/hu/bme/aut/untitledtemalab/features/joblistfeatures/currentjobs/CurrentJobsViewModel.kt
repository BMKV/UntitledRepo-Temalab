package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.network.response.JobDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrentJobsViewModel(
    application: Application,
    userId: Long,
    private val useType: CurrentJobsViewModelUseType
) :
    AndroidViewModel(application) {

    private val repository = CurrentJobsRepository(userId)

    val currentJobsDataResponse = MutableLiveData<JobDataResponse>()

    fun refreshJobsLiveData(){
        Log.i("Freelancer", "Refresh requested!")
        loadJobsDataIntoJobsLiveData()
    }

    private fun loadJobsDataIntoJobsLiveData(){
        viewModelScope.launch(Dispatchers.IO) {
            currentJobsDataResponse.postValue(
                loadJobsDataByUseType()
            )
        }
    }

    private suspend fun loadJobsDataByUseType(): JobDataResponse{
        return when (useType) {
            CurrentJobsViewModelUseType.Accepted -> repository.getAcceptedCurrentJobs()
            CurrentJobsViewModelUseType.Announced -> repository.getAnnouncedCurrentJobs()
        }
    }

    enum class CurrentJobsViewModelUseType {
        Announced, Accepted
    }
}