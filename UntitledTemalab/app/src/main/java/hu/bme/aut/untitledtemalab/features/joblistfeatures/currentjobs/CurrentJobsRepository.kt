package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.data.JobStatus
import hu.bme.aut.untitledtemalab.network.response.JobDataResponse
import hu.bme.aut.untitledtemalab.network.NetworkManager
import java.lang.Exception

class CurrentJobsRepository(val userId: Int) {

    suspend fun getAnnouncedCurrentJobs(): JobDataResponse {
        return getActiveJobsWithCategoryRetriever { userId, categoryName ->
            NetworkManager.getUsersPostedJobsByStatus(userId, categoryName)
        }
    }

    suspend fun getAcceptedCurrentJobs(): JobDataResponse {
        return getActiveJobsWithCategoryRetriever { userId, categoryName ->
            NetworkManager.getUsersAcceptedJobsByStatus(userId, categoryName)
        }
    }

    private suspend fun getActiveJobsWithCategoryRetriever(
        categoryRetriever: suspend (Int, String) -> List<JobData>
    ): JobDataResponse {
        val activeJobList = mutableListOf<JobData>()
        for (activeStatus in JobStatus.getActiveStatuses()) {
            try {
                activeJobList.addAll(
                    categoryRetriever(userId, activeStatus.name)
                )
            } catch (error: Exception) {
                return JobDataResponse(null, error)
            }
        }
        return JobDataResponse(activeJobList, null)
    }

}