package hu.bme.aut.untitledtemalab.features.profile

import hu.bme.aut.untitledtemalab.network.response.UserDataResponse

class ProfileScreenRepository(private val userId: Long) {

    suspend fun getUserData(): UserDataResponse{
        TODO()
    }

}