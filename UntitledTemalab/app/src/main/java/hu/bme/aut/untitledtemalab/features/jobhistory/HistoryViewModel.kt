package hu.bme.aut.untitledtemalab.features.jobhistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class HistoryViewModel(application: Application, useMode: String):
    AndroidViewModel(application) {

    private val repository: HistoryRepository = HistoryRepository()

    val historyElements: LiveData<List<JobHistoryDummyModel>>

    init{
       historyElements = when(useMode){
           HistoryFragment.HistoryType.SentHistory.name -> repository.sentHistory
           HistoryFragment.HistoryType.TransportedHistory.name -> repository.transportedHistory
           else ->
               throw IllegalStateException("There is no such use mode for this viewModel: $useMode")
        }
    }

}