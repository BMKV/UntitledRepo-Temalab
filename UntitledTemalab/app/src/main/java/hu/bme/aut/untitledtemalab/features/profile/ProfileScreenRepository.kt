package hu.bme.aut.untitledtemalab.features.profile

import hu.bme.aut.untitledtemalab.network.NetworkManager
import hu.bme.aut.untitledtemalab.network.response.UserDataResponse

class ProfileScreenRepository(private val userId: Long) {

    suspend fun getUserData(): UserDataResponse{
        return try{
            UserDataResponse(NetworkManager.getUserProfileById(userId), null)
        }
        catch (exception: Exception){
            UserDataResponse(null, exception)
        }
    }

}