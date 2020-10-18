package hu.bme.aut.untitledtemalab.jobhistory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class HistoryViewModelFactory(private val application: Application, private val useMode: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") //this casting should cause no problems
        return HistoryViewModel(application, useMode) as T
    }
}