package hu.bme.aut.untitledtemalab.features.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileScreenViewModelFactory(private val application: Application, val userId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") //this casting should cause no problems
        return ProfileScreenViewModel(application, userId) as T
    }
}