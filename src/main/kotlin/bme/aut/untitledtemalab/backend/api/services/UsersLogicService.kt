package bme.aut.untitledtemalab.backend.api.services

import bme.aut.untitledtemalab.backend.api.model.*
import bme.aut.untitledtemalab.backend.api.responses.InvalidPasswordModelError
import bme.aut.untitledtemalab.backend.database.JobRepository
import bme.aut.untitledtemalab.backend.database.UIDGenerator
import bme.aut.untitledtemalab.backend.database.UserRepository
import bme.aut.untitledtemalab.backend.database.model.Jobs
import bme.aut.untitledtemalab.backend.database.model.PackageSize
import bme.aut.untitledtemalab.backend.database.model.Status
import bme.aut.untitledtemalab.backend.database.model.Users
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsersLogicService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var jobRepository: JobRepository

    fun registerNewUser(newUser: UserRegistration) {
        val newUserId = UIDGenerator.generateUID()

        userRepository.save(Users(id = newUserId, password = newUser.password!!, canDeliver = newUser.canDeliver!!, emailAddress = newUser.email!!))
    }

    fun getUserProfile(userId: Long): UserProfile {
        val dbUser = userRepository.findById(userId)
        return if (dbUser.get().canDeliver) {
            UserProfile(id = dbUser.get().id, email = dbUser.get().emailAddress, rating = dbUser.get().userRating, canDeliver = dbUser.get().canDeliver,isAdmin = dbUser.get().isAdmin, freeSize = dbUser.get().cargoFreeSize, maxSize = dbUser.get().cargoMaxSize)
        } else {
            UserProfile(id = dbUser.get().id, email = dbUser.get().emailAddress, rating = dbUser.get().userRating, canDeliver = dbUser.get().canDeliver, dbUser.get().isAdmin,0, 0)
        }
    }

    fun updateUser(userId: Long, userUpdate: UserUpdate) {
        val dbUser = userRepository.findById(userId)

        if (!userUpdate.validatePassword(dbUser.get().password))
            throw InvalidPasswordModelError()

        userRepository.save(userUpdate.updateUser(dbUser.get()))
    }

    fun deleteUser(userId: Long) {
        val dbUser = userRepository.findById(userId)
        if (dbUser.isPresent)
            userRepository.delete(dbUser.get())
    }

    fun getUser(userId: Long): User {
        val dbUser = userRepository.findById(userId)
        return User(id = dbUser.get().id, email = dbUser.get().emailAddress, rating = dbUser.get().userRating)
    }

    fun filterUserJobs(userId: Long, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>,
                   jobsFilterFunction: (user: Users, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>) -> List<Jobs>,
                   jobsNoFilterFunction: (user: Users) -> List<Jobs>): List<Job> {
        val dbUser = userRepository.findById(userId)
        return if (status.isEmpty && size.isEmpty) {
            Job.convertDbJobListToApiJobList(jobsNoFilterFunction(dbUser.get()))
        } else Job.convertDbJobListToApiJobList(jobsFilterFunction(dbUser.get(), status, size))
    }

    fun getAllJobs(user: Users): List<Jobs> {
        return user.packageDelivered + user.packageSent
    }

    fun getAllSentJobs(user: Users): List<Jobs> {
        return user.packageSent
    }

    fun getAllDeliveredJobs(user: Users): List<Jobs> {
        return user.packageDelivered
    }

    fun getAllJobsFiltered(user: Users, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>): List<Jobs> {
        return getAllSentJobsFiltered(user, status, size) + getAllDeliveredJobsFiltered(user, status, size)
    }

    fun getAllSentJobsFiltered(user: Users, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>): List<Jobs> {
        val result = mutableListOf<Jobs>()

        var queryStatus: Status? = null
        if (status.isPresent)
            queryStatus = Status.fromValue(status.get().toString())

        var querySize: PackageSize? = null
        if (size.isPresent)
            querySize = PackageSize.fromValue(size.get().toString())

        if (queryStatus != null && querySize == null)
            result.addAll(jobRepository.findAllByStatusAndSender(queryStatus, user))
        if (queryStatus == null && querySize != null)
            result.addAll(jobRepository.findAllBySizeAndSender(querySize, user))
        else if (queryStatus != null && querySize != null)
            result.addAll(jobRepository.findAllBySizeAndStatusAndSender(querySize, queryStatus, user))

        return result
    }

    fun getAllDeliveredJobsFiltered(user: Users, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>): List<Jobs> {
        val result = mutableListOf<Jobs>()
        var queryStatus: Status? = null
        if (status.isPresent)
            queryStatus = Status.fromValue(status.get().toString())

        var querySize: PackageSize? = null
        if (size.isPresent)
            querySize = PackageSize.fromValue(size.get().toString())

        if (queryStatus != null && querySize == null)
            result.addAll(jobRepository.findAllByStatusAndDeliverer(queryStatus, user))
        if (queryStatus == null && querySize != null)
            result.addAll(jobRepository.findAllBySizeAndDeliverer(querySize, user))
        else if (queryStatus != null && querySize != null)
            result.addAll(jobRepository.findAllBySizeAndStatusAndDeliverer(querySize, queryStatus, user))

        return result
    }

    fun getUserCargo(userId: Long): UserCargo {
        val dbUser = userRepository.findById(userId)
        return UserCargo(freeSize = dbUser.get().cargoFreeSize, maxSize = dbUser.get().cargoMaxSize)
    }

    fun getUserFromEmail(email: String): Users {
        return userRepository.findAllByEmailAddress(email).first()
    }
}