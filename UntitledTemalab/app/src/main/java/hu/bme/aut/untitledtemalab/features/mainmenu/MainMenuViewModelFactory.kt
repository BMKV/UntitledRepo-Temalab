package hu.bme.aut.untitledtemalab.features.mainmenu

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainMenuViewModelFactory(private val application: Application, val userId: Long) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") //this casting should cause no problems
        return MainMenuViewModel(application, userId) as T
    }
}