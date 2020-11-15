package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import hu.bme.aut.untitledtemalab.data.PackageSize
import hu.bme.aut.untitledtemalab.network.NetworkManager
import hu.bme.aut.untitledtemalab.network.response.JobDataResponse
import java.lang.Exception

class JobBoardFragmentRepository {

    suspend fun getAvailableJobBySize(chosenSize: AvailableSize): JobDataResponse {
        return try {
            JobDataResponse(
                NetworkManager.getAvailableJobsBySize(
                    mapAvailableSizeToPackageSize(
                        chosenSize
                    )
                ), null
            )
        } catch (exception: Exception) {
            JobDataResponse(null, exception)
        }
    }

    private fun mapAvailableSizeToPackageSize(availableSize: AvailableSize): PackageSize {
        return when (availableSize) {
            AvailableSize.Small -> PackageSize.Small
            AvailableSize.Medium -> PackageSize.Medium
            AvailableSize.Large -> PackageSize.Large
        }
    }

    enum class AvailableSize {
        Small, Medium, Large
    }

}