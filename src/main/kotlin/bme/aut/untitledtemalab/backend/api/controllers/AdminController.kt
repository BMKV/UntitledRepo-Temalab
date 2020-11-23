package bme.aut.untitledtemalab.backend.api.controllers

import bme.aut.untitledtemalab.backend.api.responses.ApiModelError
import bme.aut.untitledtemalab.backend.api.services.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("admin")
class AdminController {

    @Autowired
    private lateinit var usersValidationService: UsersValidationService

    @Autowired
    private lateinit var usersLogicService: UsersLogicService

    @Autowired
    private lateinit var jobsLogicService: JobsLogicService

    @Autowired
    private lateinit var adminLogicService: AdminLogicService

    @GetMapping("statistics")
    fun getStatistics(@RequestParam(name = "user-id") userId: Long): ResponseEntity<Any> {
        return try {
            usersValidationService.validateUserId(userId)
            usersValidationService.isUserAdmin(userId)
            ResponseEntity(adminLogicService.getStatistics(), HttpStatus.OK)
        } catch (e: ApiModelError) {
            ResponseEntity(e.message, e.getHttpStatusCode())
        }
    }
}