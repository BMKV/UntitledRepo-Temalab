package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import hu.bme.aut.untitledtemalab.features.joblistfeatures.common.JobDataResponse
import hu.bme.aut.untitledtemalab.network.NetworkManager

/**
 * TODO documentation
 */
class HistoryRepository(private val userId: Int) {

    suspend fun getSentHistory(): JobDataResponse{
        return try{
            JobDataResponse(NetworkManager.getSentHistoryByUserId(userId), null)
        } catch (exception: Exception){
            JobDataResponse(null, exception)
        }
    }

    suspend fun getDeliveredHistory(): JobDataResponse{
        return try{
            JobDataResponse(NetworkManager.getDeliveredHistoryByUserId(userId), null)
        } catch(exception: Exception){
            JobDataResponse(null, exception)
        }
    }

}