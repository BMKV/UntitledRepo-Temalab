package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.features.joblistfeatures.common.JobDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This [AndroidViewModel] subclass's responsibility to provide the business logic for the
 * components in the View layer, that are showing data about the user's package history.
 */
class HistoryViewModel(application: Application, useMode: String, val userId: Int) :
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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            historyDataResponse.postValue(
                when (useMode) {
                    HistoryFragment.HistoryType.SentHistory.name -> repository.getSentHistory()
                    HistoryFragment.HistoryType.TransportedHistory.name -> repository.getDeliveredHistory()
                    else ->
                        throw IllegalStateException("There is no such use mode for this viewModel: $useMode")
                }
            )
        }
    }

}