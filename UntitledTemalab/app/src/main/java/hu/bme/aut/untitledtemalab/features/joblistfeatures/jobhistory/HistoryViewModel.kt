package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.network.response.JobDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This [AndroidViewModel] subclass's responsibility to provide the business logic for the
 * components in the View layer, that are showing data about the user's package history.
 */
class HistoryViewModel(application: Application, private val historyType: HistoryType, val userId: Long) :
    AndroidViewModel(application) {

    /**
     * This variable stores reference to the repository class, that can be used to reach the data
     * layer.
     */
    private val repository: HistoryRepository = HistoryRepository(userId)

    /**
     * This variable stores the list of data about the history, which is represented by the UI layer.
     */
    val historyDataResponse = MutableLiveData<JobDataResponse>()

    fun refreshJobsLiveData() {
        Log.i("Freelancer", "Refresh requested!")
        loadJobsDataIntoJobsLiveData()
    }

    private fun loadJobsDataIntoJobsLiveData() {
        viewModelScope.launch(Dispatchers.IO) {
            historyDataResponse.postValue(
                loadJobsDataByJobType()
            )
        }
    }

    private suspend fun loadJobsDataByJobType(): JobDataResponse {
        return when (historyType) {
            HistoryType.Sent -> repository.getSentHistory()
            HistoryType.Delivered -> repository.getDeliveredHistory()
        }
    }

    enum class HistoryType{
        Sent, Delivered
    }

}