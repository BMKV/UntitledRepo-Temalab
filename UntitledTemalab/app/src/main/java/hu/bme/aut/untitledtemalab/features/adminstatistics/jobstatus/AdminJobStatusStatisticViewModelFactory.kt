package hu.bme.aut.untitledtemalab.features.adminstatistics.jobstatus

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AdminJobStatusStatisticViewModelFactory(
    private val application: Application,
    val userId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") //this casting should cause no problems
        return AdminJobStatusStatisticViewModel(application, userId) as T
    }
}