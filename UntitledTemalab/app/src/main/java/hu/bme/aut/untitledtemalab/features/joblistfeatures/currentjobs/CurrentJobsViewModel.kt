package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import hu.bme.aut.untitledtemalab.features.joblistfeatures.common.JobDataResponse

class CurrentJobsViewModel(
    application: Application,
    val userId: Int,
    useType: CurrentJobsViewModelUseType
) :
    AndroidViewModel(application) {

    private val repository = CurrentJobsRepository(userId)



    enum class CurrentJobsViewModelUseType {
        Announced, Accepted
    }
}