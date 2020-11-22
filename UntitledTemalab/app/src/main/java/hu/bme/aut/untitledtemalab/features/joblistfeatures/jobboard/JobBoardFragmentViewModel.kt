package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.network.response.JobDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobBoardFragmentViewModel(
    application: Application,
    private val availableJobType: AvailableJobType
) :
    AndroidViewModel(application) {

    val availableJobs = MutableLiveData<JobDataResponse>()

    private val repository = JobBoardFragmentRepository()

    fun refreshJobsLiveData() {
        Log.i("Freelancer", "Refresh requested!")
        loadJobsDataIntoJobsLiveData()
    }

    private fun loadJobsDataIntoJobsLiveData() {
        viewModelScope.launch(Dispatchers.IO) {
            availableJobs.postValue(
                loadJobsDataByJobType()
            )
        }
    }

    private suspend fun loadJobsDataByJobType(): JobDataResponse {
        return when (availableJobType) {
            AvailableJobType.Small -> repository.getAvailableJobBySize(
                JobBoardFragmentRepository.AvailableSize.Small
            )
            AvailableJobType.Medium -> repository.getAvailableJobBySize(
                JobBoardFragmentRepository.AvailableSize.Medium
            )
            AvailableJobType.Large -> repository.getAvailableJobBySize(
                JobBoardFragmentRepository.AvailableSize.Large
            )
        }
    }

    enum class AvailableJobType {
        Small, Medium, Large
    }
}