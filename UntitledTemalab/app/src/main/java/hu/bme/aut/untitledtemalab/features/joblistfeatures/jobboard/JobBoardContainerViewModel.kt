package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.network.response.CargoDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobBoardContainerViewModel(application: Application, userId: Long) :
    AndroidViewModel(application) {

    private val jobBoardContainerRepository = JobBoardContainerRepository(userId)

    val cargoOccupancyResponse = MutableLiveData<CargoDataResponse>()

    fun refreshJobsLiveData() {
        Log.i("Freelancer", "Refresh requested!")
        loadJobsDataIntoJobsLiveData()
    }

    private fun loadJobsDataIntoJobsLiveData() {
        viewModelScope.launch(Dispatchers.IO) {
            cargoOccupancyResponse.postValue(
                jobBoardContainerRepository.getUsersCargoDetails()
            )
        }
    }
}