package hu.bme.aut.untitledtemalab.features.login

import hu.bme.aut.untitledtemalab.network.NetworkManager
import hu.bme.aut.untitledtemalab.network.response.LoginResponse

class LoginRepository {

    fun attemptToLoginUser(
        email: String, password: String, invalidFormatErrorMessage: String,
        invalidDataErrorMessage: String, networkErrorMessage: String, serverErrorMessage: String
    ): LoginResponse {
        return NetworkManager.loginUser(
            email,
            password,
            invalidFormatMessage = invalidFormatErrorMessage,
            invalidDataMessage = invalidDataErrorMessage,
            serverErrorMessage = serverErrorMessage,
            networkErrorMessage = networkErrorMessage
        )
    }

}