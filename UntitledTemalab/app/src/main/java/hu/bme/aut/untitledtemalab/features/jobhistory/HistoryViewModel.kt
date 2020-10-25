package hu.bme.aut.untitledtemalab.features.jobhistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import hu.bme.aut.untitledtemalab.data.JobData

class HistoryViewModel(application: Application, useMode: String):
    AndroidViewModel(application) {

    private val repository: HistoryRepository = HistoryRepository(TODO("User ID will be passed here"))

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