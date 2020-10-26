package bme.aut.untitledtemalab.backend.api

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController {

    @PostMapping("register")
    fun createUser() {}

    @GetMapping("login")
    fun loginUser() {}

    @GetMapping("logout")
    fun logoutUser() {}

    @GetMapping("profile")
    fun profileUser() {}

    @PutMapping("profile")
    fun updateUser() {}

    @DeleteMapping("profile")
    fun deleteUser() {}

    @GetMapping("{user-id}")
    fun getUserById(@PathVariable("user-id") parameter: String) {}

    @GetMapping("{user-id}/jobs")
    fun getUserAllJobs(@PathVariable("user-id") parameter: String) {}

    @GetMapping("{user-id}/jobs/sent")
    fun getUserSentJobs(@PathVariable("user-id") parameter: String) {}

    @GetMapping("{user-id}/jobs/delivered")
    fun getUserDeliveredJobs(@PathVariable("user-id") parameter: String) {}

    @GetMapping("{user-id}/cargo")
    fun getUserCargo(@PathVariable("user-id") parameter: String) {}
}