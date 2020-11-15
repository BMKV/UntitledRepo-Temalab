package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.network.response.UserDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobBoardContainerViewModel(application: Application, userId: Long) :
    AndroidViewModel(application) {

    private val jobBoardContainerRepository = JobBoardContainerRepository(userId)

    //TODO this will be hidden behind an interface from view layer (like the other responses)
    val cargoOccupancyResponse = MutableLiveData<UserDataResponse>()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            cargoOccupancyResponse.postValue(
                jobBoardContainerRepository.getUsersCargoDetails()
            )
        }
    }
}