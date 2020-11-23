package bme.aut.untitledtemalab.backend.api.controllers

import bme.aut.untitledtemalab.backend.api.model.*
import bme.aut.untitledtemalab.backend.api.responses.ApiModelError
import bme.aut.untitledtemalab.backend.api.services.UsersLogicService
import bme.aut.untitledtemalab.backend.api.services.UsersValidationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    private lateinit var usersValidationService: UsersValidationService

    @Autowired
    private lateinit var usersLogicService: UsersLogicService

    @PostMapping("register")
    fun createUser(@RequestBody newUser: UserRegistration): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserRegistration(newUser)
            usersLogicService.registerNewUser(newUser)
            ResponseEntity("Successful registration", HttpStatus.CREATED)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @GetMapping("login")
    fun loginUser(@RequestParam(name = "email") email: String, @RequestParam(name = "password") password: String): ResponseEntity<Any> {
        return try {
            usersValidationService.validateEmailAndPassword(email,password)
            val user = usersLogicService.getUserFromEmail(email)
            ResponseEntity(user.id.toString(), HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @GetMapping("logout")
    fun logoutUser() {
        // TODO revoke token for user
    }

    @GetMapping("profile")
    fun profileUser(@RequestParam(name = "user-id") userId: Long): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            return ResponseEntity(usersLogicService.getUserProfile(userId), HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @PutMapping("profile/update/password")
    fun updateUserPassword(@RequestParam(name = "user-id") userId: Long, @RequestBody userUpdatePassword: UserUpdatePassword): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            usersLogicService.updateUser(userId = userId, userUpdate = userUpdatePassword)
            return ResponseEntity("User's password updated", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @PutMapping("profile/update/cargo")
    fun updateUserCargo(@RequestParam(name = "user-id") userId: Long, @RequestBody userUpdateCargoSize: UserUpdateCargoSize): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            usersLogicService.updateUser(userId = userId, userUpdate = userUpdateCargoSize)
            return ResponseEntity("User's cargo updated", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @PutMapping("profile/update/can-deliver")
    fun updateUserCanDeliver(@RequestParam(name = "user-id") userId: Long, @RequestBody userUpdateCanDeliver: UserUpdateCanDeliver): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            usersLogicService.updateUser(userId = userId, userUpdate = userUpdateCanDeliver)
            return ResponseEntity("User's can deliver updated", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @PutMapping("profile/update/email")
    fun updateUserEmail(@RequestParam(name = "user-id") userId: Long, @RequestBody userUpdateEmail: UserUpdateEmail): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            usersLogicService.updateUser(userId = userId, userUpdate = userUpdateEmail)
            return ResponseEntity("User's email updated", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @DeleteMapping("profile")
    fun deleteUser(@RequestParam(name = "user-id") userId: Long): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            usersLogicService.deleteUser(userId)
            return ResponseEntity("User deleted", HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @GetMapping("{user-id}")
    fun getUserById(@PathVariable("user-id") userId: Long): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            return ResponseEntity(usersLogicService.getUser(userId), HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }

    }

    @GetMapping("{user-id}/jobs")
    fun getUserAllJobs(@PathVariable("user-id") userId: Long, @RequestParam status: Optional<Job.StatusEnum>, @RequestParam size: Optional<Job.SizeEnum>): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            return ResponseEntity(usersLogicService.filterUserJobs(userId, status, size, usersLogicService::getAllJobsFiltered, usersLogicService::getAllJobs), HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @GetMapping("{user-id}/jobs/sent")
    fun getUserSentJobs(@PathVariable("user-id") userId: Long, @RequestParam status: Optional<Job.StatusEnum>, @RequestParam size: Optional<Job.SizeEnum>): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            return ResponseEntity(usersLogicService.filterUserJobs(userId, status, size, usersLogicService::getAllSentJobsFiltered, usersLogicService::getAllSentJobs), HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @GetMapping("{user-id}/jobs/delivered")
    fun getUserDeliveredJobs(@PathVariable("user-id") userId: Long, @RequestParam status: Optional<Job.StatusEnum>, @RequestParam size: Optional<Job.SizeEnum>): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            return ResponseEntity(usersLogicService.filterUserJobs(userId, status, size, usersLogicService::getAllDeliveredJobsFiltered, usersLogicService::getAllDeliveredJobs), HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }

    @GetMapping("{user-id}/cargo")
    fun getUserCargo(@PathVariable("user-id") userId: Long): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            return ResponseEntity(usersLogicService.getUserCargo(userId),HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }
}