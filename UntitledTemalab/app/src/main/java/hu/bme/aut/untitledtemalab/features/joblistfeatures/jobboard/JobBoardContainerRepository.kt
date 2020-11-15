package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import hu.bme.aut.untitledtemalab.network.NetworkManager
import hu.bme.aut.untitledtemalab.network.response.CargoDataResponse

class JobBoardContainerRepository(private val userId: Long){

    suspend fun getUsersCargoDetails(): CargoDataResponse {
        return try{
            CargoDataResponse(NetworkManager.getUsersCargoInformation(userId), null)
        }
        catch (e: Exception){
            CargoDataResponse(null, e)
        }
    }

}