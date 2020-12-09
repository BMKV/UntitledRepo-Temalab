package bme.aut.untitledtemalab.backend

import bme.aut.untitledtemalab.backend.api.responses.InvalidUserIdModelError
import bme.aut.untitledtemalab.backend.api.responses.UserNotFoundModelError
import bme.aut.untitledtemalab.backend.api.services.IdGeneratorService
import bme.aut.untitledtemalab.backend.api.services.UsersValidationService
import bme.aut.untitledtemalab.backend.database.UserRepository
import bme.aut.untitledtemalab.backend.database.model.Users
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.assertFailsWith

@SpringBootTest(classes = [UsersValidationService::class])
@ExtendWith(MockKExtension::class)
class UsersValidationServiceTests {

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var idGeneratorService: IdGeneratorService

    @InjectMockKs
    lateinit var usersValidationService: UsersValidationService

    @Before
    fun beforeTests() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        every { userRepository.findById(1000000000) } returns Optional.of(Users(id = 1000000000, canDeliver = false, emailAddress = "test@test.test", password = "password"))
        every { userRepository.existsById(1000000000) } returns true
        every { userRepository.existsById(1000000001) } returns false
        every { idGeneratorService.validateUID(1) } returns false
        every { idGeneratorService.validateUID(1000000000) } returns true
        every { idGeneratorService.validateUID(1000000001) } returns true
    }

    @Test
    fun test_validateUserId_shouldPass() {
        assertFailsWith<InvalidUserIdModelError> {
            usersValidationService.validateUserId(1)
        }
        assertFailsWith<UserNotFoundModelError> {
            usersValidationService.validateUserId(1000000001)
        }

        assertDoesNotThrow {
            usersValidationService.validateUserId(1000000000)
        }
    }
}