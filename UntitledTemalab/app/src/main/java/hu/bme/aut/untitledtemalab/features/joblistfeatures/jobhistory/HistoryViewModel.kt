package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import hu.bme.aut.untitledtemalab.data.JobData

/**
 * This [AndroidViewModel] subclass's responsibility to provide the business logic for the
 * components in the View layer, that are showing data about the user's package history.
 */
class HistoryViewModel(application: Application, useMode: String):
    AndroidViewModel(application) {

    /**
     * This variable stores reference to the repository class, that can be used to reach the data
     * layer.
     */
    private val repository: HistoryRepository = HistoryRepository(TODO("User ID will be passed here"))

    /**
     * This variable stores the list of data about the history, which is represented by the UI layer.
     */
    val historyElements: LiveData<MutableList<JobData>>

    init{
       historyElements = when(useMode){
           HistoryFragment.HistoryType.SentHistory.name -> repository.sentHistory
           HistoryFragment.HistoryType.TransportedHistory.name -> repository.transportedHistory
           else ->
               throw IllegalStateException("There is no such use mode for this viewModel: $useMode")
        }
    }

}