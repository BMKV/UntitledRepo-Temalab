package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JobBoardFragmentViewModelFactory(
    private val application: Application,
    private val jobType: JobBoardFragmentViewModel.AvailableJobType
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") //this casting should cause no problems
        return JobBoardFragmentViewModel(application, jobType) as T
    }
}