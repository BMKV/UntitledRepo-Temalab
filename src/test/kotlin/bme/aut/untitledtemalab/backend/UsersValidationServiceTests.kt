package bme.aut.untitledtemalab.backend

import bme.aut.untitledtemalab.backend.api.responses.InvalidUserIdModelError
import bme.aut.untitledtemalab.backend.api.responses.UserNotFoundModelError
import bme.aut.untitledtemalab.backend.api.services.UsersValidationService
import bme.aut.untitledtemalab.backend.database.UserRepository
import bme.aut.untitledtemalab.backend.database.model.Users
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import kotlin.test.assertFailsWith
import kotlin.test.fail


@ExtendWith(SpringExtension::class)
class UsersValidationServiceTests {

    @InjectMocks
    private lateinit var usersValidationService: UsersValidationService

    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_validateUserId_shouldPass() {
        val user = Optional.of(Users(id = 1000000000, canDeliver = false, emailAddress = "test@test.test", password = "password"))
        given(userRepository.findById(1000000000)).willReturn(user)
        `when`(userRepository.findById(1000000000)).thenReturn(user)

        assertFailsWith<InvalidUserIdModelError> {
            usersValidationService.validateUserId(1)
        }
        assertFailsWith<UserNotFoundModelError> {
            usersValidationService.validateUserId(1000000001)
        }

        /*try {
            usersValidationService.validateUserId(1000000000)
        } catch (e: Throwable) {
            println(":,(")
            fail("Failed correct userId verification")
        }*/
    }
}