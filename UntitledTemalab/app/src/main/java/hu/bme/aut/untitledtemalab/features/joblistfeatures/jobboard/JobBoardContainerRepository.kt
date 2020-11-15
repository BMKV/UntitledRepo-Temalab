package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import hu.bme.aut.untitledtemalab.network.NetworkManager
import hu.bme.aut.untitledtemalab.network.response.UserDataResponse

class JobBoardContainerRepository(private val userId: Long){

    suspend fun getUsersCargoDetails(): UserDataResponse{
        return try{
            UserDataResponse(NetworkManager.getUserProfileById(userId), null)
        }
        catch (e: Exception){
            UserDataResponse(null, e)
        }
    }

}