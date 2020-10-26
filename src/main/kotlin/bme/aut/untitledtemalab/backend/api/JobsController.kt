package bme.aut.untitledtemalab.backend.api

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("jobs")
class JobsController {

    @GetMapping
    fun getAllAvailableJobs() {}

    @PostMapping
    fun postJob() {}

    @GetMapping("post/{job-id}")
    fun getJobById(@PathVariable("job-id") parameter: String) {}

    @PutMapping("post/{job-id}")
    fun updateJob(@PathVariable("job-id") parameter: String) {}

    @DeleteMapping("post/{job-id}")
    fun deleteJob(@PathVariable("job-id") parameter: String) {}

    @PostMapping("accept/{job-id}")
    fun acceptJob(@PathVariable("job-id") parameter: String) {}

    @DeleteMapping("accept/{job-id}")
    fun abandonJob(@PathVariable("job-id") parameter: String) {}
}