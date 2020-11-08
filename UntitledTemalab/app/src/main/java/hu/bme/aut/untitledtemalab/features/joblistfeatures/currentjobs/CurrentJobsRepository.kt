package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

import hu.bme.aut.untitledtemalab.features.joblistfeatures.common.JobDataResponse

class CurrentJobsRepository(val userId: Int) {

    suspend fun getAnnouncedCurrentJobs(): JobDataResponse{
        TODO("Meg kell kérdezni Dávidot, hogy ehhez lesz-e REST hívás.")
    }

    suspend fun getAcceptedCurrentJobs(): JobDataResponse{
        TODO("Meg kell kérdezni Dávidot, hogy ehhez lesz-e REST hívás.")
    }

}