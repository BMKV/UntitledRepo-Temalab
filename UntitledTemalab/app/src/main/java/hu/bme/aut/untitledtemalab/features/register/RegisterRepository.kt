package hu.bme.aut.untitledtemalab.features.register

import hu.bme.aut.untitledtemalab.data.UserRegistrationData
import hu.bme.aut.untitledtemalab.network.NetworkManager
import hu.bme.aut.untitledtemalab.network.response.LoginResponse

class RegisterRepository {

    fun attemptToRegisterUser(
        email: String,
        password: String,
        canTransport: Boolean,
        invalidInputMessage: String,
        usedEmailMessage: String,
        incorrectEmailFormatMessage: String,
        serverErrorMessage: String,
        networkErrorMessage: String,
        successfulRegistrationMessage: String
    ): String {
        return NetworkManager.registerUser(
            UserRegistrationData(email, password, canTransport),
            invalidInputMessage,
            usedEmailMessage,
            incorrectEmailFormatMessage,
            serverErrorMessage,
            networkErrorMessage,
            successfulRegistrationMessage
        )
    }

    fun loginRegisteredUser(
        email: String,
        password: String,
        loginErrorMessage: String
    ): LoginResponse {
        return NetworkManager.loginUser(
            email,
            password,
            loginErrorMessage,
            loginErrorMessage,
            loginErrorMessage,
            loginErrorMessage
        )
    }

}