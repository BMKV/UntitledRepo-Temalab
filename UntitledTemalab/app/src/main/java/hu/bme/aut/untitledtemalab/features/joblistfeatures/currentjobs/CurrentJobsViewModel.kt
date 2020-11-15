package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.network.response.JobDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrentJobsViewModel(
    application: Application,
    userId: Int,
    private val useType: CurrentJobsViewModelUseType
) :
    AndroidViewModel(application) {

    private val repository = CurrentJobsRepository(userId)

    val currentJobsDataResponse = MutableLiveData<JobDataResponse>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currentJobsDataResponse.postValue(
                when (useType) {
                    CurrentJobsViewModelUseType.Accepted -> repository.getAcceptedCurrentJobs()
                    CurrentJobsViewModelUseType.Announced -> repository.getAnnouncedCurrentJobs()
                }
            )
        }
    }

    enum class CurrentJobsViewModelUseType {
        Announced, Accepted
    }
}