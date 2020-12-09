package bme.aut.untitledtemalab.backend.api.services

import bme.aut.untitledtemalab.backend.api.model.*
import bme.aut.untitledtemalab.backend.api.responses.InvalidPasswordModelError
import bme.aut.untitledtemalab.backend.api.security.encoder
import bme.aut.untitledtemalab.backend.database.JobRepository
import bme.aut.untitledtemalab.backend.database.UserRepository
import bme.aut.untitledtemalab.backend.database.model.Jobs
import bme.aut.untitledtemalab.backend.database.model.PackageSize
import bme.aut.untitledtemalab.backend.database.model.Status
import bme.aut.untitledtemalab.backend.database.model.Users
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsersLogicService(private val userRepository: UserRepository,
                        private val jobRepository: JobRepository,
                        private val idGeneratorService: IdGeneratorService) {

    fun registerNewUser(newUser: UserRegistration) {
        val newUserId = idGeneratorService.generateUID()

        userRepository.save(Users(id = newUserId, password = encoder().encode(newUser.password!!), canDeliver = newUser.canDeliver!!, emailAddress = newUser.email!!, cargoMaxSize = newUser.cargoSize, cargoFreeSize = newUser.cargoSize))
    }

    fun getUserProfile(userId: Long): UserProfile {
        val dbUser = userRepository.findById(userId)
        return if (dbUser.get().canDeliver) {
            UserProfile(userId = dbUser.get().id, email = dbUser.get().emailAddress, rating = dbUser.get().userRating, canDeliver = dbUser.get().canDeliver, isAdmin = dbUser.get().isAdmin, cargoFreeSize = dbUser.get().cargoFreeSize, cargoMaxSize = dbUser.get().cargoMaxSize)
        } else {
            UserProfile(userId = dbUser.get().id, email = dbUser.get().emailAddress, rating = dbUser.get().userRating, canDeliver = dbUser.get().canDeliver, isAdmin =  dbUser.get().isAdmin, cargoFreeSize = 0, cargoMaxSize =0)
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
        return User(userId = dbUser.get().id, email = dbUser.get().emailAddress, rating = dbUser.get().userRating)
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
        return getFilteredJobs(user,status,size, ::filterJobsSender)
    }

    fun getAllDeliveredJobsFiltered(user: Users, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>): List<Jobs> {
        return getFilteredJobs(user,status,size, ::filterJobsDeliverer)
    }

    fun getFilteredJobs(user: Users, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>,  jobsFilterFunction: (user: Users, queryStatus: Status?, querySize: PackageSize?) -> List<Jobs> ): List<Jobs> {
        var queryStatus: Status? = null
        if (status.isPresent)
            queryStatus = Status.fromValue(status.get().toString())

        var querySize: PackageSize? = null
        if (size.isPresent)
            querySize = PackageSize.fromValue(size.get().toString())

        return  jobsFilterFunction(user, queryStatus, querySize)
    }

    fun filterJobsDeliverer(user: Users, queryStatus: Status? = null, querySize: PackageSize?): List<Jobs>  {
        return if (queryStatus != null && querySize == null)
            jobRepository.findAllByStatusAndDeliverer(queryStatus, user)
        else if (queryStatus == null && querySize != null)
            jobRepository.findAllBySizeAndDeliverer(querySize, user)
        else if (queryStatus != null && querySize != null)
            jobRepository.findAllBySizeAndStatusAndDeliverer(querySize, queryStatus, user)
        else
            listOf()
    }

    fun filterJobsSender(user: Users, queryStatus: Status? = null, querySize: PackageSize?): List<Jobs>  {
        return if (queryStatus != null && querySize == null)
            jobRepository.findAllByStatusAndSender(queryStatus, user)
        else if (queryStatus == null && querySize != null)
            jobRepository.findAllBySizeAndSender(querySize, user)
        else if (queryStatus != null && querySize != null)
            jobRepository.findAllBySizeAndStatusAndSender(querySize, queryStatus, user)
        else
            listOf()
    }

    fun getUserCargo(userId: Long): UserCargo {
        val dbUser = userRepository.findById(userId)
        return UserCargo(cargoFreeSize = dbUser.get().cargoFreeSize, cargoMaxSize = dbUser.get().cargoMaxSize)
    }

    fun getUserFromEmail(email: String): Users {
        return userRepository.findAllByEmailAddress(email).first()
    }
}