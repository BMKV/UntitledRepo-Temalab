package bme.aut.untitledtemalab.backend

import bme.aut.untitledtemalab.backend.api.model.ApiStatistics
import bme.aut.untitledtemalab.backend.api.services.AdminLogicService
import bme.aut.untitledtemalab.backend.database.JobRepository
import bme.aut.untitledtemalab.backend.database.UserRepository
import bme.aut.untitledtemalab.backend.database.model.Status
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest(classes = [AdminLogicService::class])
@ExtendWith(MockKExtension::class)
class AdminLogicServiceTests {

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var jobRepository: JobRepository

    @InjectMockKs
    lateinit var adminLogicService: AdminLogicService

    @Before
    fun beforeTests() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        every { userRepository.count() } returns testAllUsersNum
        every { userRepository.countAllByCanDeliver(false) } returns testAllSendersNum
        every { jobRepository.count() } returns testAllJobsNum
        every { jobRepository.countAllByStatus(Status.pending) } returns testAllPendingJobsNum
        every { jobRepository.countAllByStatus(Status.accepted) } returns testAllAcceptedJobsNum
        every { jobRepository.countAllByStatus(Status.pickedUp) } returns testAllPickedUpJobsNum
        every { jobRepository.countAllByStatus(Status.delivered) } returns testAllDeliveredJobsNum
        every { jobRepository.countAllByStatus(Status.expired) } returns testAllExpiredJobsNum

        apiStatisticsExpected.allUsersNum = 10
        apiStatisticsExpected.allSendersNum = 4
        apiStatisticsExpected.allDeliverersNum = testAllUsersNum - testAllSendersNum
        apiStatisticsExpected.allJobsNum = 21
        apiStatisticsExpected.allPendingJobsNum = 2
        apiStatisticsExpected.allAcceptedJobsNum = 3
        apiStatisticsExpected.allPickedUpJobsNum = 4
        apiStatisticsExpected.allDeliveredJobsNum = 5
        apiStatisticsExpected.allExpiredJobsNum = 7
    }

    @Test
    fun test_getStatistics_shouldPass() {
        assertEquals(apiStatisticsExpected,adminLogicService.getStatistics())
    }

    val apiStatisticsExpected = ApiStatistics()

    // Variables for testing
    val testAllUsersNum: Long = 10
    val testAllSendersNum: Long = 4
    val testAllJobsNum: Long = 21
    val testAllPendingJobsNum: Long = 2
    val testAllAcceptedJobsNum: Long = 3
    val testAllPickedUpJobsNum: Long = 4
    val testAllDeliveredJobsNum: Long = 5
    val testAllExpiredJobsNum: Long = 7
}