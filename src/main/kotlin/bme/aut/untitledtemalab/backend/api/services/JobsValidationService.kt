package bme.aut.untitledtemalab.backend.api.services

import bme.aut.untitledtemalab.backend.api.model.JobRegistration
import bme.aut.untitledtemalab.backend.api.responses.InvalidInputModelError
import bme.aut.untitledtemalab.backend.api.responses.JobNotFoundModelError
import bme.aut.untitledtemalab.backend.database.JobRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobsValidationService {
    @Autowired
    private lateinit var jobRepository: JobRepository

    fun validateJobRegistration(jobRegistration: JobRegistration) {
        if (!jobRegistration.isValid())
            throw InvalidInputModelError()
    }

    fun validateJobId(jobId: Long) {
        if (!jobRepository.existsById(jobId))
            throw JobNotFoundModelError()
    }
}