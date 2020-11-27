package hu.bme.aut.untitledtemalab.features.adminstatistics.userrole

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.network.response.UserRoleStatisticResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminUserRoleStatisticViewModel(application: Application, userId: Long) :
    AndroidViewModel(application) {

    private val repository = AdminUserRoleStatisticRepository(userId)

    val userRoleStatisticsResponse = MutableLiveData<UserRoleStatisticResponse>()

    fun refreshStatistics() {
        Log.i("Freelancer", "Refresh requested!")
    }

    private fun updateStatistics() {
        viewModelScope.launch(Dispatchers.IO) {
            userRoleStatisticsResponse.postValue(
                repository.getUserRoleStatistics()
            )
        }
    }

}