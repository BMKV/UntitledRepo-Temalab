package hu.bme.aut.untitledtemalab.features.adminstatistics.userrole

import hu.bme.aut.untitledtemalab.network.NetworkManager
import hu.bme.aut.untitledtemalab.network.response.UserRoleStatisticResponse
import java.lang.Exception

class AdminUserRoleStatisticRepository(private val userId: Long) {

    suspend fun getUserRoleStatistics(): UserRoleStatisticResponse{
        return try{
            UserRoleStatisticResponse(NetworkManager.getUserRoleAdminStatistics(userId), null)
        }
        catch (exception: Exception){
            UserRoleStatisticResponse(null, exception)
        }
    }

}