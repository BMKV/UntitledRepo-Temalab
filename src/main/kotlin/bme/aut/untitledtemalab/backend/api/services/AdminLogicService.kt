package bme.aut.untitledtemalab.backend.api.services

import bme.aut.untitledtemalab.backend.api.model.ApiStatistics
import bme.aut.untitledtemalab.backend.database.JobRepository
import bme.aut.untitledtemalab.backend.database.UserRepository
import bme.aut.untitledtemalab.backend.database.model.Status
import org.springframework.stereotype.Service

@Service
class AdminLogicService(private val userRepository: UserRepository,
                        private val jobRepository: JobRepository) {

    fun getStatistics(): ApiStatistics {
        val statistics = ApiStatistics()

        statistics.allUsersNum = userRepository.count()
        statistics.allSendersNum = userRepository.countAllByCanDeliver(false)
        statistics.allDeliverersNum = statistics.allUsersNum - statistics.allSendersNum

        statistics.allJobsNum = jobRepository.count()
        statistics.allPendingJobsNum = jobRepository.countAllByStatus(Status.pending)
        statistics.allAcceptedJobsNum = jobRepository.countAllByStatus(Status.accepted)
        statistics.allPickedUpJobsNum = jobRepository.countAllByStatus(Status.pickedUp)
        statistics.allDeliveredJobsNum = jobRepository.countAllByStatus(Status.delivered)
        statistics.allExpiredJobsNum = jobRepository.countAllByStatus(Status.expired)

        return statistics
    }
}