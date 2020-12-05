package hu.bme.aut.untitledtemalab.features.mainmenu

import hu.bme.aut.untitledtemalab.network.NetworkManager
import hu.bme.aut.untitledtemalab.network.response.AbilityQueryResponse
import kotlin.Exception

class MainMenuRepository{

    suspend fun specifiedUserIsAdmin(userId: Long): AbilityQueryResponse{
        return try{
            AbilityQueryResponse(NetworkManager.getUserProfileById(userId).isAdmin, null)
        }
        catch (exception: Exception){
            AbilityQueryResponse(null, exception)
        }
    }

    suspend fun specifiedUserCanTransport(userId: Long): AbilityQueryResponse{
        return try{
            AbilityQueryResponse(NetworkManager.getUserProfileById(userId).canDeliver, null)
        } catch (exception: Exception){
            AbilityQueryResponse(null, exception)
        }
    }

}