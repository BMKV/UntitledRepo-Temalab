package bme.aut.untitledtemalab.backend.database

import bme.aut.untitledtemalab.backend.database.model.Jobs
import bme.aut.untitledtemalab.backend.database.model.Status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


@Service
class JobExpiryChecker {
    @Autowired
    private lateinit var jobRepository: JobRepository

    // 3 600 000 millisecond = 1 hour
    @Scheduled(fixedRate = 3600000)
    fun updateJobs()    {
        val pendingJobs = jobRepository.findAllByStatus(Status.pending)
        for (j: Jobs in pendingJobs) {
            try {
                if (LocalDateTime.parse(j.deadline, DateTimeFormatter.ISO_OFFSET_DATE_TIME) < LocalDateTime.now()) {
                    j.status = Status.expired
                    jobRepository.save(j)
                }
            }
            catch (e: DateTimeParseException)   {
                println("LOG: Jobs: ${j.id} : job.deadline : invalid datetime format.")
            }
        }
    }
}