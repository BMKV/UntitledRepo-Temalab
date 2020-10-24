package hu.bme.aut.untitledtemalab.features.jobhistory

import androidx.lifecycle.LiveData

class HistoryRepository {

    val sentHistory: LiveData<List<JobHistoryDummyModel>> =
        TODO("This property will get its value from a network call (retrofit2)")

    val transportedHistory: LiveData<List<JobHistoryDummyModel>> =
        TODO("This property will get its value from a network call (retrofit2)")

}