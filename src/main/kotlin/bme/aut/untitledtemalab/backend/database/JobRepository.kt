package bme.aut.untitledtemalab.backend.database

import bme.aut.untitledtemalab.backend.database.model.Jobs
import bme.aut.untitledtemalab.backend.database.model.PackageSize
import bme.aut.untitledtemalab.backend.database.model.Status
import bme.aut.untitledtemalab.backend.database.model.Users
import org.springframework.data.repository.CrudRepository

interface JobRepository : CrudRepository<Jobs, Long> {
    fun findAllByStatus(status: Status): List<Jobs>
    fun findAllByStatusAndSize(status: Status, size: PackageSize): List<Jobs>

    fun findAllByStatusAndDeliverer(status: Status, deliverer: Users): List<Jobs>
    fun findAllBySizeAndDeliverer(size: PackageSize, deliverer: Users): List<Jobs>
    fun findAllBySizeAndStatusAndDeliverer(size: PackageSize,status: Status, deliverer: Users): List<Jobs>

    fun findAllByStatusAndSender(status: Status, deliverer: Users): List<Jobs>
    fun findAllBySizeAndSender(size: PackageSize, deliverer: Users): List<Jobs>
    fun findAllBySizeAndStatusAndSender(size: PackageSize,status: Status, deliverer: Users): List<Jobs>
}