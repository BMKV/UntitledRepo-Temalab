package bme.aut.untitledtemalab.backend

import bme.aut.untitledtemalab.backend.api.model.UserProfile
import bme.aut.untitledtemalab.backend.api.services.IdGeneratorService
import bme.aut.untitledtemalab.backend.api.services.UsersLogicService
import bme.aut.untitledtemalab.backend.database.JobRepository
import bme.aut.untitledtemalab.backend.database.RouteRepository
import bme.aut.untitledtemalab.backend.database.UserRepository
import bme.aut.untitledtemalab.backend.database.model.Users
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.assertEquals

@SpringBootTest(classes = [UsersLogicService::class])
@ExtendWith(MockKExtension::class)
class UsersLogicServiceTests {
    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var jobRepository: JobRepository

    @MockK
    lateinit var routeRepository: RouteRepository

    @MockK
    lateinit var idGeneratorService: IdGeneratorService

    @InjectMockKs
    lateinit var usersLogicService: UsersLogicService

    @Before
    fun beforeTests() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        every { userRepository.findById(1000000000) } returns Optional.of(userSender)
        every { userRepository.findById(1000000001) } returns Optional.of(userDeliverer)
    }

    @Test
    fun test_getUserProfile_shouldPass() {
        assertEquals(userSenderProfile,usersLogicService.getUserProfile(1000000000))
        assertEquals(userDelivererProfile,usersLogicService.getUserProfile(1000000001))
    }

    // Variables for tests
    private final val userSender= Users(id = 1000000000, canDeliver = false, emailAddress = "test1@test.test", password = "password")
    private final val userDeliverer = Users(id = 1000000001, canDeliver = true, emailAddress = "test2@test.test", password = "password", cargoMaxSize = 10, cargoFreeSize = 10)
    private final val userSenderProfile = UserProfile(userId = userSender.id, email = userSender.emailAddress, rating = userSender.userRating, canDeliver = userSender.canDeliver, isAdmin =  userSender.isAdmin, cargoFreeSize = 0, cargoMaxSize =0)
    private final val userDelivererProfile = UserProfile(userId = userDeliverer.id, email = userDeliverer.emailAddress, rating = userDeliverer.userRating, canDeliver = userDeliverer.canDeliver, isAdmin =  userDeliverer.isAdmin, cargoFreeSize = userDeliverer.cargoFreeSize, cargoMaxSize = userDeliverer.cargoMaxSize)
}