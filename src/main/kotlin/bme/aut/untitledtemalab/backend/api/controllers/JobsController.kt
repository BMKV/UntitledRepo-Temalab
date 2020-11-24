package bme.aut.untitledtemalab.backend.api.controllers

import bme.aut.untitledtemalab.backend.api.model.Job
import bme.aut.untitledtemalab.backend.api.model.JobRegistration
import bme.aut.untitledtemalab.backend.api.responses.ApiModelError
import bme.aut.untitledtemalab.backend.api.services.JobsLogicService
import bme.aut.untitledtemalab.backend.api.services.JobsValidationService
import bme.aut.untitledtemalab.backend.api.services.UsersValidationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("jobs")
class JobsController {

    @Autowired
    private lateinit var usersValidationService: UsersValidationService

    @Autowired
    private lateinit var jobsValidationService: JobsValidationService

    @Autowired
    private lateinit var jobsLogicService: JobsLogicService

    @GetMapping
    fun getAllAvailableJobs(@RequestParam size: Optional<Job.SizeEnum>): ResponseEntity<Any> {
        return try {
            return ResponseEntity(jobsLogicService.getAllAvailableJobs(size), HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @PostMapping
    fun postJob(@RequestParam(name = "user-id") userId: Long, @RequestBody jobRegistration: JobRegistration): ResponseEntity<Any> {
        return try {
            jobsValidationService.validateJobRegistration(jobRegistration)
            usersValidationService.validateUserId(userId)
            val newJobId = jobsLogicService.addNewJob(userId, jobRegistration)
            val responseHeaders = HttpHeaders()
            responseHeaders.set("Location", newJobId.toString())
            return ResponseEntity.created(URI("jobs/post/$newJobId"))
                    .headers(responseHeaders)
                    .body("{job-id: $newJobId}")

        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @GetMapping("post/{job-id}")
    fun getJobById(@PathVariable("job-id") jobId: Long): ResponseEntity<Any> {
        return try {
            jobsValidationService.validateJobId(jobId)
            return ResponseEntity(jobsLogicService.getJob(jobId), HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @PutMapping("post/{job-id}")
    fun updateJob(@PathVariable("job-id") jobId: Long, @RequestParam(name = "user-id") userId: Long, @RequestBody jobRegistration: JobRegistration): ResponseEntity<Any> {
        return try {
            jobsValidationService.validateJobId(jobId)
            usersValidationService.validateUserId(userId)
            jobsLogicService.changeJob(jobId, userId, jobRegistration)
            return ResponseEntity("Job modified", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @DeleteMapping("post/{job-id}")
    fun deleteJob(@PathVariable("job-id") jobId: Long, @RequestParam(name = "user-id") userId: Long): ResponseEntity<Any> {
        return try {
            jobsValidationService.validateJobId(jobId)
            usersValidationService.validateUserId(userId)
            jobsLogicService.deleteJob(jobId, userId)
            return ResponseEntity("Job deleted", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @PostMapping("accept/{job-id}")
    fun acceptJob(@PathVariable("job-id") jobId: Long, @RequestParam(name = "user-id") userId: Long): ResponseEntity<Any> {
        return try {
            jobsValidationService.validateJobId(jobId)
            usersValidationService.validateUserId(userId)
            jobsLogicService.acceptJob(jobId, userId)
            return ResponseEntity("Job accepted", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @DeleteMapping("accept/{job-id}")
    fun abandonJob(@PathVariable("job-id") jobId: Long, @RequestParam(name = "user-id") userId: Long): ResponseEntity<Any> {
        return try {
            jobsValidationService.validateJobId(jobId)
            usersValidationService.validateUserId(userId)
            jobsLogicService.abandonJob(jobId, userId)
            return ResponseEntity("Job abandoned", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @PutMapping("rate/{job-id}")
    fun rateJob(@PathVariable("job-id") jobId: Long, @RequestParam(name = "user-id") userId: Long, @RequestParam(name = "rating") rating: Int): ResponseEntity<Any> {
        return try {
            jobsValidationService.validateRating(rating)
            jobsValidationService.validateJobId(jobId)
            usersValidationService.validateUserId(userId)
            jobsLogicService.rateJob(jobId,userId,rating)
            return ResponseEntity("Job rated", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @PutMapping("pickup/{job-id}")
    fun pickUpJob(@PathVariable("job-id") jobId: Long, @RequestParam(name = "user-id") userId: Long): ResponseEntity<Any> {
        return try {
            jobsValidationService.validateJobId(jobId)
            usersValidationService.validateUserId(userId)
            jobsLogicService.pickUpJob(jobId,userId)
            return ResponseEntity("Job picked up", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @PutMapping("deliver/{job-id}")
    fun deliverJob(@PathVariable("job-id") jobId: Long, @RequestParam(name = "user-id") userId: Long): ResponseEntity<Any> {
        return try {
            jobsValidationService.validateJobId(jobId)
            usersValidationService.validateUserId(userId)
            jobsLogicService.deliverJob(jobId,userId)
            return ResponseEntity("Job delivered", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }
}