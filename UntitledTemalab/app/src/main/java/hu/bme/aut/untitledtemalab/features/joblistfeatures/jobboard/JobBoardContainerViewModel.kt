package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class JobBoardContainerViewModel(application: Application, userId: Int) :
    AndroidViewModel(application) {

    private val jobBoardContainerRepository = JobBoardContainerRepository(userId)

    val CargoOccupancyResponse
}