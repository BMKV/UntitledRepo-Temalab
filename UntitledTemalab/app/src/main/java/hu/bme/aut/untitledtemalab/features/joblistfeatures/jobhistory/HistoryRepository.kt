package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.network.NetworkManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * TODO documentation
 */
class HistoryRepository(private val userId: Int) {

    val sentHistory: LiveData<List<JobData>>
    get(){
        return MutableLiveData<List<JobData>>().apply{
            GlobalScope.launch {
                postValue(NetworkManager.getSentHistory(userId))
            }
        }
    }

    val deliveredHistory: LiveData<List<JobData>>
    get(){
        return MutableLiveData<List<JobData>>().apply{
            GlobalScope.launch {
                postValue(NetworkManager.getDeliveredHistory(userId))
            }
        }
    }

}