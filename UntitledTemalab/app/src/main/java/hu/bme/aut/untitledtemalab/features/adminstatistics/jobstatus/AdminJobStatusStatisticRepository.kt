package hu.bme.aut.untitledtemalab.features.adminstatistics.jobstatus

import hu.bme.aut.untitledtemalab.network.NetworkManager
import hu.bme.aut.untitledtemalab.network.response.JobStatusStatisticResponse

class AdminJobStatusStatisticRepository(private val userId: Long) {

    suspend fun getJobStatusStatistics(): JobStatusStatisticResponse{
        return try{
            JobStatusStatisticResponse(NetworkManager.getJobStatusAdminStatistics(userId), null)
        } catch (exception: Exception){
            JobStatusStatisticResponse(null, exception)
        }
    }

}