package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.untitledtemalab.features.joblistfeatures.common.JobDataResponse
import hu.bme.aut.untitledtemalab.network.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * TODO documentation
 */
class HistoryRepository(private val userId: Int) {

    val sentHistory: LiveData<JobDataResponse>
    get(){
        return MutableLiveData<JobDataResponse>().apply{
            GlobalScope.launch(Dispatchers.IO) {
                try{
                    postValue(JobDataResponse(NetworkManager.getSentHistory(userId), null))
                }
                catch (exception: Throwable){
                    postValue(JobDataResponse(null, exception))
                }
            }
        }
    }

    val deliveredHistory: LiveData<JobDataResponse>
    get(){
        return MutableLiveData<JobDataResponse>().apply{
            GlobalScope.launch(Dispatchers.IO) {
                try{
                    postValue(JobDataResponse(NetworkManager.getDeliveredHistory(userId), null))
                }
                catch (exception: Throwable){
                    postValue(JobDataResponse(null, exception))
                }
            }
        }
    }

}