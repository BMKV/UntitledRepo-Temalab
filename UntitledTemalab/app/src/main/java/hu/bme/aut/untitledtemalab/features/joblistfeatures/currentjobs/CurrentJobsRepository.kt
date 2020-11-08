package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

import androidx.lifecycle.LiveData
import hu.bme.aut.untitledtemalab.features.joblistfeatures.common.JobDataResponse

class CurrentJobsRepository(val userId: Int) {

    suspend fun getAnnouncedCurrentJobs(): JobDataResponse{
        TODO()
    }

    suspend fun getAcceptedCurrentJobs(): JobDataResponse{
        TODO()
    }

}