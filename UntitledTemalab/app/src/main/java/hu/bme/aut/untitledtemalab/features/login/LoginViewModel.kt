package hu.bme.aut.untitledtemalab.features.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.untitledtemalab.network.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val ERROR_INVALID_FORMAT: String = "ERROR_INVALID_FORMAT"
        const val ERROR_INVALID_DATA: String = "ERROR_INVALID_DATA"
        const val ERROR_NETWORK: String = "ERROR_NETWORK"
        const val ERROR_SERVER: String = "ERROR_SERVER"
    }

    private val repository = LoginRepository()

    val loginResponse = MutableLiveData<LoginResponse>()

    var successfulLoginHappened = false

    fun attemptToLoginUser(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            if(!successfulLoginHappened){
                loginResponse.postValue(
                    repository.attemptToLoginUser(
                        email,
                        password,
                        invalidDataErrorMessage = ERROR_INVALID_DATA,
                        invalidFormatErrorMessage = ERROR_INVALID_FORMAT,
                        networkErrorMessage = ERROR_NETWORK,
                        serverErrorMessage = ERROR_SERVER
                    )
                )
            }
        }
    }

}