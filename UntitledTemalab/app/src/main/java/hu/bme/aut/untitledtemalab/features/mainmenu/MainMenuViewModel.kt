package hu.bme.aut.untitledtemalab.features.mainmenu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.network.response.AdminQueryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainMenuViewModel(application: Application, val userId: Long): AndroidViewModel(application) {

    private val repository = MainMenuRepository()

    val isAdminResponse = MutableLiveData<AdminQueryResponse>()

    fun refreshAdminAbility(){
        Log.i("Freelancer", "Refresh requested!")
        queryUsersAdminAbility()
    }

    private fun queryUsersAdminAbility(){
        viewModelScope.launch(Dispatchers.IO) {
            isAdminResponse.postValue(
                repository.specifiedUserIsAdmin(userId)
            )
        }
    }

}