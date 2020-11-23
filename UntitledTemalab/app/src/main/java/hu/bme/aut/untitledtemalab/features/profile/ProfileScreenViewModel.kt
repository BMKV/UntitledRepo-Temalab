package hu.bme.aut.untitledtemalab.features.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.network.response.UserDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileScreenViewModel(application: Application, val userId: Long) :
    AndroidViewModel(application) {

    private val repository = ProfileScreenRepository(userId)

    val userDataResponse = MutableLiveData<UserDataResponse>()

    fun refreshUserData(){
        Log.i("Freelancer", "Refresh requested!")
        loadFreshUserDataFromRepository()
    }

    private fun loadFreshUserDataFromRepository(){
        viewModelScope.launch(Dispatchers.IO) {
            userDataResponse.postValue(
                repository.getUserData()
            )
        }
    }
}