package hu.bme.aut.untitledtemalab.features.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val MESSAGE_INVALID_INPUT = "MESSAGE_INVALID_INPUT"
        const val MESSAGE_ALREADY_USED_EMAIL = "MESSAGE_ALREADY_USED_EMAIL"
        const val MESSAGE_INCORRECT_EMAIL_FORMAT = "MESSAGE_INCORRECT_EMAIL_FORMAT"
        const val MESSAGE_SERVER_ERROR = "MESSAGE_SERVER_ERROR"
        const val MESSAGE_NETWORK_ERROR = "MESSAGE_NETWORK_ERROR"
        const val MESSAGE_SUCCESSFUL_REGISTRATION = "MESSAGE_SUCCESSFUL_REGISTRATION"
        private const val MESSAGE_SUCCESSFUL_LOGIN_AFTER_REGISTRATION =
            "MESSAGE_SUCCESSFUL_LOGIN_AFTER_REGISTRATION"
    }

    val registrationResponseMessage = MutableLiveData<String>()

    var successfulRegisterHappened = false

    private val repository = RegisterRepository()

    private lateinit var currentlyUsedEmail: String

    private lateinit var currentlyUsedPassword: String

    fun attemptToRegisterUser(
        email: String,
        password: String,
        isTransporterAlso: Boolean
    ) {
        currentlyUsedEmail = email
        currentlyUsedPassword = password
        viewModelScope.launch(Dispatchers.IO) {
            if (!successfulRegisterHappened) {
                registrationResponseMessage.postValue(
                    repository.attemptToRegisterUser(
                        email = email,
                        password = password,
                        canTransport = isTransporterAlso,
                        invalidInputMessage = MESSAGE_INVALID_INPUT,
                        usedEmailMessage = MESSAGE_ALREADY_USED_EMAIL,
                        incorrectEmailFormatMessage = MESSAGE_INCORRECT_EMAIL_FORMAT,
                        serverErrorMessage = MESSAGE_SERVER_ERROR,
                        networkErrorMessage = MESSAGE_NETWORK_ERROR,
                        successfulRegistrationMessage = MESSAGE_SUCCESSFUL_REGISTRATION
                    )
                )
            }
        }
    }

    fun loginRegisteredUser(
        onSuccessfulLogin: (Long) -> Unit,
        onFailedLogin: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.loginRegisteredUser(
                currentlyUsedEmail,
                currentlyUsedPassword,
                MESSAGE_SUCCESSFUL_LOGIN_AFTER_REGISTRATION
            ).let{ response ->
                when (response.userId) {
                    is Long -> onSuccessfulLogin(response.userId)
                    else -> onFailedLogin()
                }
            }
        }
    }

}