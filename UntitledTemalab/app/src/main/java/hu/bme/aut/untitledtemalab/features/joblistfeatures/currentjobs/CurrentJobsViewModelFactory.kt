package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CurrentJobsViewModelFactory(
    private val application: Application,
    private val userId: Int,
    private val useType: CurrentJobsViewModel.CurrentJobsViewModelUseType
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") //this casting should cause no problems
        return CurrentJobsViewModel(application, userId, useType) as T
    }
}