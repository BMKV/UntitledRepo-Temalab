package bme.aut.untitledtemalab.backend

import bme.aut.untitledtemalab.backend.api.model.Job
import bme.aut.untitledtemalab.backend.api.services.IdGeneratorService
import bme.aut.untitledtemalab.backend.api.services.JobsLogicService
import bme.aut.untitledtemalab.backend.database.JobRepository
import bme.aut.untitledtemalab.backend.database.RouteRepository
import bme.aut.untitledtemalab.backend.database.UserRepository
import bme.aut.untitledtemalab.backend.database.model.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.*


@SpringBootTest(classes = [JobsLogicService::class])
@ExtendWith(MockKExtension::class)
class JobsLogicServiceTests {

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var jobRepository: JobRepository

    @MockK
    lateinit var routeRepository: RouteRepository

    @MockK
    lateinit var idGeneratorService: IdGeneratorService

    @InjectMockKs
    lateinit var jobsLogicService: JobsLogicService

    @Before
    fun beforeTests() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        every { jobRepository.findAllByStatus(Status.pending) } returns allJobsList
        every { jobRepository.findAllByStatusAndSize(Status.pending, PackageSize.small) } returns allSmallJobsList
        every { jobRepository.findAllByStatusAndSize(Status.pending, PackageSize.medium) } returns allMediumJobsList
        every { jobRepository.findAllByStatusAndSize(Status.pending, PackageSize.large) } returns allLargeJobsList
    }

    @Test
    fun test_getAllAvailableJobs_shouldPass() {
        assertArrayEquals(listOf(Job(job1), Job(job2), Job(job3)).toTypedArray(), jobsLogicService.getAllAvailableJobs(Optional.empty()).toTypedArray())
        assertArrayEquals(listOf(Job(job1)).toTypedArray(), jobsLogicService.getAllAvailableJobs(Optional.of(Job.SizeEnum.SMALL)).toTypedArray())
        assertArrayEquals(listOf(Job(job2)).toTypedArray(), jobsLogicService.getAllAvailableJobs(Optional.of(Job.SizeEnum.MEDIUM)).toTypedArray())
        assertArrayEquals(listOf(Job(job3)).toTypedArray(), jobsLogicService.getAllAvailableJobs(Optional.of(Job.SizeEnum.LARGE)).toTypedArray())

    }

    // Variables for tests
    private final val user = Users(id = 1000000000, canDeliver = false, emailAddress = "test@test.test", password = "password")

    private final val route = Routes(id = 0,
            startLocation = "",
            destination = "",
            optimalTime = "0")

    private final val job1 = Jobs(id = 0,
            payment = 0,
            jobIssuedDate = OffsetDateTime.now().toString(),
            deadline = OffsetDateTime.now().plusDays(1).toString(),
            sender = user,
            deliveryRoute = route,
            size = PackageSize.small,
            status = Status.pending,
            name = "name1")

    private final val job2 = Jobs(id = 0,
            payment = 0,
            jobIssuedDate = OffsetDateTime.now().toString(),
            deadline = OffsetDateTime.now().plusDays(1).toString(),
            sender = user,
            deliveryRoute = route,
            size = PackageSize.medium,
            status = Status.pending,
            name = "name2")

    private final val job3 = Jobs(id = 0,
            payment = 0,
            jobIssuedDate = OffsetDateTime.now().toString(),
            deadline = OffsetDateTime.now().plusDays(1).toString(),
            sender = user,
            deliveryRoute = route,
            size = PackageSize.large,
            status = Status.pending,
            name = "name3")

    val allJobsList = listOf(job1, job2, job3)
    val allSmallJobsList = listOf(job1)
    val allMediumJobsList = listOf(job2)
    val allLargeJobsList = listOf(job3)
}