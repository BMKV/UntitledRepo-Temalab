package bme.aut.untitledtemalab.backend

import bme.aut.untitledtemalab.backend.api.model.JobRegistration
import bme.aut.untitledtemalab.backend.api.responses.InvalidInputModelError
import bme.aut.untitledtemalab.backend.api.services.JobsValidationService
import bme.aut.untitledtemalab.backend.database.JobRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertFailsWith

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [JobsValidationService::class])
class JobsValidationServiceTests {

    @Autowired
    private lateinit var jobsValidationService: JobsValidationService

    @MockBean
    private lateinit var jobRepository: JobRepository

    @Test
    fun test_validateJobRegistration_shouldPass() {
        val testWrongJobRegistration = JobRegistration()
        assertFailsWith<InvalidInputModelError> {
            jobsValidationService.validateJobRegistration(testWrongJobRegistration)
        }
    }
}