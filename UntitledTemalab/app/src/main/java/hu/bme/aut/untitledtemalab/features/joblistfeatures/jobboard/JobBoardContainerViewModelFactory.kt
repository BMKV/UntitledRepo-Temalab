package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JobBoardContainerViewModelFactory(
    private val application: Application,
    private val userId: Long
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") //this casting should cause no problems
        return JobBoardContainerViewModel(application, userId) as T
    }
}