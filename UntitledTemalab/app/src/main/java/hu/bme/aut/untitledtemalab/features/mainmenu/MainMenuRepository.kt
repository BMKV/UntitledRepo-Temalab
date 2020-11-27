package hu.bme.aut.untitledtemalab.features.mainmenu

import hu.bme.aut.untitledtemalab.network.NetworkManager
import hu.bme.aut.untitledtemalab.network.response.AdminQueryResponse
import java.lang.Exception

class MainMenuRepository{

    suspend fun specifiedUserIsAdmin(userId: Long): AdminQueryResponse{
        return try{
            AdminQueryResponse(NetworkManager.getUserProfileById(userId).isAdmin, null)
        }
        catch (exception: Exception){
            AdminQueryResponse(null, exception)
        }
    }

}