package hu.bme.aut.untitledtemalab.features.adminstatistics.userrole

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AdminUserRoleViewModelFactory(private val application: Application, val userId: Long) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") //this casting should cause no problems
        return AdminUserRoleStatisticViewModel(application, userId) as T
    }
}