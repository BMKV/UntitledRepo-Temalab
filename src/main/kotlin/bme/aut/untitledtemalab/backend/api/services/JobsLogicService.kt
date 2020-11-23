package bme.aut.untitledtemalab.backend.api.services

import bme.aut.untitledtemalab.backend.api.model.Job
import bme.aut.untitledtemalab.backend.api.model.JobRegistration
import bme.aut.untitledtemalab.backend.api.responses.*
import bme.aut.untitledtemalab.backend.database.JobRepository
import bme.aut.untitledtemalab.backend.database.RouteRepository
import bme.aut.untitledtemalab.backend.database.UIDGenerator
import bme.aut.untitledtemalab.backend.database.UserRepository
import bme.aut.untitledtemalab.backend.database.model.*
import bme.aut.untitledtemalab.backend.geoapi.GeoApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class JobsLogicService {
    @Autowired
    private lateinit var jobRepository: JobRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var routeRepository: RouteRepository

    fun getAllAvailableJobs(size: Optional<Job.SizeEnum>): List<Job> {
        return if (size.isPresent) {
            val packageSize = PackageSize.fromValue(size.get().toString())
            if (packageSize != null)
                Job.convertDbJobListToApiJobList(jobRepository.findAllByStatusAndSize(Status.pending, packageSize))
            else throw InvalidInputModelError()
        } else Job.convertDbJobListToApiJobList(jobRepository.findAllByStatus(Status.pending))
    }

    fun addNewJob(userId: Long, jobRegistration: JobRegistration): Long {
        val dbUser = userRepository.findById(userId)

        val newJobId = UIDGenerator.generateUID()

        val route = Routes(id = UIDGenerator.generateUID(),
                startLocation = jobRegistration.startLocation!!,
                destination = jobRegistration.destination!!,
                optimalTime = GeoApi.getOptimalRouteTime(jobRegistration.startLocation!!, jobRegistration.destination!!))

        val job = Jobs(id = newJobId,
                payment = jobRegistration.payment!!,
                jobIssuedDate = jobRegistration.jobIssuedDate!!.toString(),
                deadline = jobRegistration.deadline.toString(),
                sender = dbUser.get(),
                deliveryRoute = route,
                size = PackageSize.fromValue(jobRegistration.size!!.toString())!!,
                status = Status.pending,
                name = jobRegistration.name.toString())

        routeRepository.save(route)
        jobRepository.save(job)

        return newJobId
    }

    fun getJob(jobId: Long): Job {
        return Job(jobRepository.findById(jobId).get())
    }

    fun changeJob(jobId: Long, userId: Long, jobRegistration: JobRegistration) {
        val dbJob = jobRepository.findById(jobId)
        val dbUser = userRepository.findById(userId)

        if (!isUserSender(dbJob.get(), dbUser.get()))
            throw ModifyJobUnauthorisedUserModelError()

        if (jobRegistration.updateDbJob(dbJob.get()))
            jobRepository.save(dbJob.get())
    }

    fun deleteJob(jobId: Long, userId: Long) {
        val dbJob = jobRepository.findById(jobId)
        val dbUser = userRepository.findById(userId)

        if (!isUserSender(dbJob.get(), dbUser.get()))
            throw ModifyJobUnauthorisedUserModelError()

        jobRepository.delete(dbJob.get())
    }

    fun acceptJob(jobId: Long, userId: Long) {
        val dbJob = jobRepository.findById(jobId)
        val dbUser = userRepository.findById(userId)

        if (isUserSender(dbJob.get(), dbUser.get()))
            throw CantAcceptJobModelError()
        if (dbJob.get().status != Status.pending)
            throw JobNotPendingModelError()
        if ((dbUser.get().cargoFreeSize - PackageSize.toInt(dbJob.get().size)) < 0)
            throw NotEnoughSpaceInCargoModelError()

        dbUser.get().packageDelivered.add(dbJob.get())
        dbUser.get().cargoFreeSize -= PackageSize.toInt(dbJob.get().size)
        dbJob.get().status = Status.accepted

        jobRepository.save(dbJob.get())
        userRepository.save(dbUser.get())
    }

    fun abandonJob(jobId: Long, userId: Long) {
        val dbJob = jobRepository.findById(jobId)
        val dbUser = userRepository.findById(userId)

        if (dbJob.get().status != Status.accepted)
            throw JobNotAcceptedModelError()
        if (!isUserDeliverer(dbJob.get(), dbUser.get()))
            throw ModifyJobUnauthorisedUserModelError()

        dbUser.get().packageDelivered.remove(dbJob.get())
        dbJob.get().status = Status.pending

        jobRepository.save(dbJob.get())
        userRepository.save(dbUser.get())
    }

    private fun isUserDeliverer(dbJob: Jobs, dbUser: Users): Boolean {
        return dbJob.deliverer!!.id == dbUser.id
    }

    private fun isUserSender(dbJob: Jobs, dbUser: Users): Boolean {
        return dbJob.sender.id == dbUser.id
    }

}