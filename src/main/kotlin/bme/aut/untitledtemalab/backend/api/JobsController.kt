package bme.aut.untitledtemalab.backend.api

import bme.aut.untitledtemalab.backend.geoapi.GeoApi
import bme.aut.untitledtemalab.backend.api.model.Job
import bme.aut.untitledtemalab.backend.api.model.JobRegistration
import bme.aut.untitledtemalab.backend.api.responses.*
import bme.aut.untitledtemalab.backend.database.*
import bme.aut.untitledtemalab.backend.database.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("jobs")
class JobsController {

    @Autowired
    private lateinit var jobRepository: JobRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var routeRepository: RouteRepository

    @GetMapping
    fun getAllAvailableJobs(@RequestParam size: Optional<Job.SizeEnum>): List<Job> {
        return if (size.isPresent) {
            val packageSize = PackageSize.fromValue(size.get().toString())
            if (packageSize != null)
                Job.convertDbJobListToApiJobList(jobRepository.findAllByStatusAndSize(Status.pending, packageSize))
            else throw InvalidInputException("package size is not correct")
        } else Job.convertDbJobListToApiJobList(jobRepository.findAllByStatus(Status.pending))
    }

    @PostMapping
    fun postJob(@RequestParam(name = "user-id") userId: Long, @RequestBody jobRegistration: JobRegistration): ResponseEntity<String> {
        if (!jobRegistration.isValid())
            throw InvalidInputException()
        val dbUser = userRepository.findById(userId)
        if (dbUser.isEmpty)
            throw UserNotFoundException()

        val newJobId = UIDGenerator.generateUID()

        val route = Routes(id = UIDGenerator.generateUID(),
                startLocation = jobRegistration.startLocation!!,
                destination = jobRegistration.destination!!,
                optimalTime = GeoApi.getOptimalRouteTime(jobRegistration.startLocation!!,jobRegistration.destination!!))

        val job = Jobs(id = newJobId,
                payment = jobRegistration.payment!!,
                jobIssuedDate = jobRegistration.jobIssuedDate!!.toString(),
                deadline = jobRegistration.deadline.toString(),
                sender = dbUser.get(),
                deliveryRoute = route,
                size = PackageSize.fromValue(jobRegistration.size!!.toString())!!,
                status = Status.pending)

        routeRepository.save(route)
        jobRepository.save(job)

        val responseHeaders = HttpHeaders()
        responseHeaders.set("Location", newJobId.toString())
        return ResponseEntity.created(URI("jobs/post/$newJobId"))
                .headers(responseHeaders)
                .body("{job-id: $newJobId}")
    }

    @GetMapping("post/{job-id}")
    fun getJobById(@PathVariable("job-id") jobId: Long): Job {
        val dbJob = jobRepository.findById(jobId)
        if (dbJob.isEmpty)
            throw JobNotFoundException()
        return Job(dbJob.get())
    }

    @PutMapping("post/{job-id}")
    fun updateJob(@PathVariable("job-id") jobId: Long,@RequestParam(name = "user-id") userId: Long, @RequestBody jobRegistration: JobRegistration) {
        //validate
        val dbJob = jobRepository.findById(jobId)
        val dbUser = userRepository.findById(userId)
        validateJobAndUser(dbJob, dbUser)
        if (!isUserSender(dbJob.get(), dbUser.get()))
            throw ModifyJobUnauthorisedUserException()
        //logic
        if (jobRegistration.updateDbJob(dbJob.get()))
            jobRepository.save(dbJob.get())
    }

    @DeleteMapping("post/{job-id}")
    fun deleteJob(@PathVariable("job-id") jobId: Long, @RequestParam(name = "user-id") userId: Long) {
        //validate
        val dbJob = jobRepository.findById(jobId)
        val dbUser = userRepository.findById(userId)
        validateJobAndUser(dbJob, dbUser)
        if (!isUserSender(dbJob.get(), dbUser.get()))
            throw ModifyJobUnauthorisedUserException()
        // logic
        jobRepository.delete(dbJob.get())
    }

    @PostMapping("accept/{job-id}")
    fun acceptJob(@PathVariable("job-id") jobId: Long, @RequestParam(name = "user-id") userId: Long) {
        val dbJob = jobRepository.findById(jobId)
        val dbUser = userRepository.findById(userId)
        // validate
        validateJobAndUser(dbJob, dbUser)
        if (isUserSender(dbJob.get(), dbUser.get()))
            throw ModifyJobUnauthorisedUserException()
        if (dbJob.get().status != Status.pending)
            throw JobNotPendingException()
        if (dbUser.get().cargoFreeSize - PackageSize.toInt(dbJob.get().size) < 0)
            throw NotEnoughSpaceInCargoException()
        // logic
        dbUser.get().packageDelivered.add(dbJob.get())
        dbUser.get().cargoFreeSize -= PackageSize.toInt(dbJob.get().size)
        dbJob.get().status = Status.accepted

        jobRepository.save(dbJob.get())
        userRepository.save(dbUser.get())
    }

    @DeleteMapping("accept/{job-id}")
    fun abandonJob(@PathVariable("job-id") jobId: Long, @RequestParam(name = "user-id") userId: Long) {
        val dbJob = jobRepository.findById(jobId)
        val dbUser = userRepository.findById(userId)
        // validate
        validateJobAndUser(dbJob, dbUser)
        if (!isUserDeliverer(dbJob.get(), dbUser.get()))
            throw ModifyJobUnauthorisedUserException()
        // logic
        dbUser.get().packageDelivered.remove(dbJob.get())
        dbJob.get().status = Status.pending

        jobRepository.save(dbJob.get())
        userRepository.save(dbUser.get())
    }

    private fun isUserSender(dbJob: Jobs, dbUser: Users): Boolean {
        return dbJob.sender == dbUser
    }

    private fun isUserDeliverer(dbJob: Jobs, dbUser: Users): Boolean {
        return dbJob.deliverer == dbUser
    }

    private fun validateJobAndUser(dbJob: Optional<Jobs>, dbUser: Optional<Users>) {
        if (dbJob.isEmpty)
            throw JobNotFoundException()
        if (dbUser.isEmpty)
            throw UserNotFoundException()
    }
}