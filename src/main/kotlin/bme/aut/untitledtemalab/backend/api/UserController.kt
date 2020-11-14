package bme.aut.untitledtemalab.backend.api

import bme.aut.untitledtemalab.backend.api.model.*
import bme.aut.untitledtemalab.backend.api.responses.*
import bme.aut.untitledtemalab.backend.database.DatabaseHandler
import bme.aut.untitledtemalab.backend.database.JobRepository
import bme.aut.untitledtemalab.backend.database.UserRepository
import bme.aut.untitledtemalab.backend.database.model.Jobs
import bme.aut.untitledtemalab.backend.database.model.PackageSize
import bme.aut.untitledtemalab.backend.database.model.Status
import bme.aut.untitledtemalab.backend.database.model.Users
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var jobRepository: JobRepository

    @PostMapping("register")
    fun createUser(@RequestBody newUser: UserRegistration) {
        if (!newUser.isValidBody())
            throw InvalidInputException("UserRegistration body is not correct")
        if (!newUser.isValidEmailFormat())
            throw InvalidEmailFormatException()
        if (userRepository.findAllByEmailAddress(newUser.email.toString()).isNotEmpty())
            throw EmailAlreadyInUseException()
        val newUserId = DatabaseHandler.generateUID()

        DatabaseHandler.createUser(Users(id = newUserId, password = newUser.password!!, canDeliver = newUser.canDeliver!!, emailAddress = newUser.email!!))
    }

    @GetMapping("login")
    fun loginUser() {
        // TODO give token for user login
    }

    @GetMapping("logout")
    fun logoutUser() {
        // TODO revoke token for user
    }

    @GetMapping("profile")
    fun profileUser(@RequestParam(name = "user-id") userId: Long): ResponseEntity<UserProfile> {
        if (!DatabaseHandler.validateUID(userId))
            throw InvalidUserIdException("Invalid User ID supplied")
        val dbUser = userRepository.findById(userId)
        return if (dbUser.isPresent && dbUser.get().canDeliver && dbUser.get().cargoFreeSize != null && dbUser.get().cargoMaxSize != null) {
            ResponseEntity(UserProfile(id = dbUser.get().id, email = dbUser.get().emailAddress, rating = dbUser.get().userRating!!, canDeliver = dbUser.get().canDeliver, freeSize = dbUser.get().cargoFreeSize!!, maxSize = dbUser.get().cargoMaxSize!!), HttpStatus.OK)
        } else if (dbUser.isPresent && !dbUser.get().canDeliver) {
            ResponseEntity(UserProfile(id = dbUser.get().id, email = dbUser.get().emailAddress, rating = dbUser.get().userRating!!, canDeliver = dbUser.get().canDeliver, 0, 0), HttpStatus.OK)
        } else throw UserNotFoundException("User not found")
    }

    @PutMapping("profile/update/password")
    fun updateUserPassword(@RequestParam(name = "user-id") userId: Long, @RequestBody userUpdatePassword: UserUpdatePassword) {
        if (!DatabaseHandler.validateUID(userId))
            throw InvalidUserIdException("Invalid User ID supplied")
        updateUser(userId = userId, userUpdate = userUpdatePassword)
    }

    @PutMapping("profile/update/cargo")
    fun updateUserCargo(@RequestParam(name = "user-id") userId: Long, @RequestBody userUpdateCargoSize: UserUpdateCargoSize) {
        if (!DatabaseHandler.validateUID(userId))
            throw InvalidUserIdException("Invalid User ID supplied")
        updateUser(userId = userId, userUpdate = userUpdateCargoSize)
    }

    @PutMapping("profile/update/can-deliver")
    fun updateUserCanDeliver(@RequestParam(name = "user-id") userId: Long, @RequestBody userUpdateCanDeliver: UserUpdateCanDeliver) {
        if (!DatabaseHandler.validateUID(userId))
            throw InvalidUserIdException("Invalid User ID supplied")
        updateUser(userId = userId, userUpdate = userUpdateCanDeliver)
    }

    @PutMapping("profile/update/email")
    fun updateUserEmail(@RequestParam(name = "user-id") userId: Long, @RequestBody userUpdateEmail: UserUpdateEmail) {
        if (!DatabaseHandler.validateUID(userId))
            throw InvalidUserIdException("Invalid User ID supplied")
        updateUser(userId = userId, userUpdate = userUpdateEmail)
    }

    private fun updateUser(userId: Long, userUpdate: UserUpdate) {
        val dbUser = userRepository.findById(userId)
        if (dbUser.isPresent && userUpdate.validatePassword(dbUser.get().password)) {
            DatabaseHandler.updateUser(userUpdate.updateUser(dbUser.get()))
        } else throw UserNotFoundException("User not found")
    }

    @DeleteMapping("profile")
    fun deleteUser(@RequestParam(name = "user-id") userId: Long) {
        val dbUser = userRepository.findById(userId)
        if (dbUser.isPresent)
            DatabaseHandler.removeUser(dbUser.get())
    }

    @GetMapping("{user-id}")
    fun getUserById(@PathVariable("user-id") userId: Long): ResponseEntity<User> {
        if (!DatabaseHandler.validateUID(userId))
            throw InvalidUserIdException("Invalid User ID supplied")
        val dbUser = userRepository.findById(userId)
        return if (dbUser.isPresent) {
            ResponseEntity(User(id = dbUser.get().id, email = dbUser.get().emailAddress, rating = dbUser.get().userRating!!), HttpStatus.OK)
        } else throw UserNotFoundException("User not found")
    }

    @GetMapping("{user-id}/jobs")
    fun getUserAllJobs(@PathVariable("user-id") userId: Long, @RequestParam status: Optional<Job.StatusEnum>, @RequestParam size: Optional<Job.SizeEnum>): List<Job> {
        return filterJobs(userId, status, size, ::getAllJobsFiltered, ::getAllJobs)
    }

    @GetMapping("{user-id}/jobs/sent")
    fun getUserSentJobs(@PathVariable("user-id") userId: Long, @RequestParam status: Optional<Job.StatusEnum>, @RequestParam size: Optional<Job.SizeEnum>): List<Job> {
        return filterJobs(userId, status, size, ::getAllSentJobsFiltered, ::getAllSentJobs)
    }

    @GetMapping("{user-id}/jobs/delivered")
    fun getUserDeliveredJobs(@PathVariable("user-id") userId: Long, @RequestParam status: Optional<Job.StatusEnum>, @RequestParam size: Optional<Job.SizeEnum>): List<Job> {
        return filterJobs(userId, status, size, ::getAllDeliveredJobsFiltered, ::getAllDeliveredJobs)

    }

    fun filterJobs(userId: Long, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>,
                   jobsFilterFunction: (user: Users, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>) -> List<Jobs>,
                   jobsNoFilterFunction: (user: Users) -> List<Jobs>): List<Job> {
        // validation
        if (!DatabaseHandler.validateUID(userId))
            throw InvalidUserIdException("Invalid User ID supplied")
        // logic
        val dbUser = userRepository.findById(userId)
        return if (!dbUser.isEmpty) {
            if (status.isEmpty && size.isEmpty) {
                Job.convertDbJobListToApiJobList(jobsNoFilterFunction(dbUser.get()))
            } else Job.convertDbJobListToApiJobList(jobsFilterFunction(dbUser.get(), status, size))
        // validation
        } else throw UserNotFoundException("User not found")
    }

    fun getAllJobs(user: Users): List<Jobs>{
        return user.packageDelivered + user.packageSent
    }

    fun getAllSentJobs(user: Users): List<Jobs>{
        return user.packageSent
    }

    fun getAllDeliveredJobs(user: Users): List<Jobs>{
        return user.packageDelivered
    }

    fun getAllJobsFiltered(user: Users, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>): List<Jobs>{
        return getAllSentJobsFiltered(user,status,size) + getAllDeliveredJobsFiltered(user,status,size)
    }

    fun getAllSentJobsFiltered(user: Users, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>): List<Jobs>{
        var result = listOf<Jobs>()
        val queryStatus: Status? = Status.fromValue(status.get().toString())
        val querySize: PackageSize? = PackageSize.fromValue(size.get().toString())

        if (queryStatus != null && querySize == null)
            result = jobRepository.findAllByStatusIsLikeAndSender(queryStatus, user)
        if (queryStatus == null && querySize != null)
            result = jobRepository.findAllBySizeIsLikeAndSender(querySize, user)
        else if (queryStatus != null && querySize != null)
            result = jobRepository.findAllBySizeIsLikeAndStatusIsLikeAndSender(querySize, queryStatus, user)

        return result
    }

    fun getAllDeliveredJobsFiltered(user: Users, status: Optional<Job.StatusEnum>, size: Optional<Job.SizeEnum>): List<Jobs>{
        var result = listOf<Jobs>()
        val queryStatus: Status? = Status.fromValue(status.get().toString())
        val querySize: PackageSize? = PackageSize.fromValue(size.get().toString())

        if (queryStatus != null && querySize == null)
            result = jobRepository.findAllByStatusIsLikeAndDeliverer(queryStatus, user)
        if (queryStatus == null && querySize != null)
            result = jobRepository.findAllBySizeIsLikeAndDeliverer(querySize, user)
        else if (queryStatus != null && querySize != null)
            result = jobRepository.findAllBySizeIsLikeAndStatusIsLikeAndDeliverer(querySize, queryStatus, user)

        return result
    }

    @GetMapping("{user-id}/cargo")
    fun getUserCargo(@PathVariable("user-id") userId: Long): ResponseEntity<UserCargo> {
        if (!DatabaseHandler.validateUID(userId))
            throw InvalidUserIdException("Invalid User ID supplied")
        val dbUser = userRepository.findById(userId)
        return if (dbUser.isPresent && dbUser.get().canDeliver && dbUser.get().cargoFreeSize != null && dbUser.get().cargoMaxSize != null) {
            ResponseEntity(UserCargo(freeSize = dbUser.get().cargoFreeSize!!, maxSize = dbUser.get().cargoMaxSize!!), HttpStatus.OK)
        } else throw UserNotFoundException("User not found")
    }
}