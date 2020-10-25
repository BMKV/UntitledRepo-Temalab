package hu.bme.aut.untitledtemalab.features.jobhistory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * This [ViewModelProvider.Factory] interface implementation can be used to instantiate ViewModel's
 * for the HistoryFragment instances.
 * @param application the Application's reference.
 * @param useMode this dependency is injected into the instantiation of the constructed ViewModel
 * by this Factory class. The useMode defines that the ViewModel will either store data about
 * the user's sent package history or about the user's delivery history.
 *
 */
class HistoryViewModelFactory(private val application: Application, private val useMode: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") //this casting should cause no problems
        return HistoryViewModel(application, useMode) as T
    }
}